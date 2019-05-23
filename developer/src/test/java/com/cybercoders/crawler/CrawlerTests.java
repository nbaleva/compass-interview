package com.cybercoders.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerTests extends CrawlerApplicationTests {

    @Autowired
    private Crawler crawler;

    @Test
    public void testCrawl() {
        Assert.assertNotNull(crawler);
        SummaryStats summaryStats = crawler.crawl("fake url");
        Assert.assertNotNull(summaryStats);
        LOG.info(summaryStats.toString());
    }

    private static Logger LOG = LoggerFactory.getLogger(CrawlerTests.class);
}
