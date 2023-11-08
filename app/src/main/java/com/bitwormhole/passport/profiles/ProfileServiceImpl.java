package com.bitwormhole.passport.contexts;

import com.bitwormhole.passport.services.ProfileService;
import com.bitwormhole.passport.web.dto.ProfileDTO;

import java.util.List;
import java.util.Optional;

public class ProfileServiceImpl implements ProfileService, IComponent {

    @Override
    public ProfileDTO getCurrentProfile() {
        return null;
    }

    @Override
    public List<ProfileDTO> listProfiles() {
        return null;
    }

    @Override
    public Optional<ProfileDTO> getProfileByName(String name) {
        return Optional.empty();
    }

    @Override
    public void init(ClientContext cc) {

    }
}
