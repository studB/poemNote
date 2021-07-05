package com.studb.poemNote.repository.review;

import java.sql.Timestamp;
import java.util.List;

import com.studb.poemNote.domain.review.Review;
import com.studb.poemNote.domain.review.ReviewNoPublishedDto;
import com.studb.poemNote.domain.review.ReviewTitleDto;

public interface ReviewRepository {
    
    List<Review> findAll();

    List<Review> findAny(String textId);

    List<ReviewTitleDto> findReviewTitle();

    List<ReviewNoPublishedDto> findAllNoPublished();

    List<Review> findAllPublished(String textId);

    void save(Review review, Timestamp timestamp);

    int delete(String textId);

    int update(Review review, Timestamp timestmap);

    void publish(Review review, Timestamp timestamp);

    int updatePublished(Review review, Timestamp timestamp);
    
}
