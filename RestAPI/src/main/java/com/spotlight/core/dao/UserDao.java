package com.spotlight.core.dao;

import com.spotlight.core.beans.User;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/21/16.
 */
public interface UserDao {

    User getUser(User user) throws UnknownHostException;
    User saveUser(User user) throws UnknownHostException;
    boolean checkEmail(String email) throws UnknownHostException;
}
