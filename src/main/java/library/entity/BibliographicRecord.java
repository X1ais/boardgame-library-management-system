package library.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class BibliographicRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bib_id")
	private Long bibId;
	
	private String bibName;
	private Integer minNumOfPlayers;
	private Integer maxNumOfPlayers;
	private Integer minAge;
	private Integer playtime;
	private BigDecimal complexity;
	private String edition;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(name = "bibliographic_record_publisher",
	joinColumns = @JoinColumn(name = "bib_id"),
	inverseJoinColumns = @JoinColumn(name = "publisher_id"))
	private Set<Publisher> publishers = new HashSet<>();	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(name = "bibliographic_record_category",
		joinColumns = @JoinColumn(name = "bib_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(name = "bibliographic_record_designer",
		joinColumns = @JoinColumn(name = "bib_id"),
		inverseJoinColumns = @JoinColumn(name = "designer_id"))
	private Set<Designer> designers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(name = "bibliographic_record_artist",
		joinColumns = @JoinColumn(name = "bib_id"),
		inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private Set<Artist> artists = new HashSet<>();
	
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "bibRecord", cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "bibRecord")
	private Set<ItemRecord> items = new HashSet<>();

}
