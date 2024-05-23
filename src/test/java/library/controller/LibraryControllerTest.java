package library.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;

import library.LibraryApplication;
import library.controller.model.BibliographicRecordDTO;
import library.controller.model.ItemRecordDTO;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = LibraryApplication.class)
@ActiveProfiles("test")
@SqlConfig(encoding = "utf-8")
class LibraryControllerTest extends LibraryServiceTestSupport {

	@Test
	void testCreateBibRecord() {
		
		// Given: a bib record creation request
		BibliographicRecordDTO request = buildInsertBibRecord(1);
		BibliographicRecordDTO expected = buildInsertBibRecord(1);		
		
		// When: the bib record is added to the bib record table
		BibliographicRecordDTO actual = insertBibRecord(request);	
		
		// Then: the bib record returned what is expected
		assertThat(actual).isEqualTo(expected);
		
		// And: only one row is in the table.
		assertThat(rowsInBibTable()).isOne();
		
	}

	@Test
	void testUpdateBibRecord() {
		
		// Given: a bib record and an update request
		insertBibRecord(buildInsertBibRecord(1));
		BibliographicRecordDTO expected = buildUpdateBibRecord();
		
		// When: the bib record is updated
		BibliographicRecordDTO actual = updateBibRecord(expected);
		
		// Then: the bib record is as expected
		assertThat(actual).isEqualTo(expected);
		
		// And: there is still only one row in the bib record table.
		assertThat(rowsInBibTable()).isOne();
		
	}

	@Test
	void testGetBibRecord() {
		
		// Given: a bib record ID
		BibliographicRecordDTO bib = insertBibRecord(buildInsertBibRecord(1));
		BibliographicRecordDTO expected = buildInsertBibRecord(1);
		
		// When: a bib record is retrieved by bibId
		BibliographicRecordDTO actual = getBidRecord(bib.getBibId());
		
		// Then: the retrieved record matches the expected record.
		assertThat(actual).isEqualTo(expected);
		
	}

	@Test
	void testGetAllBibRecords() {
		
		// Given: two bib records
		List<BibliographicRecordDTO> expected = insertTwoBibRecords();
		
		// When: all bib records are retrieved
		List<BibliographicRecordDTO> actual = getAllBibRecords();
		
		// Then: the retrieved records will match the expected records.
		assertThat(actual).isEqualTo(expected);
		
	}

	@Test
	void testDeleteBibRecord() {
		
		// Given: a bib record with a bib record Id
		BibliographicRecordDTO bibRecord = insertBibRecord(buildInsertBibRecord(1));
		Long bibId = bibRecord.getBibId();
		
		assertThat(rowsInBibTable()).isOne();
		
		// When: the record is deleted
		deleteBibRecord(bibId);
		
		// Then: no records exist.
		assertThat(rowsInBibTable()).isZero();
		
	}

	@Test
	void testCreateItemRecord() {
		
		// Given: an item record creation request and a bib id
		BibliographicRecordDTO requestBib = insertBibRecord(buildInsertBibRecord(1));
		ItemRecordDTO request = buildInsertItemRecord(1);
		ItemRecordDTO expected = buildInsertItemRecord(1);
		
		// When: the item record is created
		ItemRecordDTO actual = insertItemRecord(requestBib.getBibId(), request);
		
		// Then: the expected record is stored in the item table
		assertThat(actual).isEqualTo(expected);
				
	}

//	@Test
//	void test() {
//		
//		// Given: 
//		
//		// When: 
//		
//		// Then: 
//		
//	}

}
