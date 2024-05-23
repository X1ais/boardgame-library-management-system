package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.ItemRecord;

public interface ItemRecordDao extends JpaRepository<ItemRecord, Long> {

}
