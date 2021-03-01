package com.challenge.demo.question.dto;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionType;
import com.challenge.demo.site.Site;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class QuestionDTO {

    private final Long questionId;
    private final Long siteId;
    private final String question;
    private final QuestionType questionType;
    private final Date createdAt;
    private final Date updatedAt;

    @JsonCreator
    public QuestionDTO(@JsonProperty("questionId") Long questionId,
                       @JsonProperty("siteId") Long siteId,
                       @JsonProperty("question") String question,
                       @JsonProperty("questionType") QuestionType questionType,
                       @JsonProperty("createdAt") Date createdAt,
                       @JsonProperty("updateAt") Date updatedAt) {
        this.questionId = questionId;
        this.siteId = siteId;
        this.question = question;
        this.questionType = questionType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getSiteId() {
        return this.siteId;
    }

    public Question createQuestion(final Site site) {
        final Question question = new Question();
        question.setSite(site);
        question.setQuestion(this.question);
        question.setQuestionType(questionType);
        return question;
    }

    public Question updateQuestion(Question existingQuestion) {
        existingQuestion.setSite(new Site(siteId));
        existingQuestion.setQuestion(question);
        existingQuestion.setQuestionType(this.questionType);
        return existingQuestion;
    }

    public static QuestionDTO build(Question question) {
        return new QuestionDTO(question.getQuestionId(),
                               question.getSite().getSiteId(),
                               question.getQuestion(),
                               question.getQuestionType(),
                               question.getCreatedAt(),
                               question.getUpdatedAt());
    }
}
