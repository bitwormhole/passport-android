package com.bitwormhole.passport.services;

import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.data.db.RootDatabase;
import com.bitwormhole.passport.data.db.UserDatabase;

public interface DatabaseService {


    UserDatabase loadUserDB(UserSpace space);

    RootDatabase loadRootDB();

    RootDatabase getRootDB();

}
