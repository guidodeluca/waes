package com.example.waes.test.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Model of the difference detected in the comparison")
public class Difference {
    @ApiModelProperty(notes = "Type of the difference. DELETE or INSERT")
    private DifferenceType differenceType;

    @ApiModelProperty(notes = "Value of the difference detected.")
    private String text;

    @ApiModelProperty(notes = "Index of the begining of the difference.")
    private int position;

    @ApiModelProperty(notes = "Lenght of the difference.")
    private int offset;
}
