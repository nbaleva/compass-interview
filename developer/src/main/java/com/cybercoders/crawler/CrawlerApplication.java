package com.cybercoders.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
 *  my solution to  https://github.com/OnAssignment/compass-interview/tree/master/developer
 */
public class CrawlerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    LOG.info("start");
		LOG.info("end");
	}

	private static Logger LOG = LoggerFactory .getLogger(CrawlerApplication.class);

	static final String LINKS_URL = "https://raw.githubusercontent.com/OnAssignment/compass-interview/master/developer/data.json";

}
