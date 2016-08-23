package com.spotlight.core.service;

import com.spotlight.core.beans.Vote;
import com.spotlight.core.exceptions.InvalidParameterException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public interface VoteManager {

    Vote saveNewVote(Vote vote) throws InvalidParameterException, UnknownHostException;
    List<Vote> getVotes(String issueId) throws InvalidParameterException, UnknownHostException;
}
