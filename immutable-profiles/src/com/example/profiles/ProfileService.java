package com.example.profiles;

import java.util.Objects;

/**
 * Creates immutable profiles using Builder pattern with centralized validation.
 */
public class ProfileService {

    public UserProfile createMinimal(String id, String email) {
        return new UserProfile.Builder(id, email).build();
    }

    public UserProfile createWithDisplayName(String id, String email, String displayName) {
        return new UserProfile.Builder(id, email)
            .displayName(displayName)
            .build();
    }

    public UserProfile createComplete(String id, String email, String phone, String displayName, 
                                    String address, boolean marketingOptIn, String twitter, String github) {
        return new UserProfile.Builder(id, email)
            .phone(phone)
            .displayName(displayName)
            .address(address)
            .marketingOptIn(marketingOptIn)
            .twitter(twitter)
            .github(github)
            .build();
    }
}
