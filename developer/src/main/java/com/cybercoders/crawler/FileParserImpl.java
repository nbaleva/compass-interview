package com.cybercoders.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileParserImpl implements FileParser {

    @Override
    public List<String> parseHtml(String input) {

        List<String> retval = new ArrayList<>();
        if (null != input && input.length() > 0) {
            Document doc = Jsoup.parse(input);
            Elements elements = doc.getElementsByTag("a");

            for (Element element : elements) {
                retval.add(element.attr("href"));
            }
        }
        return retval;
    }

    @Override
    public List<String> parseLinks(String input) {

        List<String> retval = new ArrayList<>();

        try {
            if (null != input && input.length() > 0) {
                Links links = objectMapper.readValue(input, Links.class);
                if (null != links && null != links.getLinks() && links.getLinks().size() > 0) {
                    retval.addAll(links.getLinks());
                }
            }
        }
        catch (IOException ioe) {
            LOG.error(ioe.getMessage());
        }
        return retval;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Logger LOG = LoggerFactory.getLogger(FileParserImpl.class);
}
