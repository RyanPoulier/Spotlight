package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Comment;
import com.spotlight.core.dao.CommentDao;
import com.spotlight.core.dao.impl.CommentDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.CommentManager;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public class CommentManagerImpl implements CommentManager {

    private static final Logger LOGGER = Logger.getLogger(CommentManagerImpl.class);
    private CommentDao commentDao;

    public CommentManagerImpl(){
        commentDao = new CommentDaoImpl();
    }

    @Override
    public Comment saveNewComment(Comment comment) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Saving new comment for Issue ID - " + comment.getIssueId());

        if (comment == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (StringUtils.isBlank(comment.getIssueId()) || StringUtils.isBlank(comment.getUserId())) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateUserId(comment.getUserId()) || !SpotlightUtils.validateIssueId(comment.getIssueId())) {
            String message = "User ID or Issue ID doesn't exist!";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return commentDao.saveComment(comment);
    }

    @Override
    public List<Comment> getAllComments(String issueId) throws InvalidParameterException, UnknownHostException {

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

        return commentDao.getComments(issueId);
    }
}
