package com.challenge.demo.question;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question_answer")
@EntityListeners(AuditingEntityListener.class)
public class QuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_answer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private Question question;

	private String answer;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCorrectAnswer;

    @OneToMany(mappedBy = "answer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<QuestionAnswerOption> answerOptions;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public List<QuestionAnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<QuestionAnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionAnswer that = (QuestionAnswer) o;
		return isCorrectAnswer == that.isCorrectAnswer &&
				Objects.equals(id, that.id) &&
				Objects.equals(question, that.question) &&
				Objects.equals(answer, that.answer) &&
				Objects.equals(createdAt, that.createdAt) &&
				Objects.equals(updatedAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, question, answer, isCorrectAnswer, createdAt, updatedAt);
	}
}
