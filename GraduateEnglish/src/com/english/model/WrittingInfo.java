package com.english.model;

import java.io.Serializable;

public class WrittingInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String question;
	private String answer;
	private String imagePath;
	private String date;
	private String haveImage;
	private String title;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHaveImage() {
		return haveImage;
	}
	public void setHaveImage(String haveImage) {
		this.haveImage = haveImage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "WrittingInfo [id=" + id + ", question=" + question
				+ ", answer=" + answer + ", imagePath=" + imagePath + ", date="
				+ date + ", haveImage=" + haveImage + ", title=" + title + "]";
	}
	
}
