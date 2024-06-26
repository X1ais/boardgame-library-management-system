package library.controller.model;

import java.time.LocalDate;

import library.entity.ItemRecord;
import library.entity.Loan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRecordDTO {
	
	private Long itemId;
	private String itemName;
	private String location;
	private boolean available;
	private Integer checkouts;
	private Integer checkoutPeriod;
	
	public ItemRecordDTO(ItemRecord item) {
		this.itemId = item.getItemId();
		this.itemName = item.getItemName();
		this.location = item.getLocation();
		this.available = item.isAvailable();
		this.checkouts = item.getCheckouts();
		this.checkoutPeriod = item.getCheckoutPeriod();
	}
	
	public ItemRecordDTO(Long itemId, String itemName, String location, boolean available, int checkouts, int checkoutPeriod) {
		this.setItemId(itemId);
		this.setItemName(itemName);
		this.setLocation(location);
		this.setAvailable(available);
		this.setCheckouts(checkouts);
		this.setCheckoutPeriod(checkoutPeriod);
	}
	
	public ItemRecord toItemRecord() {
		
		ItemRecord item = new ItemRecord();
		
		item.setItemId(itemId);
		item.setItemName(itemName);
		item.setLocation(location);
		item.setAvailable(available);
		item.setCheckouts(checkouts);
		item.setCheckoutPeriod(checkoutPeriod);
		
		return item;
	}
	
	@Data
	@NoArgsConstructor
	public static class LoanDTO {

		private Long loanId;
		private LocalDate checkoutDate;
		private LocalDate returnDate;
		
		public LoanDTO(Loan loan) {
			
			this.loanId = loan.getLoanId();
			this.checkoutDate = loan.getCheckoutDate();
			this.returnDate = loan.getReturnDate();
		}

//		public Loan toLoan() {
//			
//			Loan loan = new Loan();
//			
//			loan.setLoanId(loanId);
//			loan.setCheckoutDate(checkoutDate);
//			loan.setReturnDate(returnDate);
//			
//			return loan;
//		}
	}

}
