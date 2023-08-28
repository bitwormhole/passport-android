package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.web.dto.UserDTO;

public interface UserSpaceService {


    UserSpace getRoot();

    UserSpace getSpace(UserDTO user);

}
