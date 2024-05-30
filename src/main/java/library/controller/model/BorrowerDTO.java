package library.controller.model;

import java.util.HashSet;
import java.util.Set;

import library.entity.Borrower;
import library.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BorrowerDTO {
	
private Long borrowerId;
	
	private String firstName;
	private String lastName;
	private String phone;
	private int itemLimit;
	private AddressDTO address;
	private Set<ReviewDTO> reviews = new HashSet<>();
	
	public BorrowerDTO(Borrower borrower) {
		
		this.borrowerId = borrower.getBorrowerId();
		this.firstName = borrower.getFirstName();
		this.lastName = borrower.getLastName();
		this.phone = borrower.getPhone();
		this.itemLimit = borrower.getItemLimit();
		this.address = new AddressDTO(borrower.getAddress());
		
		for(Review review : borrower.getReviews()) {
			this.reviews.add(new ReviewDTO(review));
		}
		
	}
	
	public Borrower toBorrower() {
		
		Borrower borrower = new Borrower();
		
		borrower.setBorrowerId(borrowerId);
		borrower.setFirstName(firstName);
		borrower.setLastName(lastName);
		borrower.setPhone(phone);
		borrower.setItemLimit(itemLimit);
		borrower.setAddress(address.toAddress());
		
		return borrower;
	}

}
