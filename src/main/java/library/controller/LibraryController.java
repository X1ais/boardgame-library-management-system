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

import library.controller.model.BibliographicRecordDTO;
import library.controller.model.BorrowerDTO;
import library.controller.model.ItemRecordDTO;
import library.controller.model.LoanDTO;
import library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/library")
@Slf4j
public class LibraryController {
	
	@Autowired
	private LibraryService libraryService;
	
	/*
	 * HTTP requests for bib records
	 */
	
	@PostMapping("/bib")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BibliographicRecordDTO createBibRecord(@RequestBody BibliographicRecordDTO bibRecordDTO) {
		
		log.info("Creating a bibliographic record.");
		
		return libraryService.saveBibRecord(bibRecordDTO);
		
	}
	
	@GetMapping("/bib")
	public List<BibliographicRecordDTO> getAllBibRecords() {
		
		log.info("Fetching all bibliographic records.");
		
		return libraryService.retrieveAllBibRecords();
		
	}
	
	@GetMapping("/bib/{bibId}")
	public BibliographicRecordDTO getBibRecordById(@PathVariable Long bibId) {
		
		log.info("Fetching bibliographic record with ID={}.", bibId);
		
		return libraryService.retrieveBibRecordById(bibId);
		
	}
	
	@PutMapping("/bib/{bibId}")
	public BibliographicRecordDTO updateBibRecord(@PathVariable Long bibId, @RequestBody BibliographicRecordDTO bibRecordDTO) {
		
		log.info("Updating bibliographic record with ID={}.", bibId);
		
		bibRecordDTO.setBibId(
				libraryService.findBibRecordId(bibId));
		
		return libraryService.saveBibRecord(bibRecordDTO);
		
	}
	
	@DeleteMapping("/bib/{bibId}")
	public String deleteBibRecord(@PathVariable Long bibId) {
		
		log.info("Deleting bibliographic record with ID={}.", bibId);
		
		libraryService.deleteBibRecordById(bibId);
		
		String message = "Successfully deleted bib record with ID=" + bibId + ".";
		return message;
		
	}
	
	/*
	 * HTTP requests for item records
	 */
	
	@PostMapping("/item")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemRecordDTO createItemRecord(@RequestParam Long bibId, @RequestBody ItemRecordDTO itemRecordDTO) {
		
		log.info("Creating an item record for bib record with ID={}.", bibId);
		
		return libraryService.saveItemRecord(bibId, itemRecordDTO);
		
	}
	
	@GetMapping("/item")
	public List<ItemRecordDTO> getAllItemRecords() {
		
		log.info("Fetching all item records.");
		
		return libraryService.retrieveAllItemRecords();
	}
	
	@GetMapping("/item/{itemId}")
	public ItemRecordDTO getItemRecordById(@PathVariable Long itemId) {
		
		log.info("Fetching item record with ID={}.", itemId);
		
		return libraryService.retrieveItemRecordById(itemId);
	}
	
	@GetMapping("/bib/items")
	public List<ItemRecordDTO> getAllItemsByBibId(@RequestParam Long bibId) {
		
		log.info("Fetching all items records attached to bib record with ID={}", bibId);
		
		return libraryService.retrieveAllItemsByBibId(bibId);
	}
	
	@PutMapping("/item/{itemId}")
	public ItemRecordDTO updateItemRecord(@PathVariable Long itemId, @RequestBody ItemRecordDTO itemRecordDTO) {
		
		log.info("Updating item record with ID={}.", itemId);
		
		itemRecordDTO.setItemId(itemId);
		return libraryService.saveItemRecord(itemRecordDTO);
	}
	
	@DeleteMapping("/item/{itemId}")
	public String deleteItemRecord(@PathVariable Long itemId) {
		
		log.info("Deleting item record with ID={}.", itemId);
		
		libraryService.deleteItemRecord(itemId);
		
		String message = "Successfully deleted item record with ID=" + itemId + ".";
		return message;
	}
	
	/*
	 * HTTP requests for Borrower records
	 */
	
	@PostMapping("/borrower")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BorrowerDTO createBorrower(@RequestBody BorrowerDTO borrowerDTO) {
		
		log.info("Creating new borrower record.");
		
		return libraryService.saveBorrower(borrowerDTO);
	}
	
	@GetMapping("/borrower")
	public List<BorrowerDTO> getAllBorrowers() {
		
		log.info("Fetching all borrowers.");
		
		return libraryService.retrieveAllBorrowers();
	}
	
	@GetMapping("/borrower/{borrowerId}")
	public BorrowerDTO getBorrowerById(@PathVariable Long borrowerId) {
		
		log.info("Fetching borrower with ID={}", borrowerId);
		
		return libraryService.retrieveBorrowerById(borrowerId);
	}	
	
	@PutMapping("/borrower/{borrowerId}")
	public BorrowerDTO updateItemRecord(@PathVariable Long borrowerId, @RequestBody BorrowerDTO borrowerDTO) {
		
		log.info("Updating borrower record with ID={}.", borrowerId);
		
		borrowerDTO.setBorrowerId(borrowerId);
		return libraryService.saveBorrower(borrowerDTO);
	}
	
	@DeleteMapping("/borrower/{borrowerId}")
	public String deleteBorrowerById(@PathVariable Long borrowerId) {
		
		log.info("Deleting borrower with ID={}", borrowerId);
		
		libraryService.deleteBorrower(borrowerId);
		
		String message = "Successfully deleted borrower record with ID=" + borrowerId + ".";
		return message;
	}
	
	/*
	 * HTTP requests for Loan records
	 */
	
	@PostMapping("/loan")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LoanDTO createLoanRecord(@RequestBody LoanDTO loanDTO) {
		
		log.info("Creating loan record.");
		
		return libraryService.saveLoanRecord(loanDTO);
	}
	
	@DeleteMapping("/loan/{loanId}")
	public String deleteLoanRecord(@PathVariable Long loanId) {
		
		log.info("Deleting loan record with ID={}", loanId);
		
		libraryService.deleteLoan(loanId);
		
		String message = "Successfully deleted loan record with ID=" + loanId + ".";
		return message;
	}
	
}
