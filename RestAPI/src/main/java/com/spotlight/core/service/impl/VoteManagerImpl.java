package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Vote;
import com.spotlight.core.dao.VoteDao;
import com.spotlight.core.dao.impl.VoteDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.VoteManager;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public class VoteManagerImpl implements VoteManager {

    private static final Logger LOGGER = Logger.getLogger(VoteManagerImpl.class);
    private VoteDao voteDao;

    public VoteManagerImpl(){
        voteDao = new VoteDaoImpl();
    }

    @Override
    public Vote saveNewVote(Vote vote) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Saving vote for issue - " + vote.getIssueId());

        if (vote == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (StringUtils.isBlank(vote.getIssueId()) || StringUtils.isBlank(vote.getUserId())) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateIssueId(vote.getIssueId()) | !SpotlightUtils.validateUserId(vote.getUserId())) {
            String message = "Issue ID or User ID doesn't exist!";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return voteDao.saveVote(vote);
    }

    @Override
    public List<Vote> getVotes(String issueId) throws InvalidParameterException, UnknownHostException {

        if (StringUtils.isBlank(issueId)) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateIssueId(issueId)) {
            String message = "Issue ID doesn't exist!";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return voteDao.getVotes(issueId);
    }
}
