package com.kbk.common.exception;

public class BookException extends RuntimeException {
	
    private static final long serialVersionUID = 2924399886038342339L;
    
    private String errorCode;
    private String bizErrorMsg;
    private String errorDetail;

    public BookException() {
    }

    public BookException(String bizErrorMsg) {
        this.bizErrorMsg = bizErrorMsg;
    }

    public BookException(String errorCode, String bizErrorMsg) {
        this.errorCode = errorCode;
        this.bizErrorMsg = bizErrorMsg;
    }

    public BookException(String errorCode, String bizErrorMsg, String errorDetail) {
        this.errorCode = errorCode;
        this.bizErrorMsg = bizErrorMsg;
        this.errorDetail = errorDetail;
    }

    public String getBizErrorMsg() {
        return this.bizErrorMsg;
    }

    public void setBizErrorMsg(String bizErrorMsg) {
        this.bizErrorMsg = bizErrorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
