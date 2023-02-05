package com.web.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.web.api.database.DataBaseClass;
import com.web.api.model.Comment;
import com.web.api.model.Message;

public class CommentService {
	
	private Map<Long,Message> messages = DataBaseClass.getMessages();
	private Map<Long,Comment> comments;
	
	public List<Comment> getAllComments(long messageId){
		comments = getComments(messageId);
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		comments = getComments(messageId);
		return comments.get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		comments = getComments(messageId);
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		comments = getComments(messageId);
		if(comment.getId()<0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		comments = getComments(messageId);
		return comments.remove(commentId);
	}
	
	public Map<Long, Comment> getComments(long messageId){
		return messages.get(messageId).getComments();
	}

}
