package com.cybercoders.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.cybercoders.crawler.CrawlerApplication.LINKS_URL;
import static java.net.HttpURLConnection.*;

public class FileFetcherTest extends CrawlerApplicationTests {

    @Autowired
    private FileFetcher fileFetcher;

    @Test
    public void testFetchFile() {
        Assert.assertNotNull(fileFetcher);
    }

    @Test
    public void testFetchNotFoundFile() {

        HttpResponse response;

        // non exist http
        response = fileFetcher.fetchFile("http://non_exist");
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_NOT_FOUND, response.getResponseCode());

        // non exist file
        response = fileFetcher.fetchFile("file://non_exist");
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_NOT_FOUND, response.getResponseCode());

        // invalid protocol
        response = fileFetcher.fetchFile("non_exist");
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_NOT_FOUND, response.getResponseCode());

        // null
        response = fileFetcher.fetchFile(null);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_NOT_FOUND, response.getResponseCode());

        // empty
        response = fileFetcher.fetchFile("");
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_NOT_FOUND, response.getResponseCode());

    }

    @Test
    public void testHttp() {

        HttpResponse filetest = testFetchFile(LINKS_URL);
        LOG.info(filetest.getFile());
    }

    @Test
    public void testLocalFile() {

        HttpResponse filetest = testFetchFile(FILETEST_1);
        LOG.info(filetest.getFile());
    }


    private HttpResponse testFetchFile(String url) {

        HttpResponse response = fileFetcher.fetchFile(url);
        Assert.assertNotNull(response);
        LOG.info("file : " + response.getFile());
        return response;
    }

    @Test
    public void test200Http() {

        HttpResponse response = testFetchFile(STATUS_BASE_URL + HTTP_OK);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_OK, response.getResponseCode());
    }

    @Test
    public void test300Http() {

        HttpResponse response = testFetchFile(STATUS_BASE_URL + HTTP_MULT_CHOICE);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_MULT_CHOICE, response.getResponseCode());
        //Assert.assertNotNull(response.getRedirectLocation());
        LOG.info(HTTP_MULT_CHOICE + " : " + response.getRedirectLocation());

        response = testFetchFile(STATUS_BASE_URL + HTTP_MOVED_PERM);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_MOVED_PERM, response.getResponseCode());
        Assert.assertNotNull(response.getRedirectLocation());
        LOG.info(HTTP_MOVED_PERM + " : " + response.getRedirectLocation());

        response = testFetchFile(STATUS_BASE_URL + HTTP_MOVED_TEMP);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_MOVED_TEMP, response.getResponseCode());
        Assert.assertNotNull(response.getRedirectLocation());
        LOG.info(HTTP_MOVED_TEMP + " : " + response.getRedirectLocation());

        response = testFetchFile(STATUS_BASE_URL + HTTP_SEE_OTHER);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_SEE_OTHER, response.getResponseCode());
        Assert.assertNotNull(response.getRedirectLocation());
        LOG.info(HTTP_SEE_OTHER + " : " + response.getRedirectLocation());

        response = testFetchFile(STATUS_BASE_URL + HTTP_USE_PROXY);
        Assert.assertNotNull(response);
        Assert.assertEquals(HTTP_USE_PROXY, response.getResponseCode());
        Assert.assertNotNull(response.getRedirectLocation());
        LOG.info(HTTP_USE_PROXY + " : " + response.getRedirectLocation());

    }

    @Test
    public void testNon200Http() {
        testBase200Http(HTTP_BAD_GATEWAY);
        testBase200Http(HTTP_BAD_METHOD);
        testBase200Http(HTTP_BAD_REQUEST);
        testBase200Http(HTTP_CLIENT_TIMEOUT);
        testBase200Http(HTTP_CONFLICT);
        testBase200Http(HTTP_ENTITY_TOO_LARGE);
        testBase200Http(HTTP_FORBIDDEN);
        testBase200Http(HTTP_GATEWAY_TIMEOUT);
        testBase200Http(HTTP_GONE);
        testBase200Http(HTTP_INTERNAL_ERROR);
        testBase200Http(HTTP_NO_CONTENT);
        testBase200Http(HTTP_NOT_ACCEPTABLE);
        testBase200Http(HTTP_NOT_AUTHORITATIVE);
        testBase200Http(HTTP_NOT_FOUND);
        testBase200Http(HTTP_NOT_IMPLEMENTED);
        testBase200Http(HTTP_NOT_MODIFIED);
        testBase200Http(HTTP_PARTIAL);
        testBase200Http(HTTP_PAYMENT_REQUIRED);
        testBase200Http(HTTP_PRECON_FAILED);
        testBase200Http(HTTP_PROXY_AUTH);
        testBase200Http(HTTP_REQ_TOO_LONG);
        testBase200Http(HTTP_RESET);
        testBase200Http(HTTP_SERVER_ERROR);
        testBase200Http(HTTP_UNAUTHORIZED);
        testBase200Http(HTTP_UNAVAILABLE);
        testBase200Http(HTTP_UNSUPPORTED_TYPE);
        testBase200Http(HTTP_VERSION);

    }

    public void testBase200Http(int responseCode) {

        HttpResponse response = testFetchFile(STATUS_BASE_URL + responseCode);
        Assert.assertNotNull(response);
        Assert.assertEquals(responseCode, response.getResponseCode());
        Assert.assertNull(response.getFile());
    }


    private static final String FILETEST_1 = TEST_RESOURCES + "filetest.txt";
    private static final String STATUS_BASE_URL = "http://httpbin.org/status/";


    private static Logger LOG = LoggerFactory.getLogger(FileFetcherTest.class);

}
