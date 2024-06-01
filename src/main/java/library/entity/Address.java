package library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	private String streetAddress;
	private String streetAddress2;
	private String city;
	private String state;
	private int zip;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "borrower_id")
	private Borrower borrower;

}
