package com.challenge.demo.question.dto;

import com.challenge.demo.question.QuestionAnswer;
import com.challenge.demo.question.QuestionAnswerOption;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuestionAnswerOptionDTO {

    private final Long id;
    private final Long questionAnswerId;
    private final String optionText;
    private final Date createdAt;
    private final Date updatedAt;

    public QuestionAnswerOptionDTO(Long id, Long questionAnswerId, String optionText, Date createdAt, Date updatedAt) {
        this.id = id;
        this.questionAnswerId = questionAnswerId;
        this.optionText = optionText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public QuestionAnswerOption transform(QuestionAnswer questionAnswer) {
        QuestionAnswerOption option = new QuestionAnswerOption();
        option.setId(id);
        option.setAnswer(questionAnswer);
        option.setOptionText(optionText);
        option.setCreatedAt(createdAt);
        option.setUpdatedAt(updatedAt);
        return option;
    }

    public static QuestionAnswerOptionDTO build(QuestionAnswerOption option) {
        return new QuestionAnswerOptionDTO(
                option.getId(),
                option.getAnswer().getId(),
                option.getOptionText(),
                option.getCreatedAt(),
                option.getUpdatedAt()
        );
    }
}
