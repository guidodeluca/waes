package com.example.waes.test.exceptions;

public class ValidationDataException extends BaseException {
    public static final String DATA_ZEROLENGHT = "ERR_ZERODATA";
    public static final String NO_ID = "ERR_NOID";
    public static final String NO_LEFT_DATA = "ERR_NOLEFTDATA";
    public static final String NO_RIGHT_DATA = "ERR_NORIGHTDATA";

    public ValidationDataException(String code) {
        super(code);
    }
}
