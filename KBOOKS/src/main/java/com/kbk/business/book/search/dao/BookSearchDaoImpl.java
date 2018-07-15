package com.kbk.business.book.search.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kbk.business.book.search.vo.BookHistory;
import com.kbk.business.book.search.vo.BookMark;
import com.kbk.business.book.search.vo.BookSearchInfo;
import com.kbk.business.book.search.vo.BookUser;
import com.kbk.common.hibernate.BookHibernateDaoSupport;

@Repository(value="bookDao")
public class BookSearchDaoImpl extends BookHibernateDaoSupport implements BookSearchDao {
    Logger logger = LoggerFactory.getLogger(BookSearchDaoImpl.class);

    @Override
    public BookUser getUsers(BookUser user) {
        Criteria crt = this.getSessionFactory().getCurrentSession().createCriteria(BookUser.class);
        crt.add(Restrictions.eq("loginId", user.getLoginId()));
        crt.add(Restrictions.eq("loginPw", user.getLoginPw()));
        return (BookUser)crt.uniqueResult();
    }

    @Override
	public BookUser getDupUser(BookUser user) {
    	Criteria crt = this.getSessionFactory().getCurrentSession().createCriteria(BookUser.class);
        crt.add(Restrictions.eq("loginId", user.getLoginId()));
        return (BookUser)crt.uniqueResult();
	}
    
    @Transactional(readOnly=false)
    @Override
    public void insUsers(BookUser user) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().save(user);
        this.getHibernateTemplate().flush();
    }

    @Override
    public void updUsers(BookUser user) {
        this.getHibernateTemplate().update(user);
    }

    @Transactional(readOnly=false)
    @Override
    public void insBookMark(BookMark mark) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().saveOrUpdate(mark);
        this.getHibernateTemplate().flush();
    }

    @Transactional(readOnly=false)
    @Override
    public void delBookMark(BookMark mark) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().delete(mark);
        this.getHibernateTemplate().flush();
    }

    @Override
    public Integer getBookMarkCnt(BookSearchInfo bookSearchInfo) {
        Criteria cCrt = this.getSessionFactory().getCurrentSession().createCriteria(BookMark.class);
        cCrt.add(Restrictions.eq("loginId", bookSearchInfo.getLoginId()));
        return ((Number)cCrt.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<BookMark> getBookMark(BookSearchInfo bookSearchInfo) {
        Criteria rCrt = this.getSessionFactory().getCurrentSession().createCriteria(BookMark.class);
        rCrt.add(Restrictions.eq("loginId", bookSearchInfo.getLoginId()));
        rCrt.setFirstResult((Integer.parseInt(bookSearchInfo.getPage()) - 1) * Integer.parseInt(bookSearchInfo.getSize()));
        rCrt.setMaxResults(Integer.parseInt(bookSearchInfo.getSize()));
        if (bookSearchInfo.getSort().equals("title")) {
            rCrt.addOrder(Order.asc("title"));
        } else if (bookSearchInfo.getSort().equals("entDt")) {
            rCrt.addOrder(Order.desc("entDt"));
        }
        return rCrt.list();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<BookHistory> getBookHistory(BookHistory bookHistory) {
        Criteria crt = this.getSessionFactory().getCurrentSession().createCriteria(BookHistory.class);
        crt.add(Restrictions.eq("loginId", bookHistory.getLoginId()));
        crt.addOrder(Order.desc("entDt"));
        crt.setMaxResults(10);
        return crt.list();
    }

    @Override
    public void insBookHistory(BookHistory bookHistory) {
        this.getHibernateTemplate().setCheckWriteOperations(false);
        this.getHibernateTemplate().saveOrUpdate(bookHistory);
        this.getHibernateTemplate().flush();
    }

	
}
