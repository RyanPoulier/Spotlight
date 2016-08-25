package com.spotlight.core.util;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.beans.User;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.UserDao;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.dao.impl.UserDaoImpl;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/22/16.
 */
public class SpotlightUtils {

    private static final Logger LOGGER = Logger.getLogger(SpotlightUtils.class);
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

    public static boolean isNearbyIssue(Issue issue, double latitude, double longitude) {

        double distance = getDistance(latitude, issue.getLatitude(), longitude, issue.getLongitude(), 0.0, 0.0);
        LOGGER.info(issue.get_id().get$oid() + " - " + distance/1000 + "km");

        if (distance <= 5000) {
            return true;
        } else {
            return false;
        }
    }

    public static double getDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        //distance in meters
        return Math.sqrt(distance);
    }
}
