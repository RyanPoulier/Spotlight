package com.spotlight.core.dao;

import com.spotlight.core.beans.Vote;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public interface VoteDao {

    Vote saveVote(Vote vote) throws UnknownHostException;
    Vote getVote(String id) throws UnknownHostException;
    List<Vote> getVotes(String issueId) throws UnknownHostException;
}
