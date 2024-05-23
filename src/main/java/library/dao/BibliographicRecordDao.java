package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.BibliographicRecord;

public interface BibliographicRecordDao extends JpaRepository<BibliographicRecord, Long> {

}
