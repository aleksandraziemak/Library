package com.library.model;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private String title;
    private Author author;
    private UUID id;

    public Book(UUID id) {
        this.id = id;
    }

    public Book(String title, Author author, UUID id) {
        this.title = title;
        this.author = author;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public UUID getId() {
        return id;
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return "Title: " + title + ", Author: " + author.getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
