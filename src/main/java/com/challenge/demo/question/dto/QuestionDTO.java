package com.challenge.demo.question.dto;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionType;
import com.challenge.demo.site.Site;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class QuestionDTO {

    private final Long questionId;
    private final Long siteId;
    private final String question;
    private final QuestionType questionType;
    private final Date createdAt;
    private final Date updatedAt;

    public QuestionDTO(Long questionId,
                       Long siteId,
                       String question,
                       QuestionType questionType,
                       Date createdAt,
                       Date updatedAt) {
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
