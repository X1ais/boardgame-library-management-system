package library.controller.model;

import library.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {
	
	private Long reviewId;
	private int rating;
	private String reviewBody;
	private BibliographicRecordDTO bibRecord;
	private BorrowerDTO borrower;
	
	public ReviewDTO(Review review) {
		this.reviewId = review.getReviewId();
		this.rating = review.getRating();
		this.reviewBody = review.getReviewBody();
	}

}
