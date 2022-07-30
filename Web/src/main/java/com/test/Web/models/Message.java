package com.test.Web.models;

import javax.persistence.*;

@Entity
@Table
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;
    private String tag;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Message() {

    }

    public Message(String tag, String text, User user) {
        this.author = user;
        this.tag = tag;
        this.text = text;
    }

    public String getAuthorName() {
        return author != null ? author.getName() : "<none>";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
