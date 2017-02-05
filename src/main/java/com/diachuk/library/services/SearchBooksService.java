package com.diachuk.library.services;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.implementations.MySql.MySqlBookDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VA-N_ on 11.01.2017.
 */
public class SearchBooksService  extends JsonResponseBuilder{

    private String searchLine;
    private String genreFilter;
    private int offset;

    public SearchBooksService(String genreFilter){
        this.genreFilter=genreFilter;
    }

    public SearchBooksService(String searchLine, Integer offset) {
        this.searchLine = searchLine;
        this.offset = offset;
    }

    public SearchBooksService(String searchLine, String genreFilter, Integer offset) {
        this.searchLine = searchLine;
        this.genreFilter = genreFilter;
        this.offset = offset;
    }

    private ArrayList<String> splitSearchLine() {
        ArrayList<String> separateWords = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\b[a-zA-Zа-яА-Я0-9]+?\\b");
        Matcher matcher = pattern.matcher(searchLine);

        while (matcher.find()) {
            separateWords.add(matcher.group());
        }
        return separateWords;
    }

    public ArrayList<Book> search() throws SQLException {
        ArrayList<Book> result;
        if (searchLine.isEmpty()) {
            return result = MySqlBookDAO.getInstance().findAllBooks(genreFilter, offset);
        }
        result = MySqlBookDAO.getInstance().findBySearchLineFullText(searchLine, genreFilter, offset);
        if (result.isEmpty() && offset == 0) {
            ArrayList<String> keyWords = splitSearchLine();
            result = MySqlBookDAO.getInstance().findByKeyWordsSimple(keyWords, genreFilter, offset);
        }
        return result;
    }

    public ArrayList<Book> getTop10() throws SQLException {
        return MySqlBookDAO.getInstance().findTop10(genreFilter);
    }


    public ArrayList<Book> getBooksRead(Integer userId) throws SQLException {
        return MySqlBookDAO.getInstance().getBooksRead(userId,genreFilter,offset);
    }

    public ArrayList<Book> getBooksToRead(Integer userId) throws SQLException {
        return MySqlBookDAO.getInstance().getBooksToRead(userId,genreFilter,offset);
    }

}
