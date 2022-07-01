package com.api.codingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class MovieDTO {

	@JsonAlias("Title")
	private String title;

	@JsonAlias("Year")
	private String year;

	@JsonAlias("imdbID")
	private String imdbId;

	@JsonAlias("Runtime")
	private String runTime;

	@JsonAlias("Response")
	private Boolean response;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbID) {
		this.imdbId = imdbID;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}
}
