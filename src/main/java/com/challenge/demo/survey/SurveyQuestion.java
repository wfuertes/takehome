package com.challenge.demo.survey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "survey_question")
public class SurveyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SurveyQuestion.Id id;

    @Column(name = "answered", nullable = false)
    private Boolean answered;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public static SurveyQuestion of(Long surveyId, boolean answered) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setAnswered(answered);
        surveyQuestion.setId(Id.of(surveyId));
        return surveyQuestion;
    }

    public static SurveyQuestion of(Long surveyId, Long questionId) {
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setId(Id.of(surveyId, questionId));
        return surveyQuestion;
    }

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "survey_id", nullable = false)
        private Long surveyId;

        @Column(name = "question_id", nullable = false)
        private Long questionId;

        public static Id of(Long surveyId) {
            SurveyQuestion.Id id = new SurveyQuestion.Id();
            id.setSurveyId(surveyId);
            return id;
        }

        public static Id of(Long surveyId, Long questionId) {
            SurveyQuestion.Id id = new SurveyQuestion.Id();
            id.setSurveyId(surveyId);
            id.setQuestionId(questionId);
            return id;
        }

        public Long getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(Long surveyId) {
            this.surveyId = surveyId;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }
}
