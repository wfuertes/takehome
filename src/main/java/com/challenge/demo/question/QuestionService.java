package com.challenge.demo.question;

import com.challenge.demo.question.dto.QuestionAnswerDTO;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionAnswerOptionRepository questionAnswerOptionRepository;

    public QuestionService(QuestionAnswerRepository questionAnswerRepository,
                           QuestionAnswerOptionRepository questionAnswerOptionRepository) {
        this.questionAnswerRepository = questionAnswerRepository;
        this.questionAnswerOptionRepository = questionAnswerOptionRepository;
    }

    public void save(QuestionAnswerDTO answerDTO) {

    }
}
