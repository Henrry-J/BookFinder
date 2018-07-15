package com.kbk.common.exception;

import com.kbk.common.constant.FrameworkConst;

public class BookBizException extends BookException {
    private static final long serialVersionUID = 5093912443747408170L;

    public BookBizException() {
    }

    public BookBizException(String bizErrorMsg) {
        super(FrameworkConst.RESULT_ERR_BIZ, bizErrorMsg);
    }

    public BookBizException(String bizErrorMsg, String errorDetail) {
        super(FrameworkConst.RESULT_ERR_BIZ, bizErrorMsg, errorDetail);
    }

    public BookBizException(String errorCode, String bizErrorMsg, String errorDetail) {
        super(errorCode, bizErrorMsg, errorDetail);
    }
}
