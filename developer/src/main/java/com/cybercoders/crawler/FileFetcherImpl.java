package com.cybercoders.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static java.net.HttpURLConnection.*;

@Service
public class FileFetcherImpl implements FileFetcher {

    @Override
    public HttpResponse fetchFile(String url) {

        if (null != url && url.length() > 0) {
            if (url.toLowerCase().startsWith("http")) {
                return fetchHttpFile(url);
            }
            else if (url.toLowerCase().startsWith("file")) {
                return fetchLocalFile(url);
            }
        }
        // either null/empty url or invalid protocol
        HttpResponse response = new HttpResponse(url);
        response.setResponseCode(HTTP_NOT_FOUND);
        return response;
    }

    private HttpResponse fetchHttpFile(String url) {

        HttpResponse response = new HttpResponse(url);
        try {
            URL website = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)website.openConnection();
            connection.setInstanceFollowRedirects(false);

            int responseCode = connection.getResponseCode();
            LOG.debug("url : {}, response code : {}", url, responseCode);
            response.setResponseCode(responseCode);

            // handle redirects
            if (responseCode == HTTP_MOVED_PERM
                || responseCode == HTTP_MOVED_TEMP
                || responseCode == HTTP_MULT_CHOICE
                || responseCode == HTTP_USE_PROXY
                || responseCode == HTTP_SEE_OTHER) {

                // get the redirect url
                response.setRedirectLocation(connection.getHeaderField("Location"));
            }
            else if (responseCode == HTTP_OK) {

                // read the file response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder file = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    file.append(inputLine);
                }
                in.close();
                response.setFile(file.toString());
            }
            // else failed request
        }
        catch (UnknownHostException uhe) {
            LOG.error("uhe : " + uhe.getClass().getCanonicalName());
            LOG.error("here : " + url + " : " + uhe.getMessage());
            response.setResponseCode(HTTP_NOT_FOUND);
        }
        catch (IOException exc) {
            LOG.error("exc : " + exc.getClass().getCanonicalName());
            LOG.error("here : " + url + " : " + exc.getMessage());
            response.setResponseCode(HTTP_INTERNAL_ERROR);
        }
        return response;
    }

    private HttpResponse fetchLocalFile(String url) {

        HttpResponse response = new HttpResponse();
        try {
            url = url.substring(5); //chomp protocol from url
            String file = new String(Files.readAllBytes(Paths.get(url)));

            response.setResponseCode(HTTP_OK);
            response.setFile(file);
        }
        catch (NoSuchFileException nsfe) {
            LOG.error("exc : " + nsfe.getClass().getCanonicalName());
            LOG.error("file " + url + " : " + nsfe.getMessage());
            response.setResponseCode(HTTP_NOT_FOUND);
        }
        catch (IOException exc) {
            LOG.error("exc : " + exc.getClass().getCanonicalName());
            LOG.error("file " + url + " : " + exc.getMessage());
            response.setResponseCode(HTTP_INTERNAL_ERROR);
        }
        return response;
    }

    private static Logger LOG = LoggerFactory.getLogger(FileFetcherImpl.class);

}
