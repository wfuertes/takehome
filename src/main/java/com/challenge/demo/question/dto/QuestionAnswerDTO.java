package com.challenge.demo.question.dto;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionAnswer;
import com.challenge.demo.question.QuestionAnswerOption;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuestionAnswerDTO {

    private Long id;
    private Long questionId;
    private String answer;
    private boolean isCorrectAnswer;
    private Date createdAt;
    private Date updatedAt;

    @JsonInclude(Include.NON_EMPTY)
    private List<QuestionAnswerOptionDTO> options;

    @JsonCreator
    public QuestionAnswerDTO(@JsonProperty("id") Long id,
                             @JsonProperty("questionId") Long questionId,
                             @JsonProperty("answer") String answer,
                             @JsonProperty("isCorrectAnswer") boolean isCorrectAnswer,
                             @JsonProperty("createAt") Date createdAt,
                             @JsonProperty("updateAt") Date updatedAt,
                             @JsonProperty("options") List<QuestionAnswerOptionDTO> options) {
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
