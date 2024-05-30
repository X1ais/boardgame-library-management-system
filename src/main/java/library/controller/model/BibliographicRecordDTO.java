package library.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import library.entity.Artist;
import library.entity.BibliographicRecord;
import library.entity.Category;
import library.entity.Designer;
import library.entity.Publisher;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BibliographicRecordDTO {
	
	private Long bibId;
	private String bibName;
	private int minNumOfPlayers;
	private int maxNumOfPlayers;
	private int minAge;
	private int playtime;
	private BigDecimal complexity;
	private String edition;
	private Set<PublisherDTO> publishers = new HashSet<>();
	private Set<CategoryDTO> categories = new HashSet<>();
	private Set<DesignerDTO> designers = new HashSet<>();
	private Set<ArtistDTO> artists = new HashSet<>();
	private Set<String> reviews = new HashSet<>();
	private Set<String> items = new HashSet<>();
	
	public BibliographicRecordDTO(BibliographicRecord bibRecord) {
		this.bibId = bibRecord.getBibId();
		this.bibName = bibRecord.getBibName();
		this.minNumOfPlayers = bibRecord.getMinNumOfPlayers();
		this.maxNumOfPlayers = bibRecord.getMaxNumOfPlayers();
		this.minAge = bibRecord.getMinAge();
		this.playtime = bibRecord.getPlaytime();
		this.complexity = bibRecord.getComplexity();
		this.edition = bibRecord.getEdition();
		
		for (Publisher publisher : bibRecord.getPublishers()) {
			this.publishers.add(new PublisherDTO(publisher));
		}
		
		for (Category cat : bibRecord.getCategories()) {
			this.categories.add(new CategoryDTO(cat));
		}
		
		for (Designer designer : bibRecord.getDesigners()) {
			this.designers.add(new DesignerDTO(designer));
		}
		
		for (Artist artist : bibRecord.getArtists()) {
			this.artists.add(new ArtistDTO(artist));
		}
		
	}
	
	public BibliographicRecordDTO(Long bibId, String bibName, int minNumOfPlayers, int maxNumOfPlayers, int minAge, int playtime, BigDecimal complexity, String edition) {
		this.bibId = bibId;
		this.bibName = bibName;
		this.minNumOfPlayers = minNumOfPlayers;
		this.maxNumOfPlayers = maxNumOfPlayers;
		this.minAge = minAge;
		this.playtime = playtime;
		this.complexity = complexity;
		this.edition = edition;
	}
	
	public BibliographicRecord toBibRecord() {
		
		BibliographicRecord bibRecord = new BibliographicRecord();
		
		bibRecord.setBibId(bibId);
		bibRecord.setBibName(bibName);
		bibRecord.setMinNumOfPlayers(minNumOfPlayers);
		bibRecord.setMaxNumOfPlayers(maxNumOfPlayers);
		bibRecord.setMinAge(minAge);
		bibRecord.setPlaytime(playtime);
		bibRecord.setComplexity(complexity);
		bibRecord.setEdition(edition);
		
		return bibRecord;
	}
	
	@Data
	@NoArgsConstructor
	public static class PublisherDTO {
		private Long publisherId;
		private String publisherName;
		
		public PublisherDTO(Publisher publisher) {
			this.publisherId = publisher.getPublisherId();
			this.publisherName = publisher.getPublisherName();
		}

		public Publisher toPublisher() {
			
			Publisher publisher = new Publisher();
			
			publisher.setPublisherId(publisherId);
			publisher.setPublisherName(publisherName);
						
			return publisher;
			
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CategoryDTO {
		
		private Long categoryId;
		private String categoryName;
		
		public CategoryDTO(Category category) {
			this.categoryId = category.getCategoryId();
			this.categoryName = category.getCategoryName();
		}

		public Category toCategory() {
			
			Category category = new Category();
			
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName);
			
			return category;			
			
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class DesignerDTO {
		
		private Long designerId;
		private String firstName;
		private String lastName;
		
		public DesignerDTO(Designer designer) {
			
			this.designerId = designer.getDesignerId();
			this.firstName = designer.getFirstName();
			this.lastName = designer.getLastName();
		}
		
		public Designer toDesigner() {
			Designer designer = new Designer();
			
			designer.setDesignerId(designerId);
			designer.setFirstName(firstName);
			designer.setLastName(lastName);
			
			return designer;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ArtistDTO {
		
		private Long artistId;
		private String firstName;
		private String lastName;
		
		public ArtistDTO(Artist artist) {
			
			this.artistId = artist.getArtistId();
			this.firstName = artist.getFirstName();
			this.lastName = artist.getLastName();
		}
		
		public Artist toArtist() {
			
			Artist artist = new Artist();
			
			artist.setArtistId(artistId);
			artist.setFirstName(firstName);
			artist.setLastName(lastName);
			
			return artist;
		}
	}

}
