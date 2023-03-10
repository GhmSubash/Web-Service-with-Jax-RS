package com.web.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.web.api.database.DataBaseClass;
import com.web.api.model.Profile;

public class ProfileService {

	private Map<String,Profile> profiles = DataBaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("subash_rohit", new Profile(1L,"subash_rohit","Subash","Ghimire"));
	}

	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeMessage(String profileName) {
		return profiles.remove(profileName);
	}
	
}
