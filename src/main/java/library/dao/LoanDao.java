package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Loan;

public interface LoanDao extends JpaRepository<Loan, Long> {

}
