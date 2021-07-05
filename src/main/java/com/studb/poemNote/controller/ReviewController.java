package com.studb.poemNote.controller;

import java.util.List;

import com.studb.poemNote.domain.review.Review;
import com.studb.poemNote.domain.review.ReviewDto;
import com.studb.poemNote.domain.review.ReviewNoPublishedDto;
import com.studb.poemNote.domain.review.ReviewTitleDto;
import com.studb.poemNote.service.ReviewService;
import com.studb.poemNote.utils.ApiResultForm;
import com.studb.poemNote.utils.OperationResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;

    @GetMapping("/review")
    public ApiResultForm<List<ReviewDto>> readFromReview(){
        return ApiResultForm.result(reviewService.findAll());
    }

    @GetMapping("/review/{textId}")
    public ApiResultForm<ReviewDto> readOneFreomReview(@PathVariable String textId){
        return ApiResultForm.result(reviewService.findAny(textId));
    }

    @GetMapping("/review/title")
    public ApiResultForm<List<ReviewTitleDto>> readTitleFromReview() {
        return ApiResultForm.result(reviewService.findReviewTitle());
    }

    @GetMapping("/review/nopublished")
    public ApiResultForm<List<ReviewNoPublishedDto>> readNoPublishedFromReview(){
        return ApiResultForm.result(reviewService.findAllNoPublished());
    }

    @GetMapping("/review/published/{textId}")
    public ApiResultForm<List<ReviewDto>> readPublishedFromReview(@PathVariable String textId){
        return ApiResultForm.result(reviewService.findAllPublished(textId));
    }

    @PostMapping("/review")
    public ApiResultForm<OperationResult> writeReview(@RequestBody Review review){
        return ApiResultForm.result(reviewService.save(review));
    }

    @DeleteMapping("/review/{textId}")
    public ApiResultForm<OperationResult> eraseReview(@PathVariable String textId){
        return ApiResultForm.result(reviewService.delete(textId));
    }

    @PutMapping("/review")
    public ApiResultForm<OperationResult> rewriteReview(@RequestBody Review review){
        return ApiResultForm.result(reviewService.update(review));
    }

    @PostMapping("/review/publish")
    public ApiResultForm<OperationResult> publishReview(@RequestBody Review review){
        return ApiResultForm.result(reviewService.publish(review));
    }

    @PutMapping("/review/publish")
    public ApiResultForm<OperationResult> rewritePublishReview(@RequestBody Review review){
        return ApiResultForm.result(reviewService.updatePublished(review));
    }

}
