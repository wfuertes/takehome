package com.challenge.demo.survey;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "survey_question_answer")
@EntityListeners(AuditingEntityListener.class)
public class SurveyQuestionAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SurveyQuestionAnswer.Id id;

    @Column(name = "option_id")
    private Long optionId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1L;

        @Column(name = "survey_id", nullable = false)
        private Long surveyId;

        @Column(name = "question_id", nullable = false)
        private Long questionId;

        @Column(name = "answer_id", nullable = false)
        private Long answerId;

        public Id() {
        }

        public Id(Long surveyId, Long questionId, Long answerId) {
            this.surveyId = surveyId;
            this.questionId = questionId;
            this.answerId = answerId;
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

        public Long getAnswerId() {
            return answerId;
        }

        public void setAnswerId(Long answerId) {
            this.answerId = answerId;
        }
    }
}
