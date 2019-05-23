package com.cybercoders.crawler;

public class SummaryStats {

    private int totalRequests;
    private int successfulRequests;
    private int failedRequests;

    public void incrementSuccessfulRequests() {
        this.totalRequests++;
        this.successfulRequests++;
    }

    public void incrementFailedRequests() {
        this.totalRequests++;
        this.failedRequests++;
    }

    @Override
    public String toString() {
        return "SummaryStats{" + "\n" +
                "\ttotalRequests=" + totalRequests + "\n" +
                "\tsuccessfulRequests=" + successfulRequests + "\n" +
                "\tfailedRequests=" + failedRequests + "\n" +
                '}';
    }
}
