package com.challenge.demo.survey.dto;

import com.challenge.demo.survey.SurveyQuestion;
import com.challenge.demo.survey.SurveyQuestionAnswer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WidgetQuestionAnswerDTO {

    private final List<SelectedAnswer> selectedAnswers;

    @JsonCreator
    public WidgetQuestionAnswerDTO(@JsonProperty("selectedAnswers") List<SelectedAnswer> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public List<SurveyQuestionAnswer> transform(SurveyQuestion surveyQuestion) {
        return selectedAnswers.stream()
                              .map(selectedAnswer -> {
                                  SurveyQuestionAnswer surveyQuestionAnswer = new SurveyQuestionAnswer();
                                  surveyQuestionAnswer.setId(new SurveyQuestionAnswer.Id(surveyQuestion.getId().getSurveyId(),
                                                                                         surveyQuestion.getId().getQuestionId(),
                                                                                         selectedAnswer.answerId));
                                  surveyQuestionAnswer.setOptionId(selectedAnswer.selectedOptionId);
                                  return surveyQuestionAnswer;
                              }).collect(Collectors.toList());
    }

    @JsonInclude(Include.NON_NULL)
    public static class SelectedAnswer {

        private final Long answerId;
        private final Long selectedOptionId;

        @JsonCreator
        public SelectedAnswer(@JsonProperty("answerId") Long answerId,
                              @JsonProperty("selectedOptionId") Long selectedOptionId) {
            this.answerId = answerId;
            this.selectedOptionId = selectedOptionId;
        }
    }

}
