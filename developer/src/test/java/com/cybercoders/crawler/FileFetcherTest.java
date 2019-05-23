package com.cybercoders.crawler;

import com.cybercoders.crawler.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.cybercoders.crawler.CrawlerApplication.LINKS_URL;

public class FileFetcherTest extends CrawlerApplicationTests {

    @Autowired
    private FileFetcher fileFetcher;

    @Test
    public void testFetchFile() {
        Assert.assertNotNull(fileFetcher);
    }

    @Test
    public void testFetchNotFoundFile() {

        String testfile = null;

        // non exist http
        try {
            testfile = fileFetcher.fetchFile("http://non_exist");
            Assert.fail();
        }
        catch (NotFoundException nfe) {
            Assert.assertNull(testfile);
        }

        // non exist file
        try {
            testfile = fileFetcher.fetchFile("file://non_exist");
            Assert.fail();
        }
        catch (NotFoundException nfe) {
            Assert.assertNull(testfile);
        }

        // invalid protocol
        try {
            testfile = fileFetcher.fetchFile("non_exist");
            Assert.fail();
        }
        catch (NotFoundException nfe) {
            Assert.assertNull(testfile);
        }

        // null
        try {
            testfile = fileFetcher.fetchFile(null);
            Assert.fail();
        }
        catch (NotFoundException nfe) {
            Assert.assertNull(testfile);
        }

        // empty
        try {
            testfile = fileFetcher.fetchFile("");
            Assert.fail();
        }
        catch (NotFoundException nfe) {
            Assert.assertNull(testfile);
        }

    }

    @Test
    public void testHttp() {

        String filetest = testFetchFile(LINKS_URL);
        LOG.info(filetest);
    }

    @Test
    public void testLocalFile() {

        String filetest = testFetchFile(FILETEST_1);
        LOG.info(filetest);
    }

    private String testFetchFile(String url) {

        String testfile = null;
        try {
            testfile = fileFetcher.fetchFile(url);
            Assert.assertNotNull(testfile);
            LOG.info(testfile);
        }
        catch (NotFoundException nfe) {
            Assert.fail();
        }
        return testfile;
    }


    private static final String FILETEST_1 = TEST_RESOURCES + "filetest.txt";


    private static Logger LOG = LoggerFactory.getLogger(FileFetcherTest.class);

}
