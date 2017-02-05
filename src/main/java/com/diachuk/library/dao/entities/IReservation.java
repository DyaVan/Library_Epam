package com.diachuk.library.dao.entities;

import java.util.Date;

/**
 * Created by VA-N_ on 27.01.2017.
 */
public interface IReservation {

    String getType();

     User getUser();

     void setUser(User user);

     Book getBook();

     void setBook(Book book) ;

     int getId();

     void setId(int id);

     int getBookId();

     void setBookId(int bookId);

     int getUserId();

     void setUserId(int userId);

     Date getDueTo();

     void setDueTo(Date dueDate);



}
