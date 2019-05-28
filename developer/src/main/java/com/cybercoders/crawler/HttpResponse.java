package com.cybercoders.crawler;

public class HttpResponse {

    private String originalUrl;
    private int responseCode;
    private String redirectLocation;
    private String file;

    public HttpResponse() { }

    public HttpResponse(String url) {
        this.originalUrl = url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getRedirectLocation() {
        return redirectLocation;
    }

    public void setRedirectLocation(String redirectLocation) {
        this.redirectLocation = redirectLocation;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
