package com.example.trainapp;

public class Order {
    private String orderId;
    private String details;
    private String deliveryPerson;
    private String status;

    public Order(String orderId, String details, String deliveryPerson, String status) {
        this.orderId = orderId;
        this.details = details;
        this.deliveryPerson = deliveryPerson;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDetails() {
        return details;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public String getStatus() {
        return status;
    }
}
