package library.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	private LocalDate checkoutDate;
	private LocalDate returnDate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "item_id")
	private ItemRecord item;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "borrower_id")
	private Borrower borrower;

}
