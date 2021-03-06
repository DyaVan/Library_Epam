package com.diachuk.library.dao.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * Created by VA-N_ on 19.12.2016.
 */
public class BookLoan {

    private int id;
    private int userId;
    private int bookId;
    private String type;
    private Date tookDate;
    private Date dueDate;
    private Date returnDate;

    private User user;
    private Book book;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTookDate() {
        return tookDate;
    }

    public void setTookDate(Date tookDate) {
        this.tookDate = tookDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    //todo: is it okay to compare only by ids?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookLoan bookLoan = (BookLoan) o;

        return new EqualsBuilder()
                .append(id, bookLoan.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookLoan{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", bookId=").append(bookId);
        sb.append(", type='").append(type).append('\'');
        sb.append(", tookDate=").append(tookDate);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", returnDate=").append(returnDate);
        sb.append('}');
        return sb.toString();
    }

}
