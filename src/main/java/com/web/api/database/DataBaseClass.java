package com.web.api.database;

import java.util.HashMap;
import java.util.Map;

import com.web.api.model.Message;
import com.web.api.model.Profile;

public class DataBaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
