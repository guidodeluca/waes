package com.example.waes.test.api;

import lombok.Data;

@Data
public class Difference {
    private DifferenceType differenceType;
    private String text;
    private int position;
    private int offset;
}
