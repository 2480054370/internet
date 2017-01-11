package com.breadem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "chat")
public class Chat {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // 主键自增Id
    private String inputname;
    private String outputname;
    private String message;

    public Chat() {
    }

    public Chat(String inputname, String outputname, String message) {
        this.inputname = inputname;
        this.outputname = outputname;
        this.message = message;
    }

	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
	}

	public String getOutputname() {
		return outputname;
	}

	public void setOutputname(String outputname) {
		this.outputname = outputname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
