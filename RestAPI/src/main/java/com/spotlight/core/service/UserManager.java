package com.spotlight.core.service;

import com.spotlight.core.beans.User;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.exceptions.InvalidUsernameOrPasswordException;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/21/16.
 */
public interface UserManager {

    User saveUser(User user) throws InvalidParameterException, UnknownHostException;
    User getUser(User user) throws InvalidParameterException, UnknownHostException, InvalidUsernameOrPasswordException;
}
