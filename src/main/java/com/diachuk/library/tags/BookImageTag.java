package com.diachuk.library.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by VA-N_ on 05.02.2017.
 */
public class BookImageTag extends TagSupport {

    private Integer bookId = 0;

    public void setBookId(Integer bookId) {
        if (bookId != null && bookId > 0) {
            this.bookId = bookId;
        }
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String imageHtml = "<img id=\"book-img\" class=\"book-logo book-logo-edit\" src=\"\\bookImages\\" +
                    bookId +".jpg\" alt=\"Book image\">";
            pageContext.getOut().print(imageHtml);
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}
