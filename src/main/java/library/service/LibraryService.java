package library.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.controller.model.BibliographicRecordDTO;
import library.controller.model.BibliographicRecordDTO.ArtistDTO;
import library.controller.model.BibliographicRecordDTO.CategoryDTO;
import library.controller.model.BibliographicRecordDTO.DesignerDTO;
import library.controller.model.BibliographicRecordDTO.PublisherDTO;
import library.controller.model.BorrowerDTO;
import library.controller.model.ItemRecordDTO;
import library.controller.model.LoanDTO;
import library.dao.ArtistDao;
import library.dao.BibliographicRecordDao;
import library.dao.BorrowerDao;
import library.dao.CategoryDao;
import library.dao.DesignerDao;
import library.dao.ItemRecordDao;
import library.dao.LoanDao;
import library.dao.PublisherDao;
import library.entity.Artist;
import library.entity.BibliographicRecord;
import library.entity.Borrower;
import library.entity.Category;
import library.entity.Designer;
import library.entity.ItemRecord;
import library.entity.Loan;
import library.entity.Publisher;

@Service
public class LibraryService {

	@Autowired
	private BibliographicRecordDao bibRecordDao;

	@Autowired
	private PublisherDao publisherDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DesignerDao designerDao;

	@Autowired
	private ArtistDao artistDao;

	@Autowired
	private ItemRecordDao itemRecordDao;

	@Autowired
	private BorrowerDao borrowerDao;

	@Autowired
	private LoanDao loanDao;

	/*
	 * Start of the transactional service methods for the bib records.
	 */
	@Transactional(readOnly = false)
	public BibliographicRecordDTO saveBibRecord(BibliographicRecordDTO bibRecordDTO) {

		BibliographicRecord bibRecord = bibRecordDTO.toBibRecord();
		copyEntityFields(bibRecord, bibRecordDTO);

		return new BibliographicRecordDTO(bibRecordDao.save(bibRecord));
	}

	public Long findBibRecordId(Long bibId) {

		BibliographicRecord bibRecord = findBibRecordById(bibId);

		return bibRecord.getBibId();
	}

	@Transactional(readOnly = true)
	public BibliographicRecordDTO retrieveBibRecordById(Long bibId) {

		return new BibliographicRecordDTO(findBibRecordById(bibId));
	}

	@Transactional(readOnly = true)
	public List<BibliographicRecordDTO> retrieveAllBibRecords() {

		return bibRecordDao.findAll().stream().map(BibliographicRecordDTO::new).toList();
	}

	@Transactional(readOnly = false)
	public void deleteBibRecordById(Long bibId) {

		bibRecordDao.delete(findBibRecordById(bibId));
	}

	/*
	 * Start of the transactional service methods for the item records.
	 */

	@Transactional(readOnly = false)
	public ItemRecordDTO saveItemRecord(Long bibId, ItemRecordDTO itemData) {

		BibliographicRecord bibRecord = findBibRecordById(bibId);
		ItemRecord itemRecord = itemData.toItemRecord();

		bibRecord.getItems().add(itemRecord);
		itemRecord.setBibRecord(bibRecord);

		return new ItemRecordDTO(itemRecordDao.save(itemRecord));
	}

	@Transactional(readOnly = false)
	public ItemRecordDTO saveItemRecord(ItemRecordDTO itemData) {

		BibliographicRecord bibRecord = findItemRecordById(itemData.getItemId()).getBibRecord();
		ItemRecord itemRecord = itemData.toItemRecord();

		itemRecord.setBibRecord(bibRecord);

		return new ItemRecordDTO(itemRecordDao.save(itemRecord));
	}

