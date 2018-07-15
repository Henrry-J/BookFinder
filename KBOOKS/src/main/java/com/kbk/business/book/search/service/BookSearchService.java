package com.kbk.business.book.search.service;

import com.kbk.business.book.search.vo.BookHistory;
import com.kbk.business.book.search.vo.BookMark;
import com.kbk.business.book.search.vo.BookSearchInfo;
import com.kbk.business.book.search.vo.BookUser;
import java.net.URISyntaxException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BookSearchService {
    public JSONObject getBookList(BookSearchInfo bookSearchInfo) throws URISyntaxException;

    public JSONObject prcJoin(BookUser user);
    
    public JSONObject prcDupChk(BookUser user);

    public JSONObject prcLogin(BookUser user);

    public void prcLogout();

    public JSONObject insBookMark(BookMark mark);

    public JSONObject delBookMark(BookMark mark);

    public JSONObject getBookMark(BookSearchInfo bookSearchInfo);

    public JSONArray getBookHistory(BookHistory bookHistory);
}
