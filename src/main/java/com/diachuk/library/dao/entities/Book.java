package com.diachuk.library.dao.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by VA-N_ on 03.12.2016.
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private String genre;
    private int year;
    private int pages;
    private int amountForHome;
    private int availableForHome;
    private int amountInRRoom;
    private int availableInRRoom;
    private String description;
    private int readersRate;
    private int numberOfVotes;
    private String image;
    private int currentUserVote;
    private boolean isBanned;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReadersRate() {
        return readersRate;
    }

    public void setReadersRate(int readersRate) {
        this.readersRate = readersRate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getAmountForHome() {
        return amountForHome;
    }

    public void setAmountForHome(int amountForHome) {
        this.amountForHome = amountForHome;
    }

    public int getAvailableForHome() {
        return availableForHome;
    }

    public void setAvailableForHome(int availableForHome) {
        this.availableForHome = availableForHome;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAmountInRRoom() {
        return amountInRRoom;
    }

    public void setAmountInRRoom(int amountInRRoom) {
        this.amountInRRoom = amountInRRoom;
    }

    public int getAvailableInRRoom() {
        return availableInRRoom;
    }

    public void setAvailableInRRoom(int availableInRRoom) {
        this.availableInRRoom = availableInRRoom;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCurrentUserVote() {
        return currentUserVote;
    }

    public void setCurrentUserVote(int currentUserVote) {
        this.currentUserVote = currentUserVote;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append(", pages=").append(pages);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder()
                .append(id, book.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}