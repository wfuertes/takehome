package com.challenge.demo.survey.dto;

import com.challenge.demo.question.Question;
import com.challenge.demo.question.QuestionAnswer;
import com.challenge.demo.question.QuestionAnswerOption;
import com.challenge.demo.question.QuestionType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WidgetQuestionDTO {

    private final Long questionId;
    private final String question;
    private final QuestionType questionType;
    private final List<Answer> answers;

    @JsonCreator
    public WidgetQuestionDTO(@JsonProperty("questionId") Long questionId,
                             @JsonProperty("question") String question,
                             @JsonProperty("questionType") QuestionType questionType,
                             @JsonProperty("answers") List<Answer> answers) {
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }

    public static WidgetQuestionDTO build(Question question) {
        return new WidgetQuestionDTO(
                question.questionId(),
                question.question(),
                question.questionType(),
                question.answers()
                        .stream()
                        .map(Answer::build)
                        .collect(Collectors.toList())
        );
    }

    @JsonInclude(Include.NON_EMPTY)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Answer {

        private final Long answerId;
        private final String answer;
        private final List<Option> options;

        public Answer(Long answerId, String answer, List<Option> options) {
            this.answerId = answerId;
            this.answer = answer;
            this.options = options;
        }

        public static Answer build(QuestionAnswer questionAnswer) {
            return new Answer(questionAnswer.getId(),
                              questionAnswer.getAnswer(),
                              questionAnswer.getAnswerOptions()
                                            .stream()
                                            .map(Option::build)
                                            .collect(Collectors.toList()));
        }
    }

    @JsonInclude(Include.NON_NULL)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Option {

        private final Long optionId;
        private final String label;
        private final Boolean pickable;

        public Option(Long optionId, String label, Boolean pickable) {
            this.optionId = optionId;
            this.label = label;
            this.pickable = pickable;
        }

        public static Option build(QuestionAnswerOption option) {
            return new Option(option.getId(), option.getOptionText(), option.isPickable());
        }
    }
}
