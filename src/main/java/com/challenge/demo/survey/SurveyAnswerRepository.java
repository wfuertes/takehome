package com.challenge.demo.survey;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyQuestionAnswer, SurveyQuestionAnswer.Id> {
}
