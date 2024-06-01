package library.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Artist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long artistId;
	
	private String firstName;
	private String lastName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "artists")
	private Set<BibliographicRecord> bibRecords = new HashSet<>();

}
