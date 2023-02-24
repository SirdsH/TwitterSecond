package cz.educanet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named
@RequestScoped
public class Twitter {
    private int tweetID;
    private String content;
    private String author;
    private int likes;
    private LocalDateTime createdAT;
    private LocalDateTime updatedAT;

    public Twitter() {
    }

    public Twitter(int tweetID, String content, String author, int likes, LocalDateTime createdAT, LocalDateTime updatedAT) {
        this.tweetID = tweetID;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.createdAT = createdAT;
        this.updatedAT = updatedAT;
    }

    public int getTweetID() {
        return tweetID;
    }

    public void setTweetID(int tweetID) {
        this.tweetID = tweetID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }
}
