package com.kbk.common.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class BookHibernateDaoSupport extends HibernateDaoSupport {
    @Autowired
    public void initSessionFactory(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }
}
