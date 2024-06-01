package library.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import library.controller.model.BibliographicRecordDTO;
import library.controller.model.ItemRecordDTO;
import library.entity.BibliographicRecord;

public class LibraryServiceTestSupport {
	
	private static final String BIBLIOGRAPHIC_RECORD_TABLE = "bibliographic_record";

	private BibliographicRecordDTO insertBibRecord1 = new BibliographicRecordDTO(
			15L,
			"Monopoly",
			2,
			4,
			10,
			90,
			BigDecimal.valueOf(2.51),
			"First"
			);
	
	private BibliographicRecordDTO insertBibRecord2 = new BibliographicRecordDTO(
			16L,
			"Chess",
			2,
			2,
			10,
			90,
			BigDecimal.valueOf(4.57),
			"N/A"
			);
	
	private BibliographicRecordDTO updateBibRecord1 = new BibliographicRecordDTO(
			15L,
			"Simpson's Monopoly",
			2,
			4,
			12,
			120,
			BigDecimal.valueOf(2.74),
			"N/A"
			);
	
	private ItemRecordDTO insertItemRecord1 = new ItemRecordDTO(
			1L,
			"Monopoly",
			"Board game room",
			true,
			0,
			14
			);
	
	private ItemRecordDTO insertItemRecord2 = new ItemRecordDTO(
			2L,
			"Chess",
			"Front Desk",
			true,
			0,
			28
			);
	
	@Autowired
	private BibliographyController bibController;
	
	@Autowired
	private ItemController itemController;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	protected BibliographicRecordDTO buildInsertBibRecord(int i) {
		
		return i == 1 ? insertBibRecord1 : insertBibRecord2;
		
	}
	
	protected ItemRecordDTO buildInsertItemRecord(int i) {
		return i == 1 ? insertItemRecord1 : insertItemRecord2;
	}

	protected BibliographicRecordDTO insertBibRecord(BibliographicRecordDTO bibRecordDTO) {
		
		BibliographicRecord bibRecord = bibRecordDTO.toBibRecord();
		BibliographicRecordDTO clone = new BibliographicRecordDTO(bibRecord);
		
		clone.setBibId(null);
		return bibController.createBibRecord(clone);
		
	}
	
	protected BibliographicRecordDTO buildUpdateBibRecord() {
		
		return updateBibRecord1;
	}	

	protected int rowsInBibTable() {
		
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, BIBLIOGRAPHIC_RECORD_TABLE);
		
	}
	
	protected BibliographicRecordDTO getBidRecord(Long bibId) {
		
		return bibController.getBibRecordById(bibId);
		
	}

	protected BibliographicRecordDTO updateBibRecord(BibliographicRecordDTO bibData) {
		
		return bibController.updateBibRecord(bibData.getBibId(), bibData);
	
	}

	protected List<BibliographicRecordDTO> insertTwoBibRecords() {

		BibliographicRecordDTO bib1 = insertBibRecord(buildInsertBibRecord(1));
		BibliographicRecordDTO bib2 = insertBibRecord(buildInsertBibRecord(2));
		
		return List.of(bib1, bib2);
		
	}

	protected List<BibliographicRecordDTO> getAllBibRecords() {
		
		return bibController.getAllBibRecords();
		
	}

	protected void deleteBibRecord(Long bibId) {

		bibController.deleteBibRecord(bibId);
		
	}

	protected ItemRecordDTO insertItemRecord(Long bibId, ItemRecordDTO request) {
		
		return itemController.createItemRecord(bibId, request);
		
	}
	
}
