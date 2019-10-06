package com.example.waes.test.api;

import com.example.waes.test.config.Base64Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonFile {
    @ApiModelProperty(notes = "Type of the file send", example = "file/plain", required = true, position = 1)
    private String mime;

    @ApiModelProperty(notes = "Content of the file send", example = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gRG9uZWMgcmhvbmN1cyBzY2VsZXJpc3F1ZSBxdWFtLCBuZWMgbG9ib3J0aXMgcmlzdXMuCg==", required = true, position = 1)
    @JsonDeserialize(using = Base64Deserializer.class)
    private String data;
}
