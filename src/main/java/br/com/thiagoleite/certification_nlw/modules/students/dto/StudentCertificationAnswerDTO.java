package br.com.thiagoleite.certification_nlw.modules.students.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificationAnswerDTO {

  private String email;
  private String technology;
  private List<QuestionAnswersDTO> questionAnswers;
}
