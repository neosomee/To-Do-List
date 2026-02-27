package com.example.to_do_list.service;

import com.example.to_do_list.model.Task;
import com.example.to_do_list.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        logger.info("Вызван конструктор TaskService");
    }

    public Task createTask(Task task) {
        logger.info("Вызван метод создания задачи: {}", task.getTitle());
        logger.debug("Сохраняем новую задачу в базу данных: {}", task);
        Task savedTask = taskRepository.save(task);
        logger.info("Задача успешно создана с ID: {}", savedTask.getId());
        return savedTask;
    }

    public Optional<Task> findTask(Long id) {
        logger.info("Вызван метод поиска задачи ID: {}", id);
        Optional<Task> taskOpt = taskRepository.findById(id);

        if (taskOpt.isPresent()) {
            logger.debug("Задача найдена: {}", taskOpt.get());
        } else {
            logger.warn("Задача с ID {} не найдена", id);
        }

        return taskOpt;
    }

    public Task editTask(Task task) {
        logger.info("Вызван метод редактирования задачи ID: {}", task.getId());

        if (task.getId() == null || !taskRepository.existsById(task.getId())) {
            logger.error("Задача с ID {} не существует для редактирования", task.getId());
            return null;
        }

        logger.debug("Обновляем задачу: {}", task);
        Task updatedTask = taskRepository.save(task);
        logger.info("Задача с ID {} успешно обновлена", updatedTask.getId());
        return updatedTask;
    }

    public void deleteTask(Long id) {
        logger.info("Вызван метод удаления задачи ID: {}", id);
        logger.debug("Проверяем существование задачи ID: {}", id);

        if (!taskRepository.existsById(id)) {
            logger.warn("Попытка удалить несуществующую задачу с ID: {}", id);
            return;
        }

        logger.debug("Удаляем задачу с ID: {}", id);
        taskRepository.deleteById(id);
        logger.info("Задача с ID {} успешно удалена", id);
    }

    public List<Task> getAllTasks() {
        logger.info("Вызван метод получения всех задач");
        logger.debug("Загружаем список всех задач из БД");

        List<Task> tasks = taskRepository.findAll();
        logger.info("Загружено задач: {}", tasks.size());
        logger.debug("Список задач: {}", tasks);

        return tasks;
    }

    public List<Task> findByCategoryId(Long categoryId) {
        logger.info("Вызван метод поиска задач по категории: {}", categoryId);
        logger.debug("Ищем задачи с category_id = {}", categoryId);

        List<Task> tasks = taskRepository.findByCategoryId(categoryId);
        logger.info("Найдено задач с категорией {}: {}", categoryId, tasks.size());
        logger.debug("Список задач для категории {}: {}", categoryId, tasks);

        return tasks;
    }
}
