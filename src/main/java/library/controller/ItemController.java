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

import library.controller.model.ItemRecordDTO;
import library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
@Slf4j
public class ItemController {
	
	@Autowired
	private LibraryService libraryService;
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemRecordDTO createItemRecord(@RequestParam Long bibId, @RequestBody ItemRecordDTO itemRecordDTO) {
		
		log.info("Creating an item record for bib record with ID={}.", bibId);
		
		return libraryService.saveItemRecord(bibId, itemRecordDTO);
		
	}
	
	@GetMapping()
	public List<ItemRecordDTO> getAllItemRecords() {
		
		log.info("Fetching all item records.");

		return libraryService.retrieveAllItemRecords();
	}
	
	@GetMapping("/{itemId}")
	public ItemRecordDTO getItemRecordById(@PathVariable Long itemId) {
		
		log.info("Fetching item record with ID={}.", itemId);
		
		return libraryService.retrieveItemRecordById(itemId);
	}
	
	@PutMapping("/{itemId}")
	public ItemRecordDTO updateItemRecord(@PathVariable Long itemId, @RequestBody ItemRecordDTO itemRecordDTO) {
		
		log.info("Updating item record with ID={}.", itemId);
		
		itemRecordDTO.setItemId(itemId);
		return libraryService.updateItemRecord(itemRecordDTO);
	}
	
	@DeleteMapping("/{itemId}")
	public String deleteItemRecord(@PathVariable Long itemId) {
		
		log.info("Deleting item record with ID={}.", itemId);
		
		libraryService.deleteItemRecord(itemId);
		
		String message = "Successfully deleted item record with ID=" + itemId + ".";
		return message;
	}

}
