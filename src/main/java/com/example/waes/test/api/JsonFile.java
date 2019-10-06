package com.example.waes.test.api;

import com.example.waes.test.config.Base64Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonFile {
    private String mime;

    @JsonDeserialize(using = Base64Deserializer.class)
    private String data;
}
