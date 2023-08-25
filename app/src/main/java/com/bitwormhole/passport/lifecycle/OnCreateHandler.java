package com.bitwormhole.passport.lifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;

public interface OnCreateHandler {

    void handleCreate(Bundle savedInstanceState, PersistableBundle persistentState);

}
