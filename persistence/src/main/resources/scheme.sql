use
job_search_engine;

DELIMITER
$$
CREATE PROCEDURE generate_configs()
BEGIN
INSERT INTO `role` (`name`)
VALUES ('ADMIN');
INSERT INTO `role` (`name`)
VALUES ('USER');

INSERT INTO `user` (`name`, `password`)
VALUES ('Kiryl', '123');

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES (1, 2);
END $$
DELIMITER ;

CALL generate_configs();
drop procedure generate_configs;


DELIMITER
$$
CREATE PROCEDURE generate_user()
BEGIN
  DECLARE
i INT DEFAULT 0;
  WHILE
i < 300 DO
    INSERT INTO `user` (`name`,`password`) VALUES (
      concat('user_', i),
      concat('password_', i)
    );
    SET
i = i + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_user();
drop procedure generate_user;

DELIMITER
$$
CREATE PROCEDURE generate_user_role()
BEGIN
  DECLARE
i INT DEFAULT 0;
  DECLARE
user_id INT DEFAULT 2;
  WHILE
i < 300 DO
    INSERT INTO `user_role` (`user_id`,`role_id`) VALUES (
		user_id,
		2
    );
    SET
i = i + 1;
	SET
user_id = user_id + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_user_role();
drop procedure generate_user_role;

DELIMITER
$$
CREATE PROCEDURE generate_skill()
BEGIN
  DECLARE
i INT DEFAULT 0;
  WHILE
i < 300 DO
    INSERT INTO `skill` (`name`) VALUES (
      concat('skill_', i)
    );
    SET
i = i + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_skill();
drop procedure generate_skill;

DELIMITER
$$
CREATE PROCEDURE generate_vacancy()
BEGIN
  DECLARE
i INT DEFAULT 0;
  DECLARE
employer_id INT DEFAULT 1;
  WHILE
i < 300 DO
    INSERT INTO `vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`,`is_deleted`) VALUES (
      RAND()*(10000-500)+500,
      '2018-08-30 01:12:15',
      concat('employer_', i),
      concat('position_', i),
      concat('location_', i),
      0
    );
    SET
i = i + 1;
    SET
employer_id = employer_id + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_vacancy();
drop procedure generate_vacancy;

DELIMITER
$$
CREATE PROCEDURE generate_vacancy_skill()
BEGIN
  DECLARE
i INT DEFAULT 0;
  DECLARE
vacancy_id int default 1;
  DECLARE
skill_id int default 1;
  WHILE
i < 300 DO
    INSERT INTO `vacancy_skill` (`vacancy_id`, `skill_id`) VALUES (
      vacancy_id,
      skill_id
    );
    SET
i = i + 1;
    SET
vacancy_id = vacancy_id + 1;
    SET
skill_id = skill_id + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_vacancy_skill();
drop procedure generate_vacancy_skill;

DELIMITER
$$
CREATE PROCEDURE generate_job_application()
BEGIN
  DECLARE
i INT DEFAULT 0;
  DECLARE
id INT DEFAULT 1;
  DECLARE
user_id int default 1;
  DECLARE
vacancy_id int default 1;
  WHILE
i < 300 DO
    INSERT INTO `job_application` (`id`, `user_id`, `vacancy_id`, `response_date`, `salary`) VALUES (
      id,
      user_id,
      vacancy_id,
      '2018-08-30 01:12:15',
      (select `job_search_engine`.`vacancy`.`salary` from `job_search_engine`.`vacancy` where `job_search_engine`.`vacancy`.id = vacancy_id)
    );
    SET
i = i + 1;
    SET
id = id + 1;
    SET
user_id = user_id + 1;
    SET
vacancy_id = vacancy_id + 1;
END WHILE;
END$$
DELIMITER ;

CALL generate_job_application();
drop procedure generate_job_application;