package br.com.thiagoleite.certification_nlw.modules.students.controllers;

import br.com.thiagoleite.certification_nlw.modules.students.dto.VerifyIfHasCertificationDTO;
import br.com.thiagoleite.certification_nlw.modules.students.usecases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

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
}
