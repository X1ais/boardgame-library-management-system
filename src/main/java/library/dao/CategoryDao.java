package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
