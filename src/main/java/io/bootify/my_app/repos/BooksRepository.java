package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BooksRepository extends JpaRepository<Books, Long> {
}
