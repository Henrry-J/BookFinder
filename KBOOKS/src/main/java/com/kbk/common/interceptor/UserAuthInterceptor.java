package com.kbk.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kbk.business.book.search.vo.BookUser;
import com.kbk.common.constant.FrameworkConst;
import com.kbk.common.exception.BookBizException;
import com.kbk.common.util.StringUtil;

public class UserAuthInterceptor
extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(UserAuthInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        this.logger.info("UserAuthInterceptor.preHandle >>>>>>> RequestURI: " + requestUri);
        String suffix = StringUtil.getSuffix(requestUri);
        if (!FrameworkConst.VALID_REQUEST_URL_SUFFIX.containsKey(suffix)) {
            this.logger.error("Undefine Request Suffix >>>>>>> Request URI: " + requestUri);
            throw new BookBizException("정의되지 않은 접근경로입니다.");
        }
        if (FrameworkConst.HTTP_DENY_METHOD.containsKey(request.getMethod())) {
            this.logger.error("Undefine Request Method >>>>>>> Request Method: " + request.getMethod());
            throw new BookBizException("정의되지 않은 요청입니다.");
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            this.logger.debug("Session ID:" + session.getId());
            BookUser loginUser = (BookUser)session.getAttribute(FrameworkConst.LOGIN_USER);
            if (loginUser != null && loginUser.getLoginId() != null) {
                this.logger.debug("LoingUser:" + loginUser.getLoginId() + " - SessionID:" + session.getId());
            } else if (requestUri.contains("/BookMark.book") || requestUri.contains("/WishOne.action")) {
                this.logger.error("UserAuthInterceptor ---- Invalid LoginUserInfo & SessionID");
                ModelAndView mav = new ModelAndView("redirect:/Book/Search.book");
                throw new ModelAndViewDefiningException(mav);
            }
        } else {
            this.logger.debug("UserAuthInterceptor ---- Session is null");
        }
        return true;
    }
}
