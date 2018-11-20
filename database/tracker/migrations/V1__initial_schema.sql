CREATE TABLE time_entries (
  id       INT  NOT NULL AUTO_INCREMENT,
project_id INT,
user_id    INT,
date       DATE,
hours      INT,

PRIMARY KEY (id)
)
ENGINE = innodb
DEFAULT CHARSET = utf8;