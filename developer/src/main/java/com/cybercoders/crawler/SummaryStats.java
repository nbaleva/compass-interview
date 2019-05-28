package com.cybercoders.crawler;

public class SummaryStats {

    private int totalRequests;
    private int successfulRequests;
    private int failedRequests;

    void incrementSuccessfulRequests() {
        this.totalRequests++;
        this.successfulRequests++;
    }

    void incrementFailedRequests() {
        this.totalRequests++;
        this.failedRequests++;
    }

    int getTotalRequests() {
        return totalRequests;
    }

    int getSuccessfulRequests() {
        return successfulRequests;
    }

    int getFailedRequests() {
        return failedRequests;
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
