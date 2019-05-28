package com.cybercoders.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.net.HttpURLConnection.*;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;
import static java.net.HttpURLConnection.HTTP_USE_PROXY;

@Service
public class CrawlerImpl implements Crawler {

    private FileFetcher fileFetcher;
    private FileParser fileParser;

    @Autowired
    public CrawlerImpl(FileFetcher fileFetcher, FileParser fileParser) {
        this.fileFetcher = fileFetcher;
        this.fileParser = fileParser;
    }

    @Override
    public SummaryStats crawl(String initialUrl) {

        Set<String> unvisitedUrls = null;
        Set<String> visitedUrls = new HashSet<>();
        SummaryStats retval = new SummaryStats();

        // get initial list of links and store into set of unvisited urls
        HttpResponse linksFile = fileFetcher.fetchFile(initialUrl);
        if (null != linksFile && linksFile.getResponseCode() == HTTP_OK
                && null != linksFile.getFile() ) {
            List<String> links = fileParser.parseLinks(linksFile.getFile());
            if (null != links) {

                // getting initial links counts as a successful request
                retval.incrementSuccessfulRequests();
                unvisitedUrls = new HashSet<>(links);
            }
        }

        if (null != unvisitedUrls && unvisitedUrls.size() > 0) {

            // foreach unvisited url
            while (!unvisitedUrls.isEmpty()) {

                // remove url from unvisited set and place into visited set
                String url = unvisitedUrls.iterator().next();
                LOG.debug("url : " + url);
                unvisitedUrls.remove(url);
                visitedUrls.add(url);

                // fetch html content
                HttpResponse urlResponse = fileFetcher.fetchFile(url);

                if (null != urlResponse) {

                    // if fetch html returns redirect 301, 302, or 303
                    if (isRedirect(urlResponse.getResponseCode())) {

                        // increment valid request
                        retval.incrementSuccessfulRequests();

                        // if not in either unvisited or visited set,
                        // put redirect location into unvisited list
                        String redirect = urlResponse.getRedirectLocation();
                        if (null != redirect) {
                            redirect = fixAnchor(url, redirect);
                            if (!unvisitedUrls.contains(redirect) && !visitedUrls.contains(redirect)) {
                                LOG.debug("redirect : " + redirect);
                                unvisitedUrls.add(redirect);
                            }
                        }
                    }
                    else if (HTTP_OK != urlResponse.getResponseCode()) {

                        // if fetch url returns 4XX or 5XX
                        // increment invalid request
                        LOG.debug("failed url : {} : {} ", urlResponse.getResponseCode(), url);
                        retval.incrementFailedRequests();
                    }
                    else {

                        // 200 response, parse html content for list of anchors
                        retval.incrementSuccessfulRequests();
                        // for each anchor if not in visited list and not in unvisited list,
                        // put into unvisited list
                        if (null != urlResponse.getFile()) {
                            List<String> anchors = fileParser.parseHtml(urlResponse.getFile());
                            if (null != anchors) {
                                for (String anchor : anchors) {
                                    if (null != anchor && anchor.length()> 0) {
                                        anchor = fixAnchor(url, anchor);
                                        if(!unvisitedUrls.contains(anchor) &&
                                        !visitedUrls.contains(anchor)) {
                                            LOG.debug("anchor : " + anchor);
                                            unvisitedUrls.add(anchor);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                LOG.debug(retval.toString());
            }
        }

        return retval;
    }

    private boolean isRedirect(int responseCode) {
        return (responseCode == HTTP_MOVED_PERM
            || responseCode == HTTP_MOVED_TEMP
            || responseCode == HTTP_MULT_CHOICE
            || responseCode == HTTP_USE_PROXY
            || responseCode == HTTP_SEE_OTHER);
    }

    private String fixAnchor(String originalUrl, String anchor) {

        try {

            if (null != anchor && anchor.length() > 0) {

                // check if fully qualified url ( has protocol + domain + path )
                if (!anchor.toLowerCase().startsWith("http")) {

                    // full path : add protocol + domain
                    if (anchor.startsWith("/")) {
                        URL baseUrl = new URL(originalUrl);
                        anchor = baseUrl.getProtocol() + "://" + baseUrl.getHost()
                            + anchor;
                    }
                    else {
                        // relative url
                        URL baseUrl = new URL(originalUrl);
                        String basePath = baseUrl.getPath();
                        if (null == basePath) {
                            basePath = "";
                        }

                        if (basePath.length() > 0 && basePath.contains("/")) {
                            basePath = basePath.substring(0, basePath.lastIndexOf("/"));
                        }
                        anchor = baseUrl.getProtocol() + "://" + baseUrl.getHost()
                            + basePath + anchor;
                    }
                }

            }
        }
        catch (MalformedURLException mfe) {
            LOG.error(mfe.getMessage());
        }
        LOG.debug("fixAnchor returning : " + anchor);
        return anchor;
    }

    private static Logger LOG = LoggerFactory.getLogger(CrawlerImpl.class);
}
