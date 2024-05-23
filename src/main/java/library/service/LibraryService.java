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
import library.controller.model.ItemRecordDTO;
import library.dao.ArtistDao;
import library.dao.BibliographicRecordDao;
import library.dao.CategoryDao;
import library.dao.DesignerDao;
import library.dao.ItemRecordDao;
import library.dao.PublisherDao;
import library.entity.Artist;
import library.entity.BibliographicRecord;
import library.entity.Category;
import library.entity.Designer;
import library.entity.ItemRecord;
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

	
	
	
	/*
	 * Start of the transactional service methods for the bib records.
	 */
	@Transactional(readOnly = false)
	public BibliographicRecordDTO saveBibRecord(BibliographicRecordDTO bibRecordDTO) {
		
 		BibliographicRecord bibRecord = bibRecordDTO.toBibRecord();
 		copyEntityFields(bibRecord, bibRecordDTO);
		
		return new BibliographicRecordDTO(bibRecordDao.save(bibRecord));
	}

	@Transactional(readOnly = true)
	public BibliographicRecordDTO retrieveBibRecordById(Long bibId) {

		return new BibliographicRecordDTO(findBibRecordById(bibId));
	}

	@Transactional(readOnly = true)
	public List<BibliographicRecordDTO> retrieveAllBibRecords() {

		return bibRecordDao.findAll()
				.stream()
				.map(BibliographicRecordDTO::new)
				.toList();
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
	public ItemRecordDTO retrieveItemRecordById(Long itemId) {
		
		return new ItemRecordDTO(findItemRecordById(itemId));
	}

	@Transactional(readOnly = true)
	public List<ItemRecordDTO> retrieveAllItemRecords() {
		
		return itemRecordDao.findAll()
				.stream()
				.map(ItemRecordDTO::new)
				.toList();
	}

	@Transactional(readOnly = false)
	public void deleteItemRecord(Long itemId) {

		itemRecordDao.delete(findItemRecordById(itemId));
	}
	
	
	
	
	private void copyEntityFields(BibliographicRecord bibRecord, BibliographicRecordDTO bibRecordDTO) {

		for (PublisherDTO publisherData : bibRecordDTO.getPublishers()) {
			
			bibRecord.getPublishers()
				.add(findOrCreatePublisher(publisherData));	
		}
		
		for (CategoryDTO categoryData : bibRecordDTO.getCategories()) {
			
			bibRecord.getCategories()
				.add(findOrCreateCategory(categoryData));
		}
		
		for (DesignerDTO designerData : bibRecordDTO.getDesigners()) {
			
			bibRecord.getDesigners()
				.add(findOrCreateDesigner(designerData));
		}
		
		for (ArtistDTO artistData : bibRecordDTO.getArtists()) {
			
			bibRecord.getArtists()
				.add(findOrCreateArtist(artistData));
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
		
		return bibRecordDao.findById(bibId).orElseThrow(() -> new NoSuchElementException("No such bibliographic record with ID=" + bibId + " was found."));
	}

	private Publisher findPublisherById(Long publisherId) {
		
		return publisherDao.findById(publisherId).orElseThrow(() -> new NoSuchElementException("No such publisher with ID=" + publisherId + " was found."));
	}

	private Category findCategoryById(Long categoryId) {

		return categoryDao.findById(categoryId).orElseThrow(() -> new NoSuchElementException("No such category with ID=" + categoryId + " was found."));
	}

	private Designer findDesignerById(Long designerId) {

		return designerDao.findById(designerId).orElseThrow(() -> new NoSuchElementException("No such designer with ID=" + designerId + " was found."));
	}

	private Artist findArtistById(Long artistId) {
		
		return artistDao.findById(artistId).orElseThrow(() -> new NoSuchElementException("No such designer with ID=" + artistId + " was found."));
	}
	
	private ItemRecord findItemRecordById(Long itemId) {

		return itemRecordDao.findById(itemId).orElseThrow(() -> new NoSuchElementException("No such item record with ID=" + itemId + " was found."));
	}
	



}