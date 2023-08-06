package com.currencyexchange.models;

public class OpenExchangeRatesApiRequest {
    private String app_id;
    private String base;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
