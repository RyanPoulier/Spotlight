package com.spotlight.core.service;

import com.spotlight.core.beans.Comment;
import com.spotlight.core.exceptions.InvalidParameterException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public interface CommentManager {

    Comment saveNewComment(Comment comment) throws InvalidParameterException, UnknownHostException;
    List<Comment> getAllComments(String issueId) throws InvalidParameterException, UnknownHostException;
}
