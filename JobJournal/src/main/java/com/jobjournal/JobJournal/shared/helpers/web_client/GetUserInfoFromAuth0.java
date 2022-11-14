package com.jobjournal.JobJournal.shared.helpers.web_client;

import org.springframework.web.reactive.function.client.WebClient;

import com.jobjournal.JobJournal.shared.models.auth.Auth0UserInfoResponse;

import reactor.core.publisher.Mono;

public class GetUserInfoFromAuth0 {
    public static Auth0UserInfoResponse getAllUserInfoFromAuth0(String token, String domain) throws Exception {
        WebClient client = WebClient.create();

        Mono<Auth0UserInfoResponse> monoResponse = client.get()
                .uri("https://%s/userinfo".formatted(domain))
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Auth0UserInfoResponse.class);

        Auth0UserInfoResponse monoResponseConverted = monoResponse.share().block();

        return monoResponseConverted;
    }

    public static String getSubFromUserInfoInAuth0(String token, String domain) throws Exception {
        return getAllUserInfoFromAuth0(token, domain).getSub();
    }
}
