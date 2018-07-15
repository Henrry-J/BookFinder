package com.kbk.business.book.search.web;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbk.business.book.search.service.BookSearchService;
import com.kbk.business.book.search.vo.BookHistory;
import com.kbk.business.book.search.vo.BookMark;
import com.kbk.business.book.search.vo.BookSearchInfo;
import com.kbk.business.book.search.vo.BookUser;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value={"/Book/"})
public class BookSearchController {
    @Autowired
    BookSearchService bookSearchService;

    @RequestMapping(value={"Top.book"})
    public String goTop(HttpServletRequest request) {
        return "/kbooksTop";
    }

    @RequestMapping(value={"Search.book"})
    @ResponseBody
    public ModelAndView goBookSearch(HttpServletRequest request, BookHistory bookHistory) {
        ModelAndView mav = new ModelAndView("/kbooksSearch");
        mav.addObject("historyList", this.bookSearchService.getBookHistory(bookHistory));
        return mav;
    }

    @RequestMapping(value={"Search.action"})
    @ResponseBody
    public JSONObject booksSearch(HttpServletRequest request, BookSearchInfo bookSearchInfo) throws URISyntaxException {
        return this.bookSearchService.getBookList(bookSearchInfo);
    }

    @RequestMapping(value={"Join.book"})
    public String goJoin(HttpServletRequest request) {
        return "/kbooksJoin";
    }

    @RequestMapping(value={"Join.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject prcJoin(HttpServletRequest request, BookUser user) {
        return this.bookSearchService.prcJoin(user);
    }

    @RequestMapping(value={"DupChk.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject prcDupChk(HttpServletRequest request, BookUser user) {
        return this.bookSearchService.prcDupChk(user);
    }
    
    @RequestMapping(value={"Login.book"})
    public String goLogin(HttpServletRequest request) {
        return "/kbooksLogin";
    }

    @RequestMapping(value={"Login.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject prcLogin(HttpServletRequest request, BookUser user) {
        return this.bookSearchService.prcLogin(user);
    }

    @RequestMapping(value={"Logout.action"})
    public String prcLogout(HttpServletRequest request) {
        this.bookSearchService.prcLogout();
        return "redirect:/Book/Search.book";
    }

    @RequestMapping(value={"WishOne.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject insWishOne(HttpServletRequest request, BookMark mark) {
        return this.bookSearchService.insBookMark(mark);
    }

    @RequestMapping(value={"WishDel.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject delWishOne(HttpServletRequest request, BookMark mark) {
        return this.bookSearchService.delBookMark(mark);
    }

    @RequestMapping(value={"BookMark.book"})
    public String goBookMark(HttpServletRequest request) {
        return "/kbooksMark";
    }

    @RequestMapping(value={"BookMark.action"}, method={RequestMethod.POST})
    @ResponseBody
    public JSONObject getBookMark(HttpServletRequest request, BookSearchInfo bookSearchInfo) {
        return this.bookSearchService.getBookMark(bookSearchInfo);
    }
}
