package com.challenge.demo.question.dto;

import com.challenge.demo.question.QuestionAnswer;
import com.challenge.demo.question.QuestionAnswerOption;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuestionAnswerOptionDTO {

    private final Long id;
    private final Long questionAnswerId;
    private final String optionText;
    private final Boolean pickable;
    private final Date createdAt;
    private final Date updatedAt;

    @JsonCreator
    public QuestionAnswerOptionDTO(@JsonProperty("id") Long id,
                                   @JsonProperty("questionAnswerId") Long questionAnswerId,
                                   @JsonProperty("optionText") String optionText,
                                   @JsonProperty("pickable") Boolean pickable,
                                   @JsonProperty("createdAt") Date createdAt,
                                   @JsonProperty("updatedAt") Date updatedAt) {
        this.id = id;
        this.questionAnswerId = questionAnswerId;
        this.optionText = optionText;
        this.pickable = pickable;
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
                option.isPickable(),
                option.getCreatedAt(),
                option.getUpdatedAt()
        );
    }
}
