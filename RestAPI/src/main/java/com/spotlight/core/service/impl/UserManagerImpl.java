package com.spotlight.core.service.impl;

import com.spotlight.core.beans.User;
import com.spotlight.core.dao.UserDao;
import com.spotlight.core.dao.impl.UserDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.exceptions.InvalidUsernameOrPasswordException;
import com.spotlight.core.service.UserManager;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/21/16.
 */
public class UserManagerImpl implements UserManager {

    private static final Logger LOGGER = Logger.getLogger(UserManagerImpl.class);
    private UserDao userDao;

    public UserManagerImpl(){
        userDao = new UserDaoImpl();
    }

    @Override
    public User saveUser(User user) throws InvalidParameterException, UnknownHostException {

        if (user == null){
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (userDao.checkEmail(user.getEmail())) {
            String message = "Username not available";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return userDao.saveUser(user);
    }

    @Override
    public User getUser(User user) throws InvalidParameterException, UnknownHostException, InvalidUsernameOrPasswordException {

        if (user == null){
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        User retrievedUser = userDao.getUser(user);

        if (retrievedUser == null){
            String message = "Invalid Username or password";
            LOGGER.error(message);
            throw new InvalidUsernameOrPasswordException(message);
        }

        return retrievedUser;
    }
}
