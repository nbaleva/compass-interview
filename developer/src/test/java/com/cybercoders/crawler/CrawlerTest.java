package com.cybercoders.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerTest extends CrawlerApplicationTests {

    @Autowired
    private Crawler crawler;

    @Test
    @org.junit.Ignore
    public void testCrawl() {
        Assert.assertNotNull(crawler);
        SummaryStats summaryStats = crawler.crawl("fake url");
        Assert.assertNotNull(summaryStats);
        Assert.assertEquals(0, summaryStats.getTotalRequests());
        Assert.assertEquals(0, summaryStats.getSuccessfulRequests());
        Assert.assertEquals(0, summaryStats.getFailedRequests());
        LOG.info(summaryStats.toString());
    }

    @Test
    @org.junit.Ignore
    public void testCrawl1Link() {
        SummaryStats summaryStats = crawler.crawl(BASE_TEST_RESOURCES + "crawler1.json");
        Assert.assertNotNull(summaryStats);
        Assert.assertEquals(3, summaryStats.getTotalRequests());
        Assert.assertEquals(3, summaryStats.getSuccessfulRequests());
        Assert.assertEquals(0, summaryStats.getFailedRequests());
        LOG.info(summaryStats.toString());
    }

    @Test
    @org.junit.Ignore
    public void testCrawl2Links() {
        SummaryStats summaryStats = crawler.crawl(BASE_TEST_RESOURCES + "crawler2.json");
        Assert.assertNotNull(summaryStats);
        Assert.assertEquals(4, summaryStats.getTotalRequests());
        Assert.assertEquals(4, summaryStats.getSuccessfulRequests());
        Assert.assertEquals(0, summaryStats.getFailedRequests());
        LOG.info(summaryStats.toString());
    }

    @Test
    public void testData() {
        SummaryStats summaryStats = crawler.crawl(BASE_TEST_RESOURCES + "data.json");
        Assert.assertNotNull(summaryStats);
        LOG.info(summaryStats.toString());
    }

    private static Logger LOG = LoggerFactory.getLogger(CrawlerTest.class);
    private static final String BASE_TEST_RESOURCES = "file:src/test/resources/";
}
