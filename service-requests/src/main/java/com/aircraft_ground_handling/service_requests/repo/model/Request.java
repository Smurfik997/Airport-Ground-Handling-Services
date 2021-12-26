package com.aircraft_ground_handling.service_requests.repo.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "ENUM('PARKING')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RequestType requestType;

    @NotNull
    private int details;

    @NotNull
    private int producer;

    private int consumer;

    @Column(columnDefinition = "ENUM('ACTIVE', 'FINISHED')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RequestStatus requestStatus;

    public Request() {
    }

    public Request(RequestType requestType, int details, int producer, int consumer, RequestStatus requestStatus) {
        this.requestType = requestType;
        this.details = details;
        this.producer = producer;
        this.consumer = consumer;
        this.requestStatus = requestStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getDetails() {
        return details;
    }

    public void setDetails(int details) {
        this.details = details;
    }

    public int getProducer() {
        return producer;
    }

    public void setProducer(int producer) {
        this.producer = producer;
    }

    public int getConsumer() {
        return consumer;
    }

    public void setConsumer(int consumer) {
        this.consumer = consumer;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
