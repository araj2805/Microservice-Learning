package com.service.monk.moviecatalogservice.resource;

import com.netflix.discovery.DiscoveryClient;
import com.service.monk.moviecatalogservice.models.CatalogItem;
import com.service.monk.moviecatalogservice.models.Movie;
import com.service.monk.moviecatalogservice.models.Rating;
import com.service.monk.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog (@PathVariable("userId") String userId) {



        // Get all rating for the user
        // Get movie info for all the movie user's watched
        // Put them together

//        UserRating ratings = restTemplate.getForObject("http://localhost:8083/rating/users/" + userId, UserRating.class);
        UserRating ratings = restTemplate.getForObject("http://rating-data-service/rating/users/" + userId, UserRating.class);

        return ratings.getUserRatings().stream().map(rating ->
        {
//            RestTemplate way
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

//            WebClient Way
           /* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
*/


            return new CatalogItem(movie.getMovieName(), "Test", rating.getRating());
        }).collect(Collectors.toList());


//        return Collections.singletonList(
//                new CatalogItem("Avengers","Test",5)
//        );
    }
}
