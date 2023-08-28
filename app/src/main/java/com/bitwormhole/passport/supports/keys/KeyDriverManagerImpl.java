package com.bitwormhole.passport.supports.keys;

import com.bitwormhole.passport.components.security.KeyDriver;
import com.bitwormhole.passport.components.security.KeyDriverManager;
import com.bitwormhole.passport.components.security.KeyDriverRegistration;
import com.bitwormhole.passport.components.security.KeyDriverRegistry;
import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;

import java.util.ArrayList;
import java.util.List;

public class KeyDriverManagerImpl implements KeyDriverManager, IComponent {


    private KeyDriverRegistration[] regList;


    @Override
    public KeyDriver[] findByName(String name) {
        List<KeyDriver> dst = new ArrayList<>();
        KeyDriverRegistration[] src = this.regList;
        for (KeyDriverRegistration r : src) {
            KeyDriver driver = r.driver;
            if (this.isNameEqual(name, r.name) && driver != null) {
                dst.add(driver);
            }
        }
        KeyDriver[] ar = new KeyDriver[dst.size()];
        return dst.toArray(ar);
    }

    private boolean isNameEqual(String name1, String name2) {
        if (name1 == null || name2 == null) {
            return false;
        }
        name1 = name1.trim();
        name2 = name2.trim();
        return name1.equalsIgnoreCase(name2);
    }

    @Override
    public void init(ClientContext cc) {
        List<KeyDriverRegistry> list = this.loadRegList1(cc);
        this.regList = this.loadRegList2(list);
    }

    private KeyDriverRegistration[] loadRegList2(List<KeyDriverRegistry> src) {
        List<KeyDriverRegistration> dst = new ArrayList<>();
        for (KeyDriverRegistry item1 : src) {
            KeyDriverRegistration[] s2 = item1.ListRegistrations();
            for (KeyDriverRegistration item2 : s2) {
                dst.add(item2);
            }
        }
        KeyDriverRegistration[] ar = new KeyDriverRegistration[dst.size()];
        return dst.toArray(ar);
    }

    private List<KeyDriverRegistry> loadRegList1(ClientContext cc) {
        List<Object> src = cc.components;
        List<KeyDriverRegistry> dst = new ArrayList<>();
        for (Object item : src) {
            if (item instanceof KeyDriverRegistry) {
                KeyDriverRegistry reg = (KeyDriverRegistry) item;
                dst.add(reg);
            }
        }
        return dst;
    }
}
