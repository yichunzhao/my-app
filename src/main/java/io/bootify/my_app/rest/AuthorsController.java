package io.bootify.my_app.rest;

import io.bootify.my_app.model.AuthorsDTO;
import io.bootify.my_app.service.AuthorsService;
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
@RequestMapping(value = "/api/authorss", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorsController {

    private final AuthorsService authorsService;

    public AuthorsController(final AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorsDTO>> getAllAuthorss() {
        return ResponseEntity.ok(authorsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorsDTO> getAuthors(@PathVariable final Long id) {
        return ResponseEntity.ok(authorsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAuthors(@RequestBody @Valid final AuthorsDTO authorsDTO) {
        return new ResponseEntity<>(authorsService.create(authorsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthors(@PathVariable final Long id,
            @RequestBody @Valid final AuthorsDTO authorsDTO) {
        authorsService.update(id, authorsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthors(@PathVariable final Long id) {
        authorsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
