package com.example.tarik.triggerwordsv1.Eyetracking_ReadingStories;

/**
 * Created by huanghe on 5/04/2017.
 */

public class Story {

    private String title;
    private String author;
    private String content;
    private String comment;

    public Story(String title, String author, String content, String comment) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return this.title + "\n" + this.author;
    }
}
