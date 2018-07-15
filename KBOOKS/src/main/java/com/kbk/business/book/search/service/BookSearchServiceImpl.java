package com.kbk.business.book.search.service;

import com.kbk.business.book.search.dao.BookSearchDao;
import com.kbk.business.book.search.service.BookSearchService;
import com.kbk.business.book.search.vo.BookHistory;
import com.kbk.business.book.search.vo.BookMark;
import com.kbk.business.book.search.vo.BookSearchInfo;
import com.kbk.business.book.search.vo.BookUser;
import com.kbk.common.constant.FrameworkConst;
import com.kbk.common.exception.BookBizException;
import com.kbk.common.mstdata.util.SystemPropsUtil;
import com.kbk.common.restrequest.BRestClient;
import com.kbk.common.session.manager.SessionLoginUtil;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookSearchServiceImpl implements BookSearchService {
    @Autowired
    private BookSearchDao bookSearchDao;
    @Autowired
    BRestClient bRestClient;

    @SuppressWarnings("deprecation")
	@Override
    public JSONObject getBookList(BookSearchInfo bookSearchInfo) throws URISyntaxException {
        URIBuilder ub = new URIBuilder(SystemPropsUtil.getEnvironProperty("REST_API_URL"));
        ub.addParameter("query", bookSearchInfo.getQuery());
        ub.addParameter("sort", bookSearchInfo.getSort());
        ub.addParameter("page", bookSearchInfo.getPage());
        ub.addParameter("size", bookSearchInfo.getSize());
        ub.addParameter("target", bookSearchInfo.getTarget());
        ub.addParameter("category", bookSearchInfo.getCategory());
        String url = URLDecoder.decode(ub.toString());
        ResponseEntity<String> ett = this.bRestClient.restRequestGet(url);
        if (ett == null || ett.getBody() == null) {
            throw new BookBizException("응답실패");
        }
        JSONObject result = JSONObject.fromObject(ett.getBody());
        BookUser loginUser = SessionLoginUtil.getLoginUser();
        if (loginUser != null) {
            BookHistory bookHistory = new BookHistory();
            bookHistory.setLoginId(loginUser.getLoginId());
            bookHistory.setTitle(bookSearchInfo.getQuery());
            bookHistory.setEntDt(new Date(System.currentTimeMillis()));
            this.bookSearchDao.insBookHistory(bookHistory);
            result.put("kbookHistory", JSONArray.fromObject(this.bookSearchDao.getBookHistory(bookHistory)));
        }
        return result;
    }

    @Override
    public JSONObject prcJoin(BookUser user) {
        JSONObject prcResult = new JSONObject();
        user.setEntDt(new Date(System.currentTimeMillis()));
        this.bookSearchDao.insUsers(user);
        prcResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_SUCCESS);
        prcResult.put(FrameworkConst.RESULT_MSG, "회원가입이 완료되었습니다");
        return prcResult;
    }
    
    @Override
	public JSONObject prcDupChk(BookUser user) {
    	JSONObject prcResult = new JSONObject();
        BookUser bookUser = this.bookSearchDao.getDupUser(user);
        
        if(bookUser != null) {
        	prcResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_ERR_BIZ);
        	prcResult.put(FrameworkConst.RESULT_MSG, "해당 아이디는 이미 사용중입니다");
        } else {
        	prcResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_SUCCESS);
        	prcResult.put(FrameworkConst.RESULT_MSG, "해당 아이디는 사용가능합니다");
        }
        return prcResult;
	}

    @Override
    public JSONObject prcLogin(BookUser user) {
        JSONObject prcResult = new JSONObject();
        BookUser loginUser = this.bookSearchDao.getUsers(user);
        if (loginUser != null) {
            HttpSession session = SessionLoginUtil.getCurrentSession();
            session.setAttribute(FrameworkConst.LOGIN_USER, loginUser);
            loginUser.setLoginDt(new Date(System.currentTimeMillis()));
            this.bookSearchDao.updUsers(loginUser);
            prcResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_SUCCESS);
            prcResult.put(FrameworkConst.RESULT_MSG, "성공");
            return prcResult;
        }
        throw new BookBizException("아이디와 비밀번호를 확인해주세요");
    }

    @Override
    public void prcLogout() {
        HttpSession session;
        BookUser loginUser = SessionLoginUtil.getLoginUser();
        if (loginUser != null) {
            loginUser.setLogoutDt(new Date(System.currentTimeMillis()));
            this.bookSearchDao.updUsers(loginUser);
        }
        if ((session = SessionLoginUtil.getCurrentSession()) != null) {
            session.invalidate();
        }
    }

    @Override
    public JSONObject insBookMark(BookMark mark) {
        JSONObject insResult = new JSONObject();
        mark.setLoginId(SessionLoginUtil.getLoginUserId());
        mark.setEntDt(new Date(System.currentTimeMillis()));
        this.bookSearchDao.insBookMark(mark);
        insResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_SUCCESS);
        insResult.put(FrameworkConst.RESULT_MSG, "북마크에 등록되었습니다");
        return insResult;
    }

    @Override
    public JSONObject delBookMark(BookMark mark) {
        JSONObject delResult = new JSONObject();
        mark.setLoginId(SessionLoginUtil.getLoginUserId());
        mark.setEntDt(new Date(System.currentTimeMillis()));
        this.bookSearchDao.delBookMark(mark);
        delResult.put(FrameworkConst.RESULT_CODE, FrameworkConst.RESULT_SUCCESS);
        delResult.put(FrameworkConst.RESULT_MSG, "북마크에서 삭제되었습니다");
        return delResult;
    }

    @Override
    public JSONObject getBookMark(BookSearchInfo bookSearchInfo) {
        JSONObject result = new JSONObject();
        bookSearchInfo.setLoginId(SessionLoginUtil.getLoginUserId());
        Integer cnt = this.bookSearchDao.getBookMarkCnt(bookSearchInfo);
        List<BookMark> list = this.bookSearchDao.getBookMark(bookSearchInfo);
        result.put("cnt", cnt);
        result.put("list", list);
        return result;
    }

    @Override
    public JSONArray getBookHistory(BookHistory bookHistory) {
        if (SessionLoginUtil.getLoginUser() != null) {
            bookHistory.setLoginId(SessionLoginUtil.getLoginUserId());
            return JSONArray.fromObject(this.bookSearchDao.getBookHistory(bookHistory));
        }
        return new JSONArray();
    }
}
