package io.bootify.my_app.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class BooksDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    private List<Long> bookAuthors;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<Long> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(final List<Long> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

}
