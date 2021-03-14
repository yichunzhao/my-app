package io.bootify.my_app.service;

import io.bootify.my_app.domain.Authors;
import io.bootify.my_app.domain.Books;
import io.bootify.my_app.model.AuthorsDTO;
import io.bootify.my_app.repos.AuthorsRepository;
import io.bootify.my_app.repos.BooksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final BooksRepository booksRepository;

    public AuthorsService(final AuthorsRepository authorsRepository,
                          final BooksRepository booksRepository) {
        this.authorsRepository = authorsRepository;
        this.booksRepository = booksRepository;
    }

    public List<AuthorsDTO> findAll() {
        return authorsRepository.findAll()
                .stream()
                .map(authors -> mapToDTO(authors, new AuthorsDTO()))
                .collect(Collectors.toList());
    }

    public AuthorsDTO get(final Long id) {
        return authorsRepository.findById(id)
                .map(authors -> mapToDTO(authors, new AuthorsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final AuthorsDTO authorsDTO) {
        final Authors authors = new Authors();
        mapToEntity(authorsDTO, authors);
        return authorsRepository.save(authors).getId();
    }

    public void update(final Long id, final AuthorsDTO authorsDTO) {
        final Authors authors = authorsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(authorsDTO, authors);
        authorsRepository.save(authors);
    }

    public void delete(final Long id) {
        authorsRepository.deleteById(id);
    }

    private AuthorsDTO mapToDTO(final Authors authors, final AuthorsDTO authorsDTO) {
        authorsDTO.setId(authors.getId());
        authorsDTO.setFirstName(authors.getFirstName());
        authorsDTO.setLastName(authors.getLastName());
        authorsDTO.setBookAuthors(authors.getBooks() == null ? null : authors.getBooks().stream()
                .map(Books::getId).collect(Collectors.toList()));
        return authorsDTO;
    }

    private Authors mapToEntity(final AuthorsDTO authorsDTO, final Authors authors) {
        authors.setFirstName(authorsDTO.getFirstName());
        authors.setLastName(authorsDTO.getLastName());
        if (authorsDTO.getBookAuthors() != null) {
            final List<Books> bookAuthors = booksRepository.findAllById(authorsDTO.getBookAuthors());
            if (bookAuthors.size() != authorsDTO.getBookAuthors().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of bookAuthorss not found");
            }
            authors.setBooks(bookAuthors.stream().collect(Collectors.toSet()));
        }
        return authors;
    }

}
