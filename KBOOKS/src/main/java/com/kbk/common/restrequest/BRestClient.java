package com.kbk.common.restrequest;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.kbk.common.exception.BookBizException;
import com.kbk.common.mstdata.util.SystemPropsUtil;

@Component
public class BRestClient {
    Logger logger = LoggerFactory.getLogger(BRestClient.class);
    @Autowired
    @Qualifier(value="bRestTemplate")
    RestTemplate bRestTemplate;

    public ResponseEntity<String> restRequestGet(String uri) {
        if (uri == null || uri.isEmpty()) {
            return null;
        }
        this.logger.debug("RestClient Called URL: " + uri);
        
        ResponseEntity<String> res = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType media = new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8);
            headers.setContentType(media);
            headers.add("Authorization", "KakaoAK " + SystemPropsUtil.getEnvironProperty("API_AUTH_KEY"));
            
            HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			res =  bRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			
            this.logger.info("RestClient HEADER: " + res.getHeaders());
            this.logger.info("RestClient STATUS: " + res.getStatusCode());
            this.logger.info("RestClient BODY: " + ((String)res.getBody()).trim());
        }catch(UnknownHttpStatusCodeException e){
			e.printStackTrace();
			logger.error("UnknownHttpStatusCodeException occured at TRestClient - StatusCode:" + e.getRawStatusCode() + " - " + e.getMessage());
			throw new BookBizException("요청에 대한 미정의 상태코드(STATUS:" + e.getRawStatusCode() +")");
	    }catch(HttpStatusCodeException e){
	    	e.printStackTrace();
			logger.error("HttpStatusCodeException occured at TRestClient - StatusCode:" + e.getStatusCode().value() + " - " + e.getMessage());
			throw new BookBizException("요청에 대한 상태코드오류(STATUS:" + e.getStatusCode().value() +")");
	    }catch(ResourceAccessException e){
	    	e.printStackTrace();
	    	if(e.getCause() instanceof SocketTimeoutException){
	    		logger.error("SocketTimeoutException occured at TRestClient: " + e.getMessage());
	    		throw new BookBizException("SocketTimeoutException 발생:"+ e.getMessage());
	    	}else if(e.getCause() instanceof HttpHostConnectException){
	    		logger.error("HttpHostConnectException occured at TRestClient: " + e.getMessage());
	    		throw new BookBizException("HttpHostConnectException 발생:"+ e.getMessage());
	    	}else if(e.getCause() instanceof SocketException){
		    	logger.error("SocketException occured at TRestClient: " + e.getMessage());
		    	throw new BookBizException("SocketTimeoutException 발생:"+ e.getMessage());
	    	}else if(e.getCause() instanceof NoHttpResponseException){
		    	logger.error("NoHttpResponseException occured at TRestClient: " + e.getMessage());
		    	throw new BookBizException("NoHttpResponseException 발생:"+ e.getMessage());
	    	}else{
	    		logger.error("ResourceAccessException occured at TRestClient: " + e.getMessage());
		    	throw new BookBizException("ResourceAccessException 발생:"+ e.getMessage());
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
			logger.error("Exception occured at TRestClient:" + e.getMessage());
			throw new BookBizException("ResourceAccessException 발생:"+ e.getMessage());
		}
	    
        return res;
    }
}
