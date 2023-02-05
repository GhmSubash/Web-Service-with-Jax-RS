package com.web.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.web.api.database.DataBaseClass;
import com.web.api.exception.DataNotFoundException;
import com.web.api.model.Message;

public class MessageService {
	
	private Map<Long,Message> messages = DataBaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1L,"Hello","Subash"));
		messages.put(2L, new Message(2L,"Hello","Jersey"));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagePaginated(int start,int size){
		List<Message> list = new ArrayList<>(messages.values());
		if(start+size>messages.size()) {
			return new ArrayList<Message>();
		}
		return list.subList(start, start + size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("Message with Id : "+id+" not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId()<=0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}

}
