package com.example.waes.test.api;

import com.example.waes.test.config.Base64Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class EncodedRequest extends BaseApi{
    @JsonDeserialize(using = Base64Deserializer.class)
    private String data;
}
