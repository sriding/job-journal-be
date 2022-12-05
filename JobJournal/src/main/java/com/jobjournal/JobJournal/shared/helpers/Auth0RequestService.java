package com.jobjournal.JobJournal.shared.helpers;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.aspectj.weaver.ast.Var;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.reactive.function.client.WebClient;

import com.jobjournal.JobJournal.shared.models.app_metadata;
import com.jobjournal.JobJournal.shared.models.auth.Auth0UserInfoResponse;

import reactor.core.publisher.Mono;

public class Auth0RequestService {
    public static Auth0UserInfoResponse getAllUserInfoFromAuth0(String token, String domain) throws Exception {
        try {
            WebClient client = WebClient.create();

            Mono<Auth0UserInfoResponse> monoResponse = client.get()
                    .uri("https://%s/userinfo".formatted(domain))
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Auth0UserInfoResponse.class);

            Auth0UserInfoResponse monoResponseConverted = monoResponse.share().block();

            return monoResponseConverted;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getSubFromUserInfoInAuth0(String token, String domain) throws Exception {
        return getAllUserInfoFromAuth0(token, domain).getSub();
    }

    public static String getFullNameFromUserInfoInAuth0(String token, String domain) throws Exception {
        return getAllUserInfoFromAuth0(token, domain).getName();
    }

    public static String deleteUserFromFromAuth0(String token, String domain, String audience) throws Exception {
        try {
            String auth0Id = getSubFromUserInfoInAuth0(token, domain);
            System.out.println(auth0Id);
            WebClient client = WebClient.builder()
                    .baseUrl("%1$s/users/%2$s".formatted(audience, auth0Id))
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.set("Authorization", token);
                    }).build();

            Mono<String> monoResponse = client.delete().retrieve().bodyToMono(String.class);

            return monoResponse.share().block();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String updateUserFromAuth0(String token, String domain, String audience) throws Exception {
        try {
            String auth0Id = getSubFromUserInfoInAuth0(token, domain);
            System.out.println(token);
            System.out.println(auth0Id);
            WebClient client = WebClient.builder()
                    .baseUrl("%1$s/users/%2$s".formatted(audience, auth0Id))
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.set("Content-Type", "application/json");
                        httpHeaders.set("Authorization", "%s".formatted(token));
                    }).build();

            app_metadata newMetaData = new app_metadata(true);
            Mono<String> monoResponse = client.patch().body(Mono.just(newMetaData), app_metadata.class).retrieve()
                    .bodyToMono(String.class);

            return monoResponse.share().block();
        } catch (Exception e) {
            throw e;
        }
    }
}
