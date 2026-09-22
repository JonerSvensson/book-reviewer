package se.jonas.bookreviewer.service;

import org.springframework.stereotype.Service;
import se.jonas.bookreviewer.model.Review;
import se.jonas.bookreviewer.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + id));
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        boolean removed = reviewRepository.deleteById(id);
        if (!removed) {
            throw new ResourceNotFoundException("Review not found with id " + id);
        }
    }
}