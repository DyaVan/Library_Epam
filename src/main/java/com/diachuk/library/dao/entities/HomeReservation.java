package com.diachuk.library.dao.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

/**
 * Created by VA-N_ on 19.12.2016.
 */
public class HomeReservation implements IReservation {

    private final String reservationType = "Home";
    private int id;
    private int bookId;
    private int userId;
    private Date dueDate;

    private User user;
    private Book book;

    @Override
    public String getType() {
        return reservationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public Date getDueTo() {
        return dueDate;
    }

    @Override
    public void setDueTo(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        HomeReservation that = (HomeReservation) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HomeReservation{");
        sb.append("id=").append(id);
        sb.append(", bookId=").append(bookId);
        sb.append(", userId=").append(userId);
        sb.append(", dueDate=").append(dueDate);
        sb.append('}');
        return sb.toString();
    }
}
