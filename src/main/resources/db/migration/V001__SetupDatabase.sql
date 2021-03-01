CREATE TABLE IF NOT EXISTS site (
  site_id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  site_uuid     VARCHAR(40) NOT NULL,
  url           VARCHAR(256) NOT NULL,
  created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS question (
    question_id     BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    site_id         BIGINT NOT NULL,
    question        VARCHAR(250) NOT NULL,
    question_type   VARCHAR(64) NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS question_answer (
    question_answer_id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id         BIGINT NOT NULL,
    answer              VARCHAR(250) NOT NULL,
    is_correct_answer   BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS question_answer_option (
    id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_answer_id  BIGINT NOT NULL,
    option_text         VARCHAR(250),
    pickable            BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;


-- INSERTING TEST DATA
INSERT INTO site (site_id, site_uuid, url) VALUES (1, '07d85855-b7c5-4dd9-8177-73354f724452', 'www.mysite.com');

-- TRIVIA (one correct answer)
INSERT INTO question (question_id, site_id, question, question_type) VALUES (1, 1, 'How many toes does a pig have?', 'TRIVIA');
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (1, 1, '4 toes', true);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (2, 1, '2 toes', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (3, 1, '1 toes', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (4, 1, 'The do not have toes silly', false);

-- POLL (no correct answers)
INSERT INTO question (question_id, site_id, question, question_type) VALUES (2, 1, 'What is your favorite car brand?', 'POLL');
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (5, 2, 'Nissan', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (6, 2, 'Honda', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (7, 2, 'Audi', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (8, 2, 'BMW', false);

-- CHECKBOX (zero or more correct answers)
INSERT INTO question (question_id, site_id, question, question_type) VALUES (3, 1, 'What colors do you like?', 'CHECKBOX');
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (9, 3, 'Red', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (10, 3, 'Blue', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (11, 3, 'Yellow', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (12, 3, 'Green', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (13, 3, 'Black', false);
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (14, 3, 'Purple', false);

-- MATRIX (just one option is selectable, there is no right nor wrong answers)
INSERT INTO question (question_id, site_id, question, question_type) VALUES (4, 1, 'Please tell us a bit about yourself?', 'MATRIX');
INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (15, 4, 'Age/Gender', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (1, 15, '< 18', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (2, 15, '18 to 35', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (3, 15, '35 to 55', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (4, 15, '> 55', false);

INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (16, 4, 'Male', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (5, 16, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (6, 16, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (7, 16, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (8, 16, null, true);

INSERT INTO question_answer (question_answer_id, question_id, answer, is_correct_answer) VALUES (17, 4, 'Female', false);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (9, 17, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (10, 17, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (11, 17, null, true);
INSERT INTO question_answer_option (id, question_answer_id, option_text, pickable) VALUES (12, 17, null, true);