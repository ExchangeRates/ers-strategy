package com.wcreators.ersstrategy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class Rate {
    private final String major;
    private final String minor;

    private final Double sell;
    private final Double buy;

    private final Date createdDate;

    public String getName() {
        return String.format("%s/%s", major, minor);
    }
}
