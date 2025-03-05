package com.crezam.auth_service.client;

import com.crezam.auth_service.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://USER-SERVICE/user").build();
    }

    public User createUser(User user) {
        return webClient.post()
                .uri("/create")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User getUserByEmail(String email) {
        return webClient.get()
                .uri("/getByEmail/{email}", email)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public String updatePassword(String email, String newPassword) {
        return webClient.put()
                .uri("/updatePassword")
                .bodyValue("")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
