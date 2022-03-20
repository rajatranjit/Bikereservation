package com.example.BikeReservation.BikeReservation.Util;

import java.io.Serializable;
import java.util.Objects;

public class CompositetId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7883738145949121854L;
    private String startTime;
    private String endTime;
    private String bikeNumber;

    public CompositetId() {
    }

    public CompositetId(String startTime, String endTime, String bikeNumber) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.bikeNumber = bikeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositetId accountId = (CompositetId) o;
        return startTime.equals(accountId.startTime) &&
                endTime.equals(accountId.endTime) &&
                bikeNumber.equals(accountId.bikeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, bikeNumber);
    }
}