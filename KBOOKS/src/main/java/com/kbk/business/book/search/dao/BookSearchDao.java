package com.kbk.business.book.search.dao;

import com.kbk.business.book.search.vo.BookHistory;
import com.kbk.business.book.search.vo.BookMark;
import com.kbk.business.book.search.vo.BookSearchInfo;
import com.kbk.business.book.search.vo.BookUser;
import java.util.List;

public interface BookSearchDao {
    public BookUser getUsers(BookUser user);
    
    public BookUser getDupUser(BookUser user);

    public void insUsers(BookUser user);

    public void updUsers(BookUser user);

    public void insBookMark(BookMark mark);

    public void delBookMark(BookMark mark);

    public Integer getBookMarkCnt(BookSearchInfo bookSearchInfo);

    public List<BookMark> getBookMark(BookSearchInfo bookSearchInfo);

    public List<BookHistory> getBookHistory(BookHistory bookHistory);

    public void insBookHistory(BookHistory bookHistory);
}
