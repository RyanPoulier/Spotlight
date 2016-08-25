package com.spotlight.core.dao;

import com.spotlight.core.beans.Comment;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public interface CommentDao {

    Comment saveComment(Comment comment) throws UnknownHostException;
    Comment getComment(String id) throws UnknownHostException;
    List<Comment> getComments(String issueId) throws UnknownHostException;
}
