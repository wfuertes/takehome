package com.challenge.demo.survey;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionRepository;
import com.challenge.demo.site.Site;
import com.challenge.demo.site.SiteRepository;
import com.challenge.demo.survey.dto.WidgetQuestionAnswerDTO;
import com.challenge.demo.survey.dto.WidgetQuestionDTO;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SiteRepository siteRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final QuestionRepository questionRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyController(SiteRepository siteRepository,
                            QuestionRepository questionRepository,
                            SurveyRepository surveyRepository,
                            SurveyQuestionRepository surveyQuestionRepository,
                            SurveyAnswerRepository surveyAnswerRepository) {
        this.siteRepository = siteRepository;
        this.surveyRepository = surveyRepository;
        this.surveyQuestionRepository = surveyQuestionRepository;
        this.questionRepository = questionRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    @PostMapping("/site/{siteUuid}/questions")
    public ResponseEntity<?> createSurvey(@RequestHeader("userUuid") String userUuid,
                                          @PathVariable("siteUuid") String siteUuid) {

        Optional<Site> site = siteRepository.findOne(Example.of(Site.builder()
                                                                    .siteUUID(UUID.fromString(siteUuid))
                                                                    .build()));

        if (!site.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Site found for siteUuid: " + siteUuid);
        }

        Survey survey = surveyRepository.save(new Survey(UUID.fromString(userUuid), site.get().siteUUID()));
        List<Question> questions = questionRepository.findAll(Example.of(Question.builder().site(site.get()).build()));
        surveyQuestionRepository.saveAll(questions.stream()
                                                  .map(question -> SurveyQuestion.of(survey.getId(), question.questionId()))
                                                  .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(questions.stream()
                                            .map(WidgetQuestionDTO::build)
                                            .collect(Collectors.toList()));
    }

    @GetMapping("/site/{siteUuid}/questions")
    public ResponseEntity<?> getSurveyQuestions(@RequestHeader("userUuid") String userUuid,
                                                @PathVariable("siteUuid") String siteUuid) {
        Optional<Site> site = siteRepository.findOne(Example.of(Site.builder()
                                                                    .siteUUID(UUID.fromString(siteUuid))
                                                                    .build()));

        if (!site.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Site not found for siteUuid: " + siteUuid);
        }

        return surveyRepository.findOne(Example.of(new Survey(UUID.fromString(userUuid), site.get().siteUUID())))
                               .map(survey -> {
                                   List<SurveyQuestion> surveyQuestions =
                                           surveyQuestionRepository.findAll(
                                                   Example.of(SurveyQuestion.of(survey.getId(), false)));

                                   if (surveyQuestions.isEmpty()) {
                                       return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                            .body("No missing questions to be answered for survey: " + survey.getId());
                                   }

                                   return ResponseEntity.ok(
                                           questionRepository.findAllById(surveyQuestions.stream()
                                                                                         .map(sq -> sq.getId().getQuestionId())
                                                                                         .collect(Collectors.toList()))
                                                             .stream()
                                                             .map(WidgetQuestionDTO::build)
                                                             .collect(Collectors.toList())
                                   );
                               })
                               .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                     .body("No Survey found for Site: " + siteUuid + ", and User: " + userUuid));
    }

    @PostMapping("/site/{siteUuid}/questions/{questionId}/answer")
    public ResponseEntity<?> registerUserAnswer(@RequestHeader("userUuid") String userUuid,
                                                @PathVariable("siteUuid") String siteUuid,
                                                @PathVariable("questionId") Long questionId,
                                                @RequestBody WidgetQuestionAnswerDTO answerDTO) {

        Optional<Survey> survey = surveyRepository.findOne(Example.of(new Survey(UUID.fromString(userUuid), UUID.fromString(siteUuid))));

        if (!survey.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No Survey found for Site: " + siteUuid + ", and User: " + userUuid);
        }

        Optional<SurveyQuestion> surveyQuestion =
                surveyQuestionRepository.findOne(Example.of(SurveyQuestion.of(survey.get().getId(), questionId)));

        if (!surveyQuestion.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("SurveyQuestion not found for surveyId: " + survey.get()
                                                                                         .getId() + ", and questionId: " + questionId);

        }

        if (surveyQuestion.get().getAnswered()) {
            return ResponseEntity.status(HttpStatus.OK)
                                 .body("SurveyQuestion already answered for surveyId: " + survey.get()
                                                                                                .getId() + ", and questionId: " + questionId);
        }

        SurveyQuestion surveyQuestionAnswered = surveyQuestion.get();
        surveyQuestionAnswered.setAnswered(true);
        surveyQuestionRepository.save(surveyQuestionAnswered);

        surveyAnswerRepository.saveAll(answerDTO.transform(surveyQuestion.get()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
