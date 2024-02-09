package br.com.thiagoleite.certification_nlw.modules.questions.controllers;

import br.com.thiagoleite.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import br.com.thiagoleite.certification_nlw.modules.questions.dto.QuestionResultDTO;
import br.com.thiagoleite.certification_nlw.modules.questions.entities.AlternativesEntity;
import br.com.thiagoleite.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.thiagoleite.certification_nlw.modules.questions.repositories.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(
    @PathVariable String technology
  ) {
    List<QuestionEntity> questions =
      this.questionRepository.findByTechnology(technology);

    List<QuestionResultDTO> questionsResultsDTO = questions
      .stream()
      .map(question -> mapQuestionToDTO(question))
      .collect(Collectors.toList());

    return questionsResultsDTO;
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
    QuestionResultDTO questionResultDTO = QuestionResultDTO
      .builder()
      .id(question.getId())
      .technology(question.getTechnology())
      .description(question.getDescription())
      .build();

    List<AlternativesResultDTO> alternativesResultDTOs = question
      .getAlternatives()
      .stream()
      .map(alternative -> mapAlternativeDTO(alternative))
      .collect(Collectors.toList());

    questionResultDTO.setAlternatives(alternativesResultDTOs);

    return questionResultDTO;
  }

  static AlternativesResultDTO mapAlternativeDTO(
    AlternativesEntity alternativesResultDTO
  ) {
    return AlternativesResultDTO
      .builder()
      .id(alternativesResultDTO.getId())
      .description(alternativesResultDTO.getDescription())
      .build();
  }
}
