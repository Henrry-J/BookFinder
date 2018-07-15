package com.kbk.common.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.kbk.common.constant.FrameworkConst;
import com.kbk.common.exception.BookBizException;
import com.kbk.common.exception.BookDataAccessException;
import com.kbk.common.exception.BookException;

public class ExceptionAspects {
    Logger logger = LoggerFactory.getLogger(ExceptionAspects.class);

    public void controllerExceptionAdvice(Throwable ex) throws Throwable {
        this.logger.error("ExceptionAdvice (Controller)>>> Throwable: " + ex.getMessage());
        ex.printStackTrace();
        if (ex instanceof BookException) {
            this.logger.error("ExceptionAdvice (Controller) -- BookException >> " + ((BookException)ex).getBizErrorMsg());
            throw ex;
        }
        this.logger.error("ExceptionAdvice (Controller) -- RuntimeException >> " + ex.getMessage());
        throw new BookBizException(FrameworkConst.RESULT_ERR_BIZ, "RuntimeException 발생", ex.getMessage());
    }

    public void exceptionAdvice(Throwable ex) throws Throwable {
        this.logger.error("ExceptionAdvice >>> Throwable: " + ex.getMessage());
        ex.printStackTrace();
        if (ex instanceof DataAccessException) {
            this.logger.error("ExceptionAdvice -- DataAccessException >> " + ex.getCause().getMessage());
            throw new BookDataAccessException(ex);
        }
        if (ex instanceof BookDataAccessException) {
            this.logger.error("ExceptionAdvice -- BookDataAccessException >> " + ((BookException)ex).getBizErrorMsg());
            throw ex;
        }
        if (ex instanceof BookBizException) {
            this.logger.error("ExceptionAdvice -- BookBizException >> " + ((BookException)ex).getBizErrorMsg());
            throw ex;
        }
        this.logger.error("ExceptionAdvice -- RuntimeException >> " + ex.getMessage());
        throw new BookBizException(FrameworkConst.RESULT_ERR_BIZ, "RuntimeException 발생", ex.getMessage());
    }
}
