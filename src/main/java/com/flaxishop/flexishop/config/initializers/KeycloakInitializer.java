package com.flaxishop.flexishop.config.initializers;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KeycloakInitializer {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Bean(name = "keycloakInitializerRunner")
    public CommandLineRunner keycloakInitializer() {
        return args -> {
            try {
                // Authenticate using client credentials
                Keycloak keycloak = KeycloakBuilder.builder()
                        .serverUrl(authServerUrl)
                        .realm(realm)
                        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .build();


                // Access the groups resource in the Keycloak realm
                GroupsResource groupsResource = keycloak.realm(realm).groups();

                // Create groups if they don't exist
                createGroupIfNotExists(groupsResource, "admin");
                createGroupIfNotExists(groupsResource, "customer");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private void createGroupIfNotExists(GroupsResource groupsResource, String groupName) {
        // Check if group exists, if not, create it
        groupsResource.groups().stream()
                .filter(group -> groupName.equals(group.getName()))
                .findFirst()
                .ifPresentOrElse(group -> {
                    System.out.println("Group '" + groupName + "' already exists.");
                }, () -> {
                    GroupRepresentation newGroup = new GroupRepresentation();
                    newGroup.setName(groupName);
                    groupsResource.add(newGroup);
                    System.out.println("Created group: " + groupName);
                });
    }
}
