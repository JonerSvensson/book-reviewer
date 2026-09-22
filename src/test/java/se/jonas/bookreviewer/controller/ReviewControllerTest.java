package se.jonas.bookreviewer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jonas.bookreviewer.model.Review;
import se.jonas.bookreviewer.service.ResourceNotFoundException;
import se.jonas.bookreviewer.service.ReviewService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review("Jonas", "The Hitchhiker's Guide to the Galaxy", 10);
        review.setId(1L);
    }

    @Test
    void returnAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].book").value("The Hitchhiker's Guide to the Galaxy"));
    }

    @Test
    void addNewReviewAndReturnCreated() throws Exception {
        String jsonRequest = "{ \"user\": \"Jonas\", \"book\": \"The Hitchhiker's Guide to the Galaxy\", \"rating\": 10 }";
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.book").value("The Hitchhiker's Guide to the Galaxy"));

        verify(reviewService, times(1)).createReview(any(Review.class));
    }

    @Test
    void deleteReviewWhenFound() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());

        verify(reviewService).deleteReview(1L);
    }

    @Test
    void returnNotFoundWhenDeletingNonExistingReview() throws Exception {
        doThrow(new ResourceNotFoundException("Review not found with id 99"))
                .when(reviewService).deleteReview(99L);

        mockMvc.perform(delete("/api/reviews/99"))
                .andExpect(status().isNotFound());
    }
}