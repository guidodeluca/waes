package com.example.waes.test.exceptions;

public class IncompleteDataException  extends BaseException {
    public static final String NO_DATA = "ERR_NODATA";
    public static final String NO_LEFT_DATA = "ERR_NOLEFTDATA";
    public static final String NO_RIGHT_DATA = "ERR_NORIGHTDATA";

    public IncompleteDataException(String code) {
        super(code);
    }
}
