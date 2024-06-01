package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {

}
