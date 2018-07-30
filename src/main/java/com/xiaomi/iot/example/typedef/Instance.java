package com.xiaomi.iot.example.typedef;

import org.json.JSONArray;

public class Instance {

    private String description;

    private String type;

    private String subscriptionId;

    private String status;

    private JSONArray customized_services;

    private JSONArray services;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getCustomized_services() {
        return customized_services;
    }

    public void setCustomized_services(JSONArray customized_services) {
        this.customized_services = customized_services;
    }

    public JSONArray getServices() {
        return services;
    }

    public void setServices(JSONArray services) {
        this.services = services;
    }
}
