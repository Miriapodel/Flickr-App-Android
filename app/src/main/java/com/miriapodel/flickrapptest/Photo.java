package com.miriapodel.flickrapptest;

public class Photo
{
    String author;
    String author_id;
    String image;
    String link;
    String title;
    String tags;

    public Photo(String author, String author_id, String image, String link, String title, String tags) {
        this.author = author;
        this.author_id = author_id;
        this.image = image;
        this.link = link;
        this.title = title;
        this.tags = tags;
    }

     String getAuthor() {
        return author;
    }

     String getAuthor_id() {
        return author_id;
    }

     String getImage() {
        return image;
    }

     String getLink() {
        return link;
    }

     String getTitle() {
        return title;
    }

     String getTags() {
        return tags;
    }
}
