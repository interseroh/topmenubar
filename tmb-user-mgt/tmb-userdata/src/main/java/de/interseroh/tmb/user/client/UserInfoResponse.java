package de.interseroh.tmb.user.client;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public interface UserInfoResponse {

	String getUsername();
	String getEmail();
	void setUsername(String username);
	void setEmail(String email) ;
}
