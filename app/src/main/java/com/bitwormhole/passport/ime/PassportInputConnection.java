package com.bitwormhole.passport.ime;

import android.view.View;
import android.view.inputmethod.BaseInputConnection;

public class PassportInputConnection extends BaseInputConnection {
    public PassportInputConnection(View targetView, boolean fullEditor) {
        super(targetView, fullEditor);
    }
}
