package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Authors;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorsRepository extends JpaRepository<Authors, Long> {
}
