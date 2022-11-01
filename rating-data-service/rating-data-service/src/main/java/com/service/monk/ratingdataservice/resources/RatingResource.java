package com.service.monk.ratingdataservice.resources;

import com.service.monk.ratingdataservice.models.Rating;
import com.service.monk.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId,4);

    }

    @RequestMapping("users/{userId}")
    public UserRating getUsersRating(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("123",4),
                new Rating("456",5),
                new Rating("789",3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRatings(ratings);

        return userRating;


    }
}
