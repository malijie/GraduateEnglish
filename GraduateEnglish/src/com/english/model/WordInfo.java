package com.english.model;

import java.io.Serializable;

public class WordInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String symbols;
	private String content;
	private String word;
	private String example;
	private boolean isKnown;
	private boolean isStranger;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSymbols() {
		return symbols;
	}
	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public boolean isKnown() {
		return isKnown;
	}
	public void setKnown(boolean isKnown) {
		this.isKnown = isKnown;
	}
	public boolean isStranger() {
		return isStranger;
	}
	public void setStranger(boolean isStranger) {
		this.isStranger = isStranger;
	}
	
	@Override
	public String toString() {
		return "Word [word=" + word + "id=" + id + ", symbols=" + symbols + ", content="
				+ content +  ", example=" + example
				+ ", isKnown=" + isKnown + ", isStranger=" + isStranger + "]";
	}
	
	
	
}
