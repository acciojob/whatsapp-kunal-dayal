package com.driver;

import java.time.LocalDateTime;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date date;

    public Message(int id, String content, Date timestamp) {
        this.id = ++id;
        this.content = content;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
