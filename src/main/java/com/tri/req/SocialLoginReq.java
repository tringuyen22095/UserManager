package com.tri.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocialLoginReq {
	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "idToken")
	private String idToken;

	@JsonProperty(value = "image")
	private String image;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "provide")
	private String provide;

	@JsonProperty(value = "token")
	private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvide() {
		return provide;
	}

	public void setProvide(String provide) {
		this.provide = provide;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setUserName(String email) {
		this.email = email;
	}

}
