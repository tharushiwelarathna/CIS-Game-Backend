package com.tharushi.tomatogame.config.security;


import com.tharushi.tomatogame.config.security.custom.CustomOauthException;
import com.tharushi.tomatogame.config.security.custom.CustomTokenEnhancer;
import com.tharushi.tomatogame.service.authService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;


import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
@Log4j2
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userDetailsService;
    private final CustomTokenEnhancer customTokenEnhancer;
    private final Environment environment;



    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer
                .inMemory()

                // ADMIN
                .withClient(SecurityConstants.ADMIN_ID)
                .secret(passwordEncoder.encode(""))
                .authorizedGrantTypes(SecurityConstants.GRANT_TYPE_PASSWORD, SecurityConstants.AUTHORIZATION_CODE, SecurityConstants.REFRESH_TOKEN, SecurityConstants.IMPLICIT)
                .scopes(SecurityConstants.SCOPE_READ, SecurityConstants.SCOPE_WRITE, SecurityConstants.TRUST)
                .accessTokenValiditySeconds(SecurityConstants.ADMIN_ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(SecurityConstants.ADMIN_REFRESH_TOKEN_VALIDITY_SECONDS)
                .and()

                // PLAYER DONOR
                .withClient(SecurityConstants.PLAYER_ID)
                .secret(passwordEncoder.encode(""))
                .authorizedGrantTypes(SecurityConstants.GRANT_TYPE_PASSWORD, SecurityConstants.AUTHORIZATION_CODE, SecurityConstants.REFRESH_TOKEN, SecurityConstants.IMPLICIT)
                .scopes(SecurityConstants.SCOPE_READ, SecurityConstants.SCOPE_WRITE, SecurityConstants.TRUST)
                .accessTokenValiditySeconds(SecurityConstants.PLAYER_ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(SecurityConstants.PLAYER_REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userDetailsService)
                .prefix(environment.getRequiredProperty("spring.mvc.servlet.path"))
                .exceptionTranslator(exception -> {
                    return ResponseEntity.status(UNAUTHORIZED).body(new CustomOauthException(exception.getMessage()));
                });
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SecurityConstants.TOKEN_SIGN_IN_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, accessTokenConverter()));
        return enhancerChain;
    }
}
