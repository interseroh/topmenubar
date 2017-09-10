package de.interseroh.tmb.user.client;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class UserInfoResponse {

	private String username;
	private String email;

	public UserInfoResponse(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public UserInfoResponse setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserInfoResponse setEmail(String email) {
		this.email = email;
		return this;
	}
}
