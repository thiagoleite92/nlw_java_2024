package br.com.thiagoleite.certification_nlw.modules.students.usecases;

import br.com.thiagoleite.certification_nlw.modules.students.dto.VerifyIfHasCertificationDTO;
import br.com.thiagoleite.certification_nlw.modules.students.entities.CertificationStudentEntity;
import br.com.thiagoleite.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {

  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  public boolean execute(VerifyIfHasCertificationDTO dto) {
    List<CertificationStudentEntity> result =
      this.certificationStudentRepository.findBystudentEmailAndTechnology(
          dto.getEmail(),
          dto.getTechnology()
        );

    if (!result.isEmpty()) {
      return true;
    }

    return false;
  }
}
