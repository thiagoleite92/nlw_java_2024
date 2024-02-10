package br.com.thiagoleite.certification_nlw.modules.students.useCases;

import br.com.thiagoleite.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.thiagoleite.certification_nlw.modules.questions.repositories.QuestionRepository;
import br.com.thiagoleite.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import br.com.thiagoleite.certification_nlw.modules.students.dto.VerifyIfHasCertificationDTO;
import br.com.thiagoleite.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import br.com.thiagoleite.certification_nlw.modules.students.entities.CertificationStudentEntity;
import br.com.thiagoleite.certification_nlw.modules.students.entities.StudentyEntity;
import br.com.thiagoleite.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import br.com.thiagoleite.certification_nlw.modules.students.repositories.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCertificationAnswersUseCase {

  int correctAnswers = 0;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  @SuppressWarnings("null")
  public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto)
    throws Exception {
    var hasCertification =
      this.verifyIfHasCertificationUseCase.execute(
          new VerifyIfHasCertificationDTO(dto.getEmail(), dto.getTechnology())
        );

    if (hasCertification) {
      throw new Exception("Você já tirou sua certificação");
    }

    List<QuestionEntity> questionsEntity =
      this.questionRepository.findByTechnology(dto.getTechnology());
    List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

    dto
      .getQuestionAnswers()
      .stream()
      .forEach(questionAnswer -> {
        QuestionEntity question = questionsEntity
          .stream()
          .filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
          .findFirst()
          .get();

        var findCorrectAlternative = question
          .getAlternatives()
          .stream()
          .filter(alternative -> alternative.isCorrect())
          .findFirst()
          .get();

        if (
          findCorrectAlternative
            .getId()
            .equals(questionAnswer.getAlternativeID())
        ) {
          questionAnswer.setCorrect(true);
          correctAnswers = correctAnswers + 1;
        } else {
          questionAnswer.setCorrect(false);
        }

        var answerCertificationsEntity = AnswersCertificationsEntity
          .builder()
          .answerID(questionAnswer.getAlternativeID())
          .questionID(questionAnswer.getQuestionID())
          .isCorrect(questionAnswer.isCorrect())
          .build();

        answersCertifications.add(answerCertificationsEntity);
      });

    UUID studentID;

    Optional<StudentyEntity> student =
      this.studentRepository.findByEmail(dto.getEmail());

    if (student.isEmpty()) {
      StudentyEntity studentCreated = StudentyEntity
        .builder()
        .email(dto.getEmail())
        .build();

      studentRepository.save(studentCreated);
      studentID = studentCreated.getId();
    } else {
      studentID = student.get().getId();
    }

    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity
      .builder()
      .technology(dto.getTechnology())
      .studentID(studentID)
      .grade(correctAnswers)
      .answersCertificationsEntities(answersCertifications)
      .build();

    CertificationStudentEntity certificationStudentCreated = certificationStudentRepository.save(
      certificationStudentEntity
    );

    answersCertifications
      .stream()
      .forEach(answersCertification -> {
        answersCertification.setCertificationID(
          certificationStudentEntity.getId()
        );
        answersCertification.setCertificationStudentEntity(
          certificationStudentEntity
        );
      });

    certificationStudentEntity.setAnswersCertificationsEntities(
      answersCertifications
    );

    return certificationStudentCreated;
  }
}
