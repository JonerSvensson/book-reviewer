package se.jonas.bookreviewer.repository;

import org.springframework.stereotype.Repository;
import se.jonas.bookreviewer.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReviewRepository {


    // Create one instance of each object
    private final List<Review> reviews = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    // Hard coded entries
    public ReviewRepository() {
        save(new Review("Jonas","Dark Matter", 8));
        save(new Review("Jonas","Silent Patient", 2));
    }

    public List<Review> findAll() {
        return reviews;
    }

    public Optional<Review> findById(Long id) {
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst();
    }

    public Review save(Review review) {
        review.setId(nextId.getAndIncrement());
        reviews.add(review);
        return review;
    }

    public boolean deleteById(Long id) {
        return reviews.removeIf(review -> review.getId().equals(id));
    }
}
