package se.jonas.bookreviewer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jonas.bookreviewer.model.Review;
import se.jonas.bookreviewer.repository.ReviewRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void getReview_returnsReviewWhenFound() {
        Review review = new Review("Jonas", "Dune", 9);
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReview(1L);

        assertEquals("Dune", result.getBook());
    }

    @Test
    void getReview_throwsWhenNotFound() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReview(99L));
    }

    @Test
    void createReview_delegatesToRepository() {
        Review review = new Review("Jonas", "Dune", 9);
        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.createReview(review);

        assertEquals(review, result);
        verify(reviewRepository).save(review);
    }

    @Test
    void deleteReview_throwsWhenNotFound() {
        when(reviewRepository.deleteById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(99L));
    }
}