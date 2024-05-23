package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Designer;

public interface DesignerDao extends JpaRepository<Designer, Long> {

}
