package com.assignment.tickerService.kafkaConsumer.view;

public enum ViewType {
    TICKER_ACTIVITY("ticker");

    private String viewName;

    ViewType(String name) {
        this.viewName = name;
    }

    public String getViewName() {
        return viewName;
    }
}
