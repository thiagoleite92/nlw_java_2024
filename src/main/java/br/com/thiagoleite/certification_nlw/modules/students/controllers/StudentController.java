package br.com.thiagoleite.certification_nlw.modules.students.controllers;

import br.com.thiagoleite.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import br.com.thiagoleite.certification_nlw.modules.students.dto.VerifyIfHasCertificationDTO;
import br.com.thiagoleite.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import br.com.thiagoleite.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  @Autowired
  private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

  @PostMapping("/verifyIfHasCertification")
  public String verifyIfHasCertification(
    @RequestBody VerifyIfHasCertificationDTO dto
  ) {
    boolean result = this.verifyIfHasCertificationUseCase.execute(dto);

    if (result) {
      return "Usuário já tem certificação";
    }

    return "Usuário pode fazer a prova";
  }

  @PostMapping("/certification/answer")
  public ResponseEntity<Object> certificationAnswer(
    @RequestBody StudentCertificationAnswerDTO dto
  ) throws Exception {
    try {
      var result = this.studentCertificationAnswersUseCase.execute(dto);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
