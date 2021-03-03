package com.challenge.demo.question;

import com.challenge.demo.question.dto.QuestionAnswerDTO;
import com.challenge.demo.question.dto.QuestionDTO;
import com.challenge.demo.site.SiteRepository;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final SiteRepository siteRepository;
    private final QuestionRepository questionRepository;
    private final QuestionAnswerRepository answerRepository;
    private final QuestionAnswerOptionRepository optionRepository;

    public QuestionController(SiteRepository siteRepository,
                              QuestionRepository questionRepository,
                              QuestionAnswerRepository answerRepository,
                              QuestionAnswerOptionRepository optionRepository) {
        this.siteRepository = siteRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.optionRepository = optionRepository;
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        return siteRepository.findById(questionDTO.getSiteId())
                             .map(questionDTO::createQuestion)
                             .map(questionRepository::save)
                             .map(question -> ResponseEntity.status(HttpStatus.CREATED).body(QuestionDTO.build(question)))
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getQuestions() {
        return ResponseEntity.ok(questionRepository.findAll()
                                                   .stream()
                                                   .map(QuestionDTO::build)
                                                   .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable(value = "id") Long questionId,
                                                      @RequestBody QuestionDTO questionDTO) {
        return questionRepository.findById(questionId)
                                 .map(questionDTO::updateQuestion)
                                 .map(questionRepository::save)
                                 .map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable(value = "id") Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(question -> {
                                     questionRepository.delete(question);
                                     return ResponseEntity.ok(QuestionDTO.build(question));
                                 })
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable(value = "id") Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<QuestionAnswerDTO> createQuestionAnswers(@PathVariable(value = "id") Long questionId,
                                                                   @RequestBody QuestionAnswerDTO newQADto) {
        return questionRepository.findById(questionId)
                                 .map(question -> answerRepository.save(newQADto.transform(question)))
                                 .map(savedAnswer -> {
                                     List<QuestionAnswerOption> answerOptions = optionRepository.saveAll(newQADto.transform(savedAnswer));
                                     savedAnswer.setAnswerOptions(answerOptions);
                                     return new ResponseEntity<>(QuestionAnswerDTO.build(savedAnswer), HttpStatus.CREATED);
                                 })
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<QuestionAnswerDTO>> getQuestionAnswers(@PathVariable(value = "id") Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(question -> {
                                     QuestionAnswer questionAnswer = new QuestionAnswer();
                                     questionAnswer.setQuestion(Question.builder().questionId(questionId).build());
                                     return ResponseEntity.ok(answerRepository.findAll(Example.of(questionAnswer))
                                                                              .stream()
                                                                              .map(QuestionAnswerDTO::build)
                                                                              .collect(Collectors.toList()));
                                 }).orElse(ResponseEntity.notFound().build());
    }
}