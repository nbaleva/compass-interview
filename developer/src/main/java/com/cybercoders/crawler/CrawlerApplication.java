package com.cybercoders.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
 *  my solution to  https://github.com/OnAssignment/compass-interview/tree/master/developer
 */
public class CrawlerApplication implements CommandLineRunner {

    private Crawler crawler;

    @Autowired
    public CrawlerApplication(Crawler crawler) {
        this.crawler = crawler;
    }

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SummaryStats stats = crawler.crawl(LINKS_URL);
        LOG.info(stats.toString());
    }

    static final String LINKS_URL = "https://raw.githubusercontent.com/OnAssignment/compass-interview/master/developer/data.json";
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerApplication.class);

}
