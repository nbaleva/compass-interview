package com.cybercoders.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileParserTest extends CrawlerApplicationTests {

    @Autowired
    private FileFetcher fileFetcher;

    @Autowired
    private FileParser fileParser;

    @Test
    public void testFileParser() {
        Assert.assertNotNull(fileParser);
    }

    @Test
    public void testParseLinks() {

        List<String> links;
        String file = fileFetcher.fetchFile(DATA_FILE).getFile();
        Assert.assertNotNull(file);
        Assert.assertTrue(file.length() > 0);
        links = fileParser.parseLinks(file);
        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 62);

        for (String link : links) {
            LOG.info(link);
        }

        // null file
        links = fileParser.parseLinks(null);
        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 0);

        // empty file
        links = fileParser.parseLinks("");
        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 0);

        // empty json file
        links = fileParser.parseLinks("{}");
        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 0);

        // json file with empty links
        links = fileParser.parseLinks("{\"links\":[]}");
        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 0);

        // bad json property
        links = fileParser.parseLinks("{\"bad_linkss\":[]}");
        Assert.assertNull(links);
    }

    @Test
    public void testParseHtml() {

        List<String> links;
        String file = fileFetcher.fetchFile(HTTP_FILE).getFile();
        Assert.assertNotNull(file);
        Assert.assertTrue(file.length() > 0);
        links = fileParser.parseHtml(file);

        Assert.assertNotNull(links);
        Assert.assertEquals(links.size(), 9);

        for (String link : links) {
            LOG.info(link);
        }
    }


    private static Logger LOG = LoggerFactory.getLogger(FileParserTest.class);
    private static final String DATA_FILE = TEST_RESOURCES + "data.json";
    private static final String HTTP_FILE = "http://httpbin.org/links/10/0";

}
