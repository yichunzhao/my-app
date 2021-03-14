package io.bootify.my_app.service;

import io.bootify.my_app.domain.Authors;
import io.bootify.my_app.domain.Books;
import io.bootify.my_app.model.BooksDTO;
import io.bootify.my_app.repos.AuthorsRepository;
import io.bootify.my_app.repos.BooksRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class BooksService {

    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;

    public BooksService(final BooksRepository booksRepository,
            final AuthorsRepository authorsRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
    }

    public List<BooksDTO> findAll() {
        return booksRepository.findAll()
                .stream()
                .map(books -> mapToDTO(books, new BooksDTO()))
                .collect(Collectors.toList());
    }

    public BooksDTO get(final Long id) {
        return booksRepository.findById(id)
                .map(books -> mapToDTO(books, new BooksDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final BooksDTO booksDTO) {
        final Books books = new Books();
        mapToEntity(booksDTO, books);
        return booksRepository.save(books).getId();
    }

    public void update(final Long id, final BooksDTO booksDTO) {
        final Books books = booksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(booksDTO, books);
        booksRepository.save(books);
    }

    public void delete(final Long id) {
        booksRepository.deleteById(id);
    }

    private BooksDTO mapToDTO(final Books books, final BooksDTO booksDTO) {
        booksDTO.setId(books.getId());
        booksDTO.setTitle(books.getTitle());
        booksDTO.setBookAuthors(books.getAuthors() == null ? null : books.getAuthors().stream()
                .map(Authors::getId).collect(Collectors.toList()));
        return booksDTO;
    }

    private Books mapToEntity(final BooksDTO booksDTO, final Books books) {
        books.setTitle(booksDTO.getTitle());
        if (booksDTO.getBookAuthors() != null) {
            final List<Authors> bookAuthors = authorsRepository.findAllById(booksDTO.getBookAuthors());
            if (bookAuthors.size() != booksDTO.getBookAuthors().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of bookAuthors not found");
            }
            books.setAuthors(bookAuthors.stream().collect(Collectors.toSet()));
        }
        return books;
    }

}
