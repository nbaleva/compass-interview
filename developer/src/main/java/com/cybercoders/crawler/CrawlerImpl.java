package com.cybercoders.crawler;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CrawlerImpl implements Crawler {

    @Override
    public SummaryStats crawl(String initialUrl) {

        Set<String> unvisitedUrls = new HashSet<>();
        Set<String> visitedUrls = new HashSet<>();
        SummaryStats retval = new SummaryStats();

        // get initial list of links and store into set of unvisited urls
        retval.incrementSuccessfulRequests();

        // foreach unvisited url
        // remove url from unvisited set and place into visited set
            // fetch html content
                // if fetch html returns redirect 301, 302, or 303
                    // increment valid request
                    retval.incrementSuccessfulRequests();
                    // if not in either unvisited or visited set, put redirect location into unvisited list
                // if fetch url returns 4XX or 5XX
                    // increment invalid request
                    retval.incrementFailedRequests();
                // if 200 response, parse html content for list of anchors
                    retval.incrementSuccessfulRequests();
                    // for each anchor if not in visited list and not in unvisited list, put into unvisited list

        return retval;
    }

}
