package com.example.to_do_list.service;

import com.example.to_do_list.model.Category;
import com.example.to_do_list.model.Task;
import com.example.to_do_list.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        logger.info("Вызван конструктор CategoryService");
    }

    public Category createCategory(Category category) {
        logger.info("Вызван метод создания категории: {}", category.getName());
        logger.debug("Сохраняем новую категорию в БД: {}", category);

        Category savedCategory = this.categoryRepository.save(category);
        logger.info("Категория успешна создана с ID: {}", savedCategory.getId());
        return savedCategory;
    }

    public Category findCategory(long id) {
        logger.info("Вызван метод поиска категории по ID: {}", id);
        logger.debug("Сохраняем новую категорию в БД: {}", id);

        Category category = this.categoryRepository.findById(id).get();
        if (category == null) {
            logger.debug("Категория найдена: {}", category.getName());
        } else {
            logger.warn("Категория с ID {} не найдена", id);
        }
        return category;
    }

    public Category editCategory(Category category) {
        logger.info("Вызван метод редактирования категории ID: {}", category.getId());

        if (!categoryRepository.existsById(category.getId())) {
            logger.error("Категория с ID: {}", category.getId());
            return null;
        }

        logger.debug("Обновляем категорию: {}", category);
        Category editedCategory = this.categoryRepository.save(category);
        logger.info("Категория с ID {}", editedCategory.getId());
        return editedCategory;
    }

    public Category deleteCategory(long id) {
        logger.info("Вызван метод удаления категории ID: {}", id);

        Category category = this.categoryRepository.findById(id).get();
        if (category != null) {
            logger.debug("Удаляем категорию: {}", category.getName());
            categoryRepository.deleteById(id);
            logger.info("Категория ID {} успешно удалена", id);
        }  else {
            logger.warn("Попытка удалить несуществующую категорию");
        }
        return category;
    }

    public List<Category> getAllCategories() {
        logger.info("Вызван метод получения всех категорий");
        logger.debug("Загружаем список всех категорий из БД");

        List<Category> categories = categoryRepository.findAll();
        logger.info("Загружено категорий: {}", categories.size());
        logger.debug("Список категорий: {}", categories);

        return categories;
    }


}
