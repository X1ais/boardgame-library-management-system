package library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {

}
