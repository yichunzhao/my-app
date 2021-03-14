package io.bootify.my_app.rest;

import io.bootify.my_app.model.BooksDTO;
import io.bootify.my_app.service.BooksService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/bookss", produces = MediaType.APPLICATION_JSON_VALUE)
public class BooksController {

    private final BooksService booksService;

    public BooksController(final BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public ResponseEntity<List<BooksDTO>> getAllBookss() {
        return ResponseEntity.ok(booksService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BooksDTO> getBooks(@PathVariable final Long id) {
        return ResponseEntity.ok(booksService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createBooks(@RequestBody @Valid final BooksDTO booksDTO) {
        return new ResponseEntity<>(booksService.create(booksDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooks(@PathVariable final Long id,
            @RequestBody @Valid final BooksDTO booksDTO) {
        booksService.update(id, booksDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooks(@PathVariable final Long id) {
        booksService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
