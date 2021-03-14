package io.bootify.my_app.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class AuthorsDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    private List<Long> bookAuthors;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public List<Long> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(final List<Long> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

}
