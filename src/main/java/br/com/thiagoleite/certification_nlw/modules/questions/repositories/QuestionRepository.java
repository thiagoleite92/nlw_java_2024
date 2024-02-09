package br.com.thiagoleite.certification_nlw.modules.questions.repositories;

import br.com.thiagoleite.certification_nlw.modules.questions.entities.QuestionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository
  extends JpaRepository<QuestionEntity, UUID> {
  List<QuestionEntity> findByTechnology(String technology);
}
