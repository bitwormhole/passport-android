package com.bitwormhole.passport.supports.sessions;

import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.services.UserSpaceService;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.HashUtils;
import com.bitwormhole.passport.web.dto.UserDTO;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UserSpaceServiceImpl implements UserSpaceService, IComponent {

    private File filesDir;

    @Override
    public UserSpace getRoot() {
        File p = getSpacePath("root");
        return new UserSpaceImpl(p);
    }

    @Override
    public UserSpace getSpace(UserDTO user) {
        String name = getSpaceName(user);
        File p = getSpacePath("home/" + name);
        return new UserSpaceImpl(p);
    }

    private String getSpaceName(UserDTO user) {
        String src = user.domain + "/" + user.email;
        Charset enc = StandardCharsets.UTF_8;
        byte[] raw = src.getBytes(enc);
        byte[] sum = HashUtils.computeMD5sum(raw);
        String str = BinaryUtils.stringify(sum);
        return "user-" + str;
    }

    private File getSpacePath(String p) {
        return new File(filesDir, p);
    }

    @Override
    public void init(ClientContext cc) {
        this.filesDir = cc.context.getFilesDir();
    }
}
