package library.controller.model;

import java.time.LocalDate;

import library.entity.Loan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDTO {

	private Long loanId;
	private LocalDate checkoutDate;
	private LocalDate returnDate;
	private ItemRecordDTO item;
	private BorrowerDTO borrower;
	
	public LoanDTO(Loan loan) {
		
		this.loanId = loan.getLoanId();
		this.checkoutDate = loan.getCheckoutDate();
		this.returnDate = loan.getReturnDate();
		this.item = new ItemRecordDTO(loan.getItem());
		this.borrower = new BorrowerDTO(loan.getBorrower());
	}

	public Loan toLoan() {
		
		Loan loan = new Loan();
		
		loan.setLoanId(loanId);
		loan.setCheckoutDate(checkoutDate);
		loan.setReturnDate(returnDate);
		loan.setItem(item.toItemRecord());
		loan.setBorrower(borrower.toBorrower());
		
		return loan;
	}
}
