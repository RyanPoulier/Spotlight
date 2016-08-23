package com.spotlight.core.util;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.beans.User;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.UserDao;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.dao.impl.UserDaoImpl;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/22/16.
 */
public class SpotlightUtils {

    private static UserDao userDao;
    private static IssueDao issueDao;

    static {
        userDao = new UserDaoImpl();
        issueDao = new IssueDaoImpl();
    }

    public static boolean validateUserId(String id) throws UnknownHostException {

        return userDao.validateUser(id);
    }

    public static boolean validateIssueId(String id) throws UnknownHostException {

        Issue issue = issueDao.getIssue(id);

        if (issue == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String getUserNameById(String id) throws UnknownHostException {

        User user = userDao.getUser(id);

        return user.getFirstName() + " " + user.getLastName();
    }
}
