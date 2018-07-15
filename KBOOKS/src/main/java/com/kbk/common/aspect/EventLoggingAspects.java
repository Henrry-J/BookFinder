package com.kbk.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class EventLoggingAspects {
	
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
        Object proceed = null;
        StopWatch sw = new StopWatch(pjp.getSignature().getName());
        sw.start();
        logger.info("Start >> method: " + pjp.getSignature().getName() + " (" + this.getAgumentNames(pjp.getArgs()) + ")");
        proceed = pjp.proceed();
        sw.stop();
        logger.info("End   >> method: " + pjp.getSignature().getName() + " (" + this.getAgumentNames(pjp.getArgs()) + ") --- " + sw.shortSummary());
        return proceed;
    }

    public String getAgumentNames(Object[] obj) {
        StringBuffer sb = new StringBuffer();
        if (obj != null) {
            int i = 0;
            while (i < obj.length) {
                if (i > 0) {
                    sb.append(", ");
                }
                if (obj[i] instanceof String) {
                    sb.append(obj[i].toString());
                } else if (obj[i] instanceof Integer) {
                    sb.append(obj[i].toString());
                } else {
                    sb.append(obj[i].getClass().getSimpleName());
                }
                ++i;
            }
        }
        return sb.toString();
    }
}
