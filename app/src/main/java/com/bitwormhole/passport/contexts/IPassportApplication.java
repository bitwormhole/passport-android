package com.bitwormhole.passport.contexts;

import android.content.Context;

import com.bitwormhole.passport.services.Services;

public interface IPassportApplication {

    IClient getClient();

    Services getServices();

    Context getContext();

}
