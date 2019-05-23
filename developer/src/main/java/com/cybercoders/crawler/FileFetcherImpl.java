package com.cybercoders.crawler;

import com.cybercoders.crawler.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileFetcherImpl implements FileFetcher {

    @Override
    public String fetchFile(String url) throws NotFoundException {

        if (null != url && url.length() > 0) {
            if (url.toLowerCase().startsWith("http")) {
                return fetchHttpFile(url);
            }
            else if (url.toLowerCase().startsWith("file")) {
                return fetchLocalFile(url);
            }
        }
        // either null/empty url or invalid protocol
        throw new NotFoundException("invalid url");
    }

    private String fetchHttpFile(String url) throws NotFoundException {

        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();

            // todo test response code, may need to return 301, 404, etc
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        }
        catch (IOException exc) {
            LOG.error(url + " : " + exc.getMessage());
            throw new NotFoundException(exc.getMessage());
        }
    }

    private String fetchLocalFile(String url) throws NotFoundException {

        try {
            url = url.substring(5); //chomp protocol from url
            return new String(Files.readAllBytes(Paths.get(url)));
        }
        catch (IOException exc) {
            LOG.error(url + " : " + exc.getMessage());
            throw new NotFoundException(exc.getMessage());
        }
    }

    private static Logger LOG = LoggerFactory.getLogger(FileFetcherImpl.class);

}
