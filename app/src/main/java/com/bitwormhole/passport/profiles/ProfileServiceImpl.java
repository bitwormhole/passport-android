package com.bitwormhole.passport.profiles;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.ProfileService;
import com.bitwormhole.passport.web.dto.ProfileDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ProfileServiceImpl implements ProfileService, IComponent {

    private ProfileDTO current;
    private ProfileDTO release;
    private ProfileDTO debug;

    public ProfileServiceImpl() {
        debug = ProfileFactoryDebug.get();
        release = ProfileFactoryRelease.get();
        current = debug;
    }

    @Override
    public ProfileDTO getCurrentProfile() {
        return current;
    }

    @Override
    public List<ProfileDTO> listProfiles() {
        List<ProfileDTO> list = new ArrayList<>();
        list.add(release);
        list.add(debug);
        return list;
    }

    @Override
    public Optional<ProfileDTO> getProfileByName(String name) {
        return Optional.empty();
    }

    @Override
    public void init(ClientContext cc) {

    }
}
