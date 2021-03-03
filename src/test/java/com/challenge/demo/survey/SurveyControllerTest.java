package com.challenge.demo.survey;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionRepository;
import com.challenge.demo.question.QuestionType;
import com.challenge.demo.site.Site;
import com.challenge.demo.site.SiteRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SurveyControllerTest {

    private SurveyController surveyController;

    private SiteRepository siteRepository;
    private SurveyRepository surveyRepository;
    private SurveyQuestionRepository surveyQuestionRepository;
    private QuestionRepository questionRepository;
    private SurveyAnswerRepository surveyAnswerRepository;

    @BeforeEach
    public void setup() {
        siteRepository = mock(SiteRepository.class);
        surveyRepository = mock(SurveyRepository.class);
        surveyQuestionRepository = mock(SurveyQuestionRepository.class);
        questionRepository = mock(QuestionRepository.class);
        surveyAnswerRepository = mock(SurveyAnswerRepository.class);

        surveyController = new SurveyController(siteRepository,
                                                questionRepository,
                                                surveyRepository,
                                                surveyQuestionRepository,
                                                surveyAnswerRepository);
    }

    @Test
    public void createSurvey_whenSiteDoesExists_returnsNotFound() {
        String userUuid = "817ea573-f15a-4122-913a-f73ed78d4b3e";
        String siteUuid = "f0ec3d9d-17f8-45fe-a5bf-a4c8ec3e2a2e";

        when(siteRepository.findOne(any())).thenReturn(Optional.empty());
        ResponseEntity<?> response = surveyController.createSurvey(userUuid, siteUuid);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "Site found for siteUuid: f0ec3d9d-17f8-45fe-a5bf-a4c8ec3e2a2e");
    }

    @Test
    public void createSurvey_whenSiteExists_createsSiteSurveyAndReturnQuestions() {
        String userUuid = "817ea573-f15a-4122-913a-f73ed78d4b3e";
        String siteUuid = "f0ec3d9d-17f8-45fe-a5bf-a4c8ec3e2a2e";

        Site site = Site.builder()
                        .siteId(1L)
                        .siteUUID(UUID.fromString(siteUuid))
                        .url("www.dot.com")
                        .build();
        Survey survey = new Survey(1L, UUID.fromString(siteUuid), UUID.fromString(userUuid), new Date());
        Question question = Question.builder()
                                    .questionId(1L)
                                    .question("Test?")
                                    .questionType(QuestionType.TRIVIA)
                                    .site(site)
                                    .answers(Collections.emptyList())
                                    .build();

        SurveyQuestion surveyQuestion = SurveyQuestion.of(survey.getId(), question.questionId());

        when(siteRepository.findOne(any())).thenReturn(Optional.of(site));
        when(surveyRepository.save(any())).thenReturn(survey);
        when(questionRepository.findAll(any(Example.class))).thenReturn(Collections.singletonList(question));
        when(surveyQuestionRepository.saveAll(anyList())).thenReturn(Collections.singletonList(surveyQuestion));

        ResponseEntity<?> response = surveyController.createSurvey(userUuid, siteUuid);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ArgumentCaptor<Survey> surveyCaptor = ArgumentCaptor.forClass(Survey.class);
        verify(surveyRepository).save(surveyCaptor.capture());
        assertEquals(surveyCaptor.getValue().getUserUuid(), UUID.fromString(userUuid));
        assertEquals(surveyCaptor.getValue().getSiteUuid(), UUID.fromString(siteUuid));

        ArgumentCaptor<Iterable> surveyQuestionCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(surveyQuestionRepository).saveAll(surveyQuestionCaptor.capture());
        Set<SurveyQuestion> surveyQuestions = Sets.newHashSet(surveyQuestionCaptor.getValue());
        assertEquals(surveyQuestions.size(), 1);
        assertEquals(surveyQuestions.iterator().next().getId(), surveyQuestion.getId());
    }

}