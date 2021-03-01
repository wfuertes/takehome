package com.challenge.demo.survey;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, SurveyQuestion.Id> {
}
