package com.bitwormhole.passport.supports.converting;

import com.bitwormhole.passport.components.bo.SecretKeyBO;
import com.bitwormhole.passport.data.converters.SecretKeyConverter;
import com.bitwormhole.passport.data.dxo.LongID;
import com.bitwormhole.passport.data.dxo.SecretKeyID;
import com.bitwormhole.passport.data.entity.SecretKeyEntity;
import com.bitwormhole.passport.data.entity.UserSpaceEntity;
import com.bitwormhole.passport.utils.ResultSet;
import com.bitwormhole.passport.web.dto.SecretKeyDTO;
import com.bitwormhole.passport.web.dto.UserSpaceDTO;

import java.util.ArrayList;
import java.util.List;

public class SecretKeyConverterImpl implements SecretKeyConverter {

    @Override
    public ResultSet<SecretKeyBO> dto2bo(SecretKeyDTO... src) {
        ResultSet<SecretKeyBO> dst = new ResultSet<>();
        for (SecretKeyDTO o1 : src) {
            SecretKeyBO o2 = new SecretKeyBO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = new SecretKeyID(o1.id);
        }
        return dst;
    }

    @Override
    public ResultSet<SecretKeyDTO> bo2dto(SecretKeyBO... src) {
        ResultSet<SecretKeyDTO> dst = new ResultSet<>();
        for (SecretKeyBO o1 : src) {
            SecretKeyDTO o2 = new SecretKeyDTO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = LongID.valueOf(o1.id);
        }
        return dst;
    }

    @Override
    public ResultSet<SecretKeyBO> entity2bo(SecretKeyEntity... src) {
        ResultSet<SecretKeyBO> dst = new ResultSet<>();
        for (SecretKeyEntity o1 : src) {
            SecretKeyBO o2 = new SecretKeyBO();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = new SecretKeyID(o1.id);
        }
        return dst;
    }

    @Override
    public ResultSet<SecretKeyEntity> bo2entity(SecretKeyBO... src) {
        ResultSet<SecretKeyEntity> dst = new ResultSet<>();
        for (SecretKeyBO o1 : src) {
            SecretKeyEntity o2 = new SecretKeyEntity();
            dst.add(o2);
            // fields:
            BaseFields.convertBaseFields(o1, o2);
            o2.id = LongID.valueOf(o1.id);
        }
        return dst;
    }
}
