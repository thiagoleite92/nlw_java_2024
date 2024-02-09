package br.com.thiagoleite.certification_nlw.modules.questions.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "alternatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String description;

  private boolean isCorrect;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
