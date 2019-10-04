package com.example.waes.test.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response {
    private ResponseType type;
    private List<Difference> differences = new ArrayList<>();
}
