package com.challenge.demo.question.dto;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionAnswer;
import com.challenge.demo.question.QuestionAnswerOption;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuestionAnswerDTO {

    private Long id;
    private Long questionId;
    private String answer;
    private boolean isCorrectAnswer;
    private Date createdAt;
    private Date updatedAt;
    private List<QuestionAnswerOptionDTO> options;

    public QuestionAnswerDTO(Long id,
                             Long questionId,
                             String answer,
                             boolean isCorrectAnswer,
                             Date createdAt,
                             Date updatedAt,
                             List<QuestionAnswerOptionDTO> options) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrectAnswer = isCorrectAnswer;
        this.options = options;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public QuestionAnswer transform(final Question question) {
        final QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setId(id);
        questionAnswer.setQuestion(question);
        questionAnswer.setAnswer(answer);
        questionAnswer.setCorrectAnswer(isCorrectAnswer);
        return questionAnswer;
    }

    public List<QuestionAnswerOption> transform(final QuestionAnswer questionAnswer) {
        return options.stream().map(dto -> dto.transform(questionAnswer)).collect(Collectors.toList());
    }

    public static QuestionAnswerDTO build(final QuestionAnswer questionAnswer) {
        return new QuestionAnswerDTO(
                questionAnswer.getId(),
                questionAnswer.getQuestion().getQuestionId(),
                questionAnswer.getAnswer(),
                questionAnswer.isCorrectAnswer(),
                questionAnswer.getCreatedAt(),
                questionAnswer.getUpdatedAt(),
                questionAnswer.getAnswerOptions()
                              .stream()
                              .map(QuestionAnswerOptionDTO::build)
                              .collect(Collectors.toList())
        );
    }
}
