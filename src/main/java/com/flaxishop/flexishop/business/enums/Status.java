package com.flaxishop.flexishop.business.enums;

import lombok.Data;

public enum Status {
    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Status fromLabel(String label) {
        for (Status status : Status.values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No Status found with label: " + label);
    }
}
