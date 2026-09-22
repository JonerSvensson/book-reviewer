package se.jonas.bookreviewer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.jonas.bookreviewer.model.Review;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryTest {

    private ReviewRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ReviewRepository();
    }

    @Test
    void findAll_startsWithSeedData() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void save_addsReviewWithGeneratedId() {
        Review review = new Review("Jonas", "The Hitchhiker's Guide to the Galaxy", 10);

        Review saved = repository.save(review);

        assertNotNull(saved.getId());
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void findById_returnsSavedReview() {
        Review saved = repository.save(new Review("Jonas", "The Hitchhiker's Guide to the Galaxy", 10));

        Optional<Review> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("The Hitchhiker's Guide to the Galaxy", found.get().getBook());
    }

    @Test
    void findById_returnsEmptyForUnknownId() {
        Optional<Review> found = repository.findById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void deleteById_removesReview() {
        Review saved = repository.save(new Review("Jonas", "The Hitchhiker's Guide to the Galaxy", 10));

        boolean deleted = repository.deleteById(saved.getId());

        assertTrue(deleted);
        assertFalse(repository.findAll().contains(saved));
    }

    @Test
    void deleteById_returnsFalseForUnknownId() {
        boolean deleted = repository.deleteById(999L);

        assertFalse(deleted);
    }
}