package com.challenge.demo.question;

import com.challenge.demo.question.dto.QuestionAnswerDTO;
import com.challenge.demo.question.dto.QuestionDTO;
import com.challenge.demo.site.SiteRepository;
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

@RestController
@RequestMapping("/questions")
public class QuestionController {

	private final QuestionRepository questionRepository;
    private final SiteRepository siteRepository;
    private final QuestionAnswerRepository qaRepository;

    public QuestionController(QuestionRepository questionRepository, SiteRepository siteRepository, QuestionAnswerRepository qaRepository) {
        this.questionRepository = questionRepository;
        this.siteRepository = siteRepository;
        this.qaRepository = qaRepository;
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO incomingQuestion) {
        return siteRepository.findById(incomingQuestion.getSiteId())
                             .map(site -> {
                                 final Question newQ = QuestionDTO.createQuestion(incomingQuestion, site);
                                 return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(newQ)), HttpStatus.CREATED);
                             })
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

	@GetMapping
	public ResponseEntity<List<QuestionDTO>> getQuestions() {
		return ResponseEntity.ok(QuestionDTO.build(questionRepository.findAll()));
	}

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody Question incomingQuestion, @PathVariable(value = "id") Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(question -> {
                                     question.setQuestion(incomingQuestion.getQuestion());
                                     question.setSite(incomingQuestion.getSite());
                                     return ResponseEntity.ok(QuestionDTO.build(questionRepository.save(question)));
                                 })
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
		return questionRepository
				.findById(questionId)
				.map(question -> {
					final QuestionAnswer newQa = QuestionAnswerDTO.transform(newQADto, question);
					return new ResponseEntity<>(QuestionAnswerDTO.build(qaRepository.save(newQa)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

    @GetMapping("/{id}/answers")
    public ResponseEntity<List<QuestionAnswerDTO>> getQuestionAnswers(@PathVariable(value = "id") Long questionId) {
        return questionRepository.findById(questionId)
                                 .map(question -> ResponseEntity.ok(QuestionAnswerDTO.build(question.getAnswers())))
                                 .orElseGet(() -> ResponseEntity.notFound().build());
    }
}