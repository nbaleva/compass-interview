package com.cybercoders.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerTests extends CrawlerApplicationTests {

    @Autowired
    private Crawler crawler;

    @Test
    public void testCrawl() {
        Assert.assertNotNull(crawler);
        SummaryStats summaryStats = crawler.crawl("fake url");
        Assert.assertNotNull(summaryStats);
    }
}
