package library.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Borrower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long borrowerId;
	
	private String firstName;
	private String lastName;
	private String phone;
	private int itemLimit;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
	private Set<Loan> loans = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "borrower", cascade = CascadeType.PERSIST)
	private Set<Review> reviews = new HashSet<>();

}
