package library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import library.controller.model.BorrowerDTO;
import library.controller.model.BibliographicRecordDTO.ReviewDTO;
import library.controller.model.ItemRecordDTO.LoanDTO;
import library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrower")
@Slf4j
public class BorrowerController {
	
	@Autowired
	private LibraryService libraryService;
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public BorrowerDTO createBorrower(@RequestBody BorrowerDTO borrowerDTO) {
		
		log.info("Creating new borrower record.");
		
		return libraryService.saveBorrower(borrowerDTO);
	}
	
	@GetMapping()
	public List<BorrowerDTO> getAllBorrowers() {
		
		log.info("Fetching all borrowers.");
		
		return libraryService.retrieveAllBorrowers();
	}
	
	@GetMapping("/{borrowerId}")
	public BorrowerDTO getBorrowerById(@PathVariable Long borrowerId) {
		
		log.info("Fetching borrower with ID={}", borrowerId);
		
		return libraryService.retrieveBorrowerById(borrowerId);
	}	
	
	@PutMapping("/{borrowerId}")
	public BorrowerDTO updateItemRecord(@PathVariable Long borrowerId, @RequestBody BorrowerDTO borrowerDTO) {
		
		log.info("Updating borrower record with ID={}.", borrowerId);
		
		borrowerDTO.setBorrowerId(borrowerId);
		return libraryService.saveBorrower(borrowerDTO);
	}
	
	@DeleteMapping("/{borrowerId}")
	public String deleteBorrowerById(@PathVariable Long borrowerId) {
		
		log.info("Deleting borrower with ID={}", borrowerId);
		
		libraryService.deleteBorrower(borrowerId);
		
		String message = "Successfully deleted borrower record with ID=" + borrowerId + ".";
		return message;
	}
	
	/*
	 * HTTP requests for Loan records
	 */
	
	@PostMapping("/{borrowerId}/loan")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LoanDTO createLoanRecord(@PathVariable Long borrowerId, @RequestParam Long itemId) {
		
		log.info("Creating loan record.");
		
		return libraryService.saveLoanRecord(itemId, borrowerId);
	}
	
	@GetMapping("/{borrowerId}/loans")
	public List<LoanDTO> getAllLoansByBorrowerId(@PathVariable Long borrowerId) {
		
		log.info("Fetching all loans attached to borrower with ID={}", borrowerId);
		
		return libraryService.retrieveAllLoansByBorrowerId(borrowerId);
	}
	
	@GetMapping("/loan/{loanId}")
	public LoanDTO getLoanRecord(@PathVariable Long loanId) {
		
		log.info("Fetching loan record with ID={}", loanId);
		
		return libraryService.retrieveLoanById(loanId);
	}
	
	@DeleteMapping("/loan/{loanId}")
	public String deleteLoanRecord(@PathVariable Long loanId) {
		
		log.info("Deleting loan record with ID={}", loanId);
		
		libraryService.deleteLoan(loanId);
		
		String message = "Successfully deleted loan record with ID=" + loanId + ".";
		return message;
	}
	
	/*
	 * HTTP requests for Review
	 */
	
	@GetMapping("/{borrowerId}/reviews")
	public List<ReviewDTO> getReviewsByBorrowerId(@PathVariable Long borrowerId) {
	
		log.info("Fetching reviews attached to Borrower with ID={}", borrowerId);
		
		return libraryService.retrieveAllReviewsByBorrowerId(borrowerId);
	}

}
