package com.kbk.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.dao.TransientDataAccessResourceException;

import com.kbk.common.constant.FrameworkConst;

public class BookDataAccessException extends BookException {
    
    Logger logger = LoggerFactory.getLogger(BookDataAccessException.class);
    
    private static final long serialVersionUID = 4311525578031136849L;

    public BookDataAccessException() {
    }

    public BookDataAccessException(Throwable ex) {
        this.setErrorCode(FrameworkConst.RESULT_ERR_SYS);
        
        if(ex instanceof DuplicateKeyException){
			// violation of an primary key or unique constraint
			this.setBizErrorMsg("이미 동일한 Key값으로 등록된 데이터가 존재합니다.");
		}
		else if(ex instanceof EmptyResultDataAccessException) {
			// a result was expected to have at least one row but zero rows
			this.setBizErrorMsg("DB 에러: 결과 값이 없음 (예상된 결과 값은 하나 이상)");
		}
		else if(ex instanceof IncorrectResultSizeDataAccessException) {
			// a result was not of the expected size
			this.setBizErrorMsg("DB 에러: 예상된 결과 값의 사이즈와 상이");
		}
		else if(ex instanceof DataAccessResourceFailureException) {
			// can't connect to a database using JDBC
			this.setBizErrorMsg("시스템 에러: DB 접속 실패");
		}
		else if(ex instanceof InvalidDataAccessResourceUsageException) {
			// bad SQL
			this.setBizErrorMsg("시스템 에러: 잘못된 SQL");
		}
		else if(ex instanceof PermissionDeniedDataAccessException) {
			// denied a permission to access a specific element
			this.setBizErrorMsg("DB 에러: 해당 자원에 접근 권한 없습니다.");
		}
		else if(ex instanceof CannotAcquireLockException) {
			// failure to acquire a lock during an update (select for update)
			this.setBizErrorMsg("다른 유저에 의해 동일 데이터가 업데이트 중입니다.");
		}
		else if(ex instanceof DeadlockLoserDataAccessException) {
			// current process was a deadlock loser
			this.setBizErrorMsg("DB 에러: DeadLock 상태");
		}
		else if(ex instanceof QueryTimeoutException) {
			// query timeout
			this.setBizErrorMsg("DB 에러: Query Timeout");
		}
		else if(ex instanceof TransientDataAccessResourceException) {
			// resource fail - read only ..
			this.setBizErrorMsg("DB 에러: 커넥션 속성 위반(read-only)");
		}
		else if(ex instanceof DataAccessException) {
			// resource fail - read only ..
			this.setBizErrorMsg("DB 에러: 데이터 접근중 오류");
		}else {
			// data access Exception supper class
			this.setBizErrorMsg("DB 에러: 데이터 접근중 오류");
		}
        
        this.logger.error("Convert BookDataAccessException Error Msg: " + this.getBizErrorMsg());
        
        if (ex.getCause() != null) {
            this.setErrorDetail(ex.getCause().toString());
            this.logger.error("BookDataAccessException Error Msg Detail: " + ex.getCause().getMessage());
        }
    }
}
