-- liquibase formatted sql

-- changeset neo:001-create-categories
CREATE TABLE categories (
                            id      BIGSERIAL PRIMARY KEY,
                            name    VARCHAR(100) NOT NULL,
                            color   VARCHAR(20)
);

-- changeset neo:002-create-tasks
CREATE TABLE tasks (
                       id          BIGSERIAL PRIMARY KEY,
                       title       VARCHAR(200)      NOT NULL,
                       description TEXT,
                       completed   BOOLEAN           NOT NULL DEFAULT FALSE,
                       priority    INT               NOT NULL DEFAULT 3,
                       due_date    TIMESTAMP,
                       created_at  TIMESTAMP         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       category_id BIGINT,
                       CONSTRAINT fk_tasks_category
                           FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- changeset neo:003-indexes
CREATE INDEX idx_tasks_completed ON tasks(completed);
CREATE INDEX idx_tasks_category_id ON tasks(category_id);
