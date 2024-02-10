package br.com.thiagoleite.certification_nlw.modules.students.repositories;

import br.com.thiagoleite.certification_nlw.modules.students.entities.StudentyEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentyEntity, UUID> {
  public Optional<StudentyEntity> findByEmail(String email);
}
