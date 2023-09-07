package com.bitwormhole.passport.services;

import com.bitwormhole.passport.web.dto.ProfileDTO;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    ProfileDTO getCurrentProfile();

    List<ProfileDTO> listProfiles();

    Optional<ProfileDTO> getProfileByName(String name);

}
