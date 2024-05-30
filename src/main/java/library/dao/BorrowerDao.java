package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Borrower;

public interface BorrowerDao extends JpaRepository<Borrower, Long> {

}
