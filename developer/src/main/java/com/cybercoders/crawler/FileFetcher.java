package com.cybercoders.crawler;

import com.cybercoders.crawler.exception.NotFoundException;

public interface FileFetcher {

    String fetchFile(String utl) throws NotFoundException;
}
