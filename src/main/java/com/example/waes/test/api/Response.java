package com.example.waes.test.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Response of the comparison.")
public class Response {
    @ApiModelProperty(notes = "Result of the lenght of the comparison. EQUASL or NOT EQUALS")
    private ResponseType type;

    @ApiModelProperty(notes = "List of differences in the comparison.")
    private List<Difference> differences = new ArrayList<>();
}
