package com.oceanview.model;

import java.io.Serializable;


public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roomId;
    private String roomType;
    private String roomNumber;
    private double roomRate;
    private String status;
    private String description;
    private int capacity;

    public Room() {
    }

    public Room(String roomId, String roomType, String roomNumber, double roomRate,
                String status, String description, int capacity) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.roomRate = roomRate;
        this.status = status;
        this.description = description;
        this.capacity = capacity;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(double roomRate) {
        this.roomRate = roomRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomRate=" + roomRate +
                ", status='" + status + '\'' +
                '}';
    }
}

