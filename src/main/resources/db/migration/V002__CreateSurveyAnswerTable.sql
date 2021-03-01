CREATE TABLE IF NOT EXISTS survey (
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    user_uuid           VARCHAR(40) NOT NULL,
    site_uuid           VARCHAR(40) NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS survey_question (
    survey_id           BIGINT NOT NULL,
    question_id         BIGINT NOT NULL,
    answered            BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (survey_id, question_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS survey_question_answer (
    survey_id           BIGINT NOT NULL,
    question_id         BIGINT NOT NULL,
    answer_id           BIGINT NOT NULL,
    option_id           BIGINT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (survey_id, question_id, answer_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;