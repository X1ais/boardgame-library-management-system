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
import library.controller.model.ItemRecordDTO;
import library.controller.model.BibliographicRecordDTO.ReviewDTO;
import library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bib")
@Slf4j
public class BibliographyController {
	
	@Autowired
	private LibraryService libraryService;
	
	/*
	 * HTTP requests for bib records
	 */
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public BibliographicRecordDTO createBibRecord(@RequestBody BibliographicRecordDTO bibRecordDTO) {
		
		log.info("Creating a bibliographic record.");
		
		return libraryService.saveBibRecord(bibRecordDTO);
		
	}
	
	@GetMapping()
	public List<BibliographicRecordDTO> getAllBibRecords() {
		
		log.info("Fetching all bibliographic records.");
		
		return libraryService.retrieveAllBibRecords();
		
	}
	
	@GetMapping("/{bibId}")
	public BibliographicRecordDTO getBibRecordById(@PathVariable Long bibId) {
		
		log.info("Fetching bibliographic record with ID={}.", bibId);
		
		return libraryService.retrieveBibRecordById(bibId);
		
	}
	
	@PutMapping("/{bibId}")
	public BibliographicRecordDTO updateBibRecord(@PathVariable Long bibId, @RequestBody BibliographicRecordDTO bibRecordDTO) {
		
		log.info("Updating bibliographic record with ID={}.", bibId);
		
		bibRecordDTO.setBibId(bibId);
		
		return libraryService.updateBibRecord(bibRecordDTO);
		
	}
	
	@DeleteMapping("/{bibId}")
	public String deleteBibRecord(@PathVariable Long bibId) {
		
		log.info("Deleting bibliographic record with ID={}.", bibId);
		
		libraryService.deleteBibRecordById(bibId);
		
		String message = "Successfully deleted bib record with ID=" + bibId + ".";
		return message;
		
	}
	
	/*
	 * HTTP requests for item records
	 */
	
	@GetMapping("/{bibId}/items")
	public List<ItemRecordDTO> getAllItemsByBibId(@PathVariable Long bibId) {
		
		log.info("Fetching all items records attached to bib record with ID={}", bibId);
		
		return libraryService.retrieveAllItemsByBibId(bibId);
	}
	
	/*
	 * HTTP requests for Reviews
	 */
	
	@PostMapping("/{bibId}/review")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO, @PathVariable Long bibId, @RequestParam Long borrowerId) {
		
		log.info("Creating a review.");
		
		return libraryService.saveReview(reviewDTO, bibId, borrowerId);
	}
	
	@GetMapping("/{bibId}/reviews")
	public List<ReviewDTO> getReviewsByBibId(@PathVariable Long bibId) {
	
		log.info("Fetching reviews attached to Bib Record with ID={}", bibId);
		
		return libraryService.retrieveAllReviewsByBibId(bibId);
	}	
	
}
