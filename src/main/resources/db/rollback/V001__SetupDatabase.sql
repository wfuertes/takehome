DROP TABLE site;
DROP TABLE question;
DROP TABLE question_answer;
DROP TABLE question_answer_option;

DELETE FROM flyway_schema_history WHERE installed_rank = 1;