	@Transactional(readOnly = true)
	public List<ItemRecordDTO> retrieveAllItemsByBibId(Long bibId) {

		BibliographicRecord bibRecord = findBibRecordById(bibId);

		return bibRecord.getItems().stream().map(ItemRecordDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public ItemRecordDTO retrieveItemRecordById(Long itemId) {

		return new ItemRecordDTO(findItemRecordById(itemId));
	}

	@Transactional(readOnly = true)
	public List<ItemRecordDTO> retrieveAllItemRecords() {

		return itemRecordDao.findAll().stream().map(ItemRecordDTO::new).toList();
	}

	@Transactional(readOnly = false)
	public void deleteItemRecord(Long itemId) {
		
		ItemRecord item = findItemRecordById(itemId);
		BibliographicRecord bibRecord = item.getBibRecord();
		
		bibRecord.getItems().remove(item);

		itemRecordDao.delete(findItemRecordById(itemId));
	}

	/*
	 * Start of the transactional service methods for the borrower records.
	 */

	@Transactional(readOnly = false)
	public BorrowerDTO saveBorrower(BorrowerDTO borrowerDTO) {

		Borrower borrower = borrowerDTO.toBorrower();

		return new BorrowerDTO(borrowerDao.save(borrower));
	}

	@Transactional(readOnly = true)
	public List<BorrowerDTO> retrieveAllBorrowers() {

		return borrowerDao.findAll().stream().map(BorrowerDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public BorrowerDTO retrieveBorrowerById(Long borrowerId) {

		return new BorrowerDTO(findBorrowerById(borrowerId));
	}

	@Transactional(readOnly = false)
	public void deleteBorrower(Long borrowerId) {

		borrowerDao.delete(findBorrowerById(borrowerId));
	}

	/*
	 * Start of the transactional service methods for the borrower records.
	 */

	@Transactional(readOnly = false)
	public LoanDTO saveLoanRecord(LoanDTO loanDTO) throws RuntimeException{

		ItemRecord item = findItemRecordById(loanDTO.getItem().getItemId());
		Borrower borrower = findBorrowerById(loanDTO.getBorrower().getBorrowerId());

		if (item.isAvailable() && borrower.getLoans().size() < borrower.getItemLimit()) {
			
			Loan loan = new Loan();
			loan.setBorrower(borrower);
			loan.setItem(item);
			loan.setReturnDate(loan.getCheckoutDate().plusDays(item.getCheckoutPeriod()));

			item.setLoanRecord(loan);
			item.setAvailable(false);
			item.incrementCheckouts();

			borrower.getLoans().add(loan);

			return new LoanDTO(loanDao.save(loan));
			
		} else if(!item.isAvailable()){
			throw new RuntimeException("Item is unavailable.");
		} else if(borrower.getLoans().size() >= borrower.getItemLimit()) {
			throw new RuntimeException("Borrower has reached the borrowing limit.");
		} else {
			throw new RuntimeException("Something unexpected occured.");
		}
	}

	@Transactional(readOnly = false)
	public void deleteLoan(Long loanId) {
		
		Loan loan = findLoanById(loanId);
		ItemRecord item = findItemRecordById(loan.getItem().getItemId());
		Borrower borrower = findBorrowerById(loan.getBorrower().getBorrowerId());
		
		item.setLoanRecord(null);
		item.setAvailable(true);
		
		borrower.getLoans().remove(loan);
		
		loanDao.delete(loan);
	}
	
	
	

	private void copyEntityFields(BibliographicRecord bibRecord, BibliographicRecordDTO bibRecordDTO) {

		for (PublisherDTO publisherData : bibRecordDTO.getPublishers()) {

			bibRecord.getPublishers().add(findOrCreatePublisher(publisherData));
		}

		for (CategoryDTO categoryData : bibRecordDTO.getCategories()) {

			bibRecord.getCategories().add(findOrCreateCategory(categoryData));
		}

		for (DesignerDTO designerData : bibRecordDTO.getDesigners()) {

			bibRecord.getDesigners().add(findOrCreateDesigner(designerData));
		}

		for (ArtistDTO artistData : bibRecordDTO.getArtists()) {

			bibRecord.getArtists().add(findOrCreateArtist(artistData));
		}
	}

//	private BibliographicRecord findOrCreateBibRecord(BibliographicRecordDTO bibRecordData) {
//		
//		Long bibId = bibRecordData.getBibId();
//		
//		return Objects.isNull(bibId) ? bibRecordData.toBibRecord() : findBibRecordById(bibId);
//	}

	private Publisher findOrCreatePublisher(PublisherDTO publisherData) {

		Long publisherId = publisherData.getPublisherId();

		return Objects.isNull(publisherId) ? publisherData.toPublisher() : findPublisherById(publisherId);
	}

	private Category findOrCreateCategory(CategoryDTO categoryData) {

		Long categoryId = categoryData.getCategoryId();

		return Objects.isNull(categoryId) ? categoryData.toCategory() : findCategoryById(categoryId);
	}

	private Designer findOrCreateDesigner(DesignerDTO designerData) {

		Long designerId = designerData.getDesignerId();

		return Objects.isNull(designerId) ? designerData.toDesigner() : findDesignerById(designerId);
	}

	private Artist findOrCreateArtist(ArtistDTO artistData) {

		Long artistId = artistData.getArtistId();

		return Objects.isNull(artistId) ? artistData.toArtist() : findArtistById(artistId);
	}

//	private ItemRecord findOrCreateItem(ItemRecordDTO itemData) {
//		
//		Long itemId = itemData.getItemId();
//		
//		return Objects.isNull(itemId) ? itemData.toItemRecord() : findItemRecordById(itemId);
//	}

	private BibliographicRecord findBibRecordById(Long bibId) {

		return bibRecordDao.findById(bibId).orElseThrow(
				() -> new NoSuchElementException("No such bibliographic record with ID=" + bibId + " was found."));
	}

	private Publisher findPublisherById(Long publisherId) {

		return publisherDao.findById(publisherId).orElseThrow(
				() -> new NoSuchElementException("No such publisher with ID=" + publisherId + " was found."));
	}

	private Category findCategoryById(Long categoryId) {

		return categoryDao.findById(categoryId).orElseThrow(
				() -> new NoSuchElementException("No such category with ID=" + categoryId + " was found."));
	}

	private Designer findDesignerById(Long designerId) {

		return designerDao.findById(designerId).orElseThrow(
				() -> new NoSuchElementException("No such designer with ID=" + designerId + " was found."));
	}

	private Artist findArtistById(Long artistId) {

		return artistDao.findById(artistId)
				.orElseThrow(() -> new NoSuchElementException("No such designer with ID=" + artistId + " was found."));
	}

	private ItemRecord findItemRecordById(Long itemId) {

		return itemRecordDao.findById(itemId)
				.orElseThrow(() -> new NoSuchElementException("No such item record with ID=" + itemId + " was found."));
	}

	private Borrower findBorrowerById(Long borrowerId) {

		return borrowerDao.findById(borrowerId).orElseThrow(
				() -> new NoSuchElementException("No such borrower record with ID=" + borrowerId + " was found."));
	}
	
	private Loan findLoanById(Long loanId) {
		
		return loanDao.findById(loanId)
				.orElseThrow(() -> new NoSuchElementException("No such loan record with ID=" + loanId + " was found."));
	}

}
