package com.tri.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadReq {
	@JsonProperty(value = "fileName")
	private String fileName;
	@JsonProperty(value = "content")
	private String content;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
