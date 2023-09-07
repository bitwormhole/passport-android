package com.bitwormhole.passport.supports.converting;

import com.bitwormhole.passport.components.bo.SecretKeyBO;
import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.data.converters.UserSpaceConverter;
import com.bitwormhole.passport.data.dxo.LongID;
import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.dxo.UserSpaceID;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.utils.ResultSet;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import java.util.ArrayList;
import java.util.List;

public class UserSpaceConverterImpl implements UserSpaceConverter {


    @Override
    public ResultSet<UserSpaceEntity> bo2entity(UserSpaceBO... src) {
        ResultSet<UserSpaceEntity> dst = new ResultSet<>();
        for (UserSpaceBO o1 : src) {
            UserSpaceEntity o2 = new UserSpaceEntity();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = LongID.valueOf(o1.id);
            o2.domain = o1.domain;
            o2.email = o1.email;
        }
        return dst;
    }

    @Override
    public ResultSet<UserSpaceBO> entity2bo(UserSpaceEntity... src) {
        ResultSet<UserSpaceBO> dst = new ResultSet<>();
        for (UserSpaceEntity o1 : src) {
            UserSpaceBO o2 = new UserSpaceBO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = new UserSpaceID(o1.id);
            o2.domain = o1.domain;
            o2.email = o1.email;
        }
        return dst;
    }

    @Override
    public ResultSet<UserSpaceBO> dto2bo(UserSpaceDTO... src) {
        ResultSet<UserSpaceBO> dst = new ResultSet<>();
        for (UserSpaceDTO o1 : src) {
            UserSpaceBO o2 = new UserSpaceBO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = new UserSpaceID(o1.id);
            o2.domain = o1.domain;
            o2.email = o1.email;
        }
        return dst;
    }

    @Override
    public ResultSet<UserSpaceDTO> bo2dto(UserSpaceBO... src) {
        ResultSet<UserSpaceDTO> dst = new ResultSet<>();
        for (UserSpaceBO o1 : src) {
            UserSpaceDTO o2 = new UserSpaceDTO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = LongID.valueOf(o1.id);
            o2.domain = o1.domain;
            o2.email = o1.email;
        }
        return dst;
    }
}
