INSERT INTO `job_search_engine`.`role` (`name`)
VALUES ('ADMIN');
INSERT INTO `job_search_engine`.`role` (`name`)
VALUES ('USER');

INSERT INTO `user` (`name`, `password`)
VALUES ('Kiryl', '123');
INSERT INTO `user` (`name`, `password`)
VALUES ('user_1', '123');
INSERT INTO `user` (`name`, `password`)
VALUES ('user_2', '123');


INSERT INTO `job_search_engine`.`user_role` (`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `job_search_engine`.`user_role` (`user_id`, `role_id`)
VALUES (1, 2);
INSERT INTO `job_search_engine`.`user_role` (`user_id`, `role_id`)
VALUES (2, 2);

INSERT INTO `job_search_engine`.`skill` (`name`)
VALUES (concat('skill_', 1));
INSERT INTO `job_search_engine`.`skill` (`name`)
VALUES (concat('skill_', 2));
INSERT INTO `job_search_engine`.`skill` (`name`)
VALUES (concat('skill_', 3));

INSERT INTO `job_search_engine`.`vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`, `is_deleted`)
VALUES ('300',
        '2018-08-30 01:12:15',
        concat('employer_', 4),
        concat('position_', 1),
        concat('location_', 1),
        0);
INSERT INTO `job_search_engine`.`vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`, `is_deleted`)
VALUES ('500',
        '2018-08-30 01:12:15',
        concat('employer_', 3),
        concat('position_', 2),
        concat('location_', 1),
        0);
INSERT INTO `job_search_engine`.`vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`, `is_deleted`)
VALUES ('700',
        '2018-08-30 01:12:15',
        concat('employer_', 2),
        concat('position_', 2),
        concat('location_', 1),
        0);
INSERT INTO `job_search_engine`.`vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`, `is_deleted`)
VALUES ('700',
        '2018-08-30 01:12:15',
        concat('employer_', 1),
        concat('position_', 2),
        concat('location_', 1),
        0);
INSERT INTO `job_search_engine`.`vacancy` (`salary`, `placement_date`, `employer`, `position`, `location`, `is_deleted`)
VALUES ('700',
        '2018-08-30 01:12:15',
        concat('employer_', 5),
        concat('position_', 1),
        concat('location_', 1),
        0);

INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (1, 1);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (1, 2);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (3, 1);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (4, 1);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (3, 2);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (4, 2);
INSERT INTO `job_search_engine`.`vacancy_skill` (`vacancy_id`, `skill_id`)
VALUES (2, 2);

INSERT INTO `job_search_engine`.`job_application` (`user_id`, `vacancy_id`, `response_date`, `salary`)
VALUES (1,
        1,
        '2018-08-30 01:12:15',
        (select `job_search_engine`.`vacancy`.`salary`
         from `job_search_engine`.`vacancy`
         where `job_search_engine`.`vacancy`.id = 1));

INSERT INTO `job_search_engine`.`job_application` (`user_id`, `vacancy_id`, `response_date`, `salary`)
VALUES (1,
        2,
        '2018-08-30 01:12:15',
        (select `job_search_engine`.`vacancy`.`salary`
         from `job_search_engine`.`vacancy`
         where `job_search_engine`.`vacancy`.id = 2));

INSERT INTO `job_search_engine`.`job_application` (`user_id`, `vacancy_id`, `response_date`, `salary`)
VALUES (1,
        3,
        '2018-08-30 01:12:15',
        (select `job_search_engine`.`vacancy`.`salary`
         from `job_search_engine`.`vacancy`
         where `job_search_engine`.`vacancy`.id = 3));

INSERT INTO `job_search_engine`.`job_application` (`user_id`, `vacancy_id`, `response_date`, `salary`)
VALUES (1,
        4,
        '2018-08-30 01:12:15',
        (select `job_search_engine`.`vacancy`.`salary`
         from `job_search_engine`.`vacancy`
         where `job_search_engine`.`vacancy`.id = 4));