package library.controller.model;

import java.util.HashSet;
import java.util.Set;

import library.controller.model.BibliographicRecordDTO.ReviewDTO;
import library.entity.Address;
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
		
		return borrower;
	}
	
	@Data
	@NoArgsConstructor
	public static class AddressDTO {
		
		private Long addressId;
		private String streetAddress;
		private String streetAddress2;
		private String city;
		private String state;
		private int zip;
		private BorrowerDTO borrower;
		
		public AddressDTO(Address address) {
			this.addressId = address.getAddressId();
			this.streetAddress = address.getStreetAddress();
			this.streetAddress2 = address.getStreetAddress2();
			this.city = address.getCity();
			this.state = address.getState();
			this.zip = address.getZip();
			this.borrower = new BorrowerDTO(address.getBorrower());
		}

		public Address toAddress() {
			Address address = new Address();
			
			address.setAddressId(addressId);
			address.setStreetAddress(streetAddress);
			address.setStreetAddress2(streetAddress2);
			address.setCity(city);
			address.setState(state);
			address.setZip(zip);
			address.setBorrower(borrower.toBorrower());
			
			return address;
		}

	}

}
