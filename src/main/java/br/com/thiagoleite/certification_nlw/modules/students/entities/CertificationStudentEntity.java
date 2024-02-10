package br.com.thiagoleite.certification_nlw.modules.students.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "certifications")
@Builder
public class CertificationStudentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "student_id")
  private UUID studentID;

  @ManyToOne
  @JoinColumn(name = "student_id", insertable = false, updatable = false)
  @JsonManagedReference
  private StudentyEntity studentEntity;

  @Column(length = 100)
  private String technology;

  @Column(length = 10)
  private int grade;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(
    name = "answer_certification_id",
    insertable = false,
    updatable = false
  )
  @JsonManagedReference
  private List<AnswersCertificationsEntity> answersCertificationsEntities;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
