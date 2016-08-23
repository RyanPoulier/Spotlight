package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Complaint;
import com.spotlight.core.dao.ComplaintDao;
import com.spotlight.core.dao.impl.ComplaintDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.ComplaintManager;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public class ComplaintManagerImpl implements ComplaintManager {

    private static final Logger LOGGER = Logger.getLogger(ComplaintManagerImpl.class);
    private ComplaintDao complaintDao;

    public ComplaintManagerImpl(){
        complaintDao = new ComplaintDaoImpl();
    }

    @Override
    public Complaint saveNewComplaint(Complaint complaint) throws UnknownHostException, InvalidParameterException {

        if (complaint == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (StringUtils.isBlank(complaint.getIssueId()) || StringUtils.isBlank(complaint.getUserId())) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (SpotlightUtils.validateUserId(complaint.getUserId()) == false && SpotlightUtils.validateIssueId(complaint.getIssueId()) == false) {
            String message = "User ID or Issue ID doesn't exist!";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return complaintDao.saveComplaint(complaint);
    }

    @Override
    public List<Complaint> getAllComplaints(String issueId) throws UnknownHostException {

        LOGGER.info("Retrieving the complaints for issue ID - " + issueId);
        return complaintDao.getComplaints(issueId);
    }
}
