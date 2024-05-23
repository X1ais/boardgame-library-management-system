package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Publisher;

public interface PublisherDao extends JpaRepository<Publisher, Long> {

}
