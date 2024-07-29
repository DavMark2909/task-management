package application.feign;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor oauth2FeignInterceptor(OAuth2AuthorizedClientManager manager) {
        return requestTemplate -> {
            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId("task-management")
                    .principal(SecurityContextHolder.getContext().getAuthentication())
                    .build();
            OAuth2AuthorizedClient authorizedClient = manager.authorize(authorizeRequest);
            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                requestTemplate.header("Authorization", "Bearer " + accessToken);
            }
        };
    }

}
