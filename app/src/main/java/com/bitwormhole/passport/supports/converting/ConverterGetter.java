package com.bitwormhole.passport.supports.converting;

import com.bitwormhole.passport.contexts.ClientContext;
import com.bitwormhole.passport.contexts.IComponent;
import com.bitwormhole.passport.data.converters.Converters;
import com.bitwormhole.passport.data.converters.SecretKeyConverter;
import com.bitwormhole.passport.data.converters.UserSpaceConverter;

public class ConverterGetter implements Converters, IComponent {

    private ClientContext context;

    public ConverterGetter() {
    }

    @Override
    public void init(ClientContext cc) {
        this.context = cc;
    }


    @Override
    public SecretKeyConverter getSecretKeyConverter() {
        return context.secretKeyConverter;
    }

    @Override
    public UserSpaceConverter getUserSpaceConverter() {
        return context.userSpaceConverter;
    }

}
