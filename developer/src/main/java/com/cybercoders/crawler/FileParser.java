package com.cybercoders.crawler;

import java.util.List;

public interface FileParser {

    List<String> parseHtml(String input);

    List<String> parseLinks(String input);
}
