package com.rc.repo;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepository {

    @Autowired
    private DataAccess dataAccess;

    protected Session getSession() {
        return dataAccess.getSession();
    }
}
