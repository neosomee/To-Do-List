package com.example.to_do_list.repository;

import com.example.to_do_list.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
