package com.bitwormhole.passport.ime;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bitwormhole.passport.R;

public class PassportKeyboardView extends LinearLayout implements View.OnClickListener {


    private IMEContext mImeContext;

    public PassportKeyboardView(Context context) {
        super(context);
    }

    public PassportKeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(IMEContext ctx) {
        this.mImeContext = ctx;
    }

    public PassportKeyboardView layout(LayoutInflater li) {

        PassportKeyboardView view;
        view = (PassportKeyboardView) li.inflate(R.layout.activity_ime_keyboard, this);

        this.findViewById(R.id.button_input_account).setOnClickListener((v) -> {
            this.handleClickAccount(mImeContext.service);
        });

        this.findViewById(R.id.button_input_password).setOnClickListener((v) -> {
            this.handleClickPassword(mImeContext.service);
        });

        return view;
    }

    private void handleClickAccount(InputMethodService service) {
        InputConnection conn = service.getCurrentInputConnection();
        conn.commitText("hello", 1);
    }

    private void handleClickPassword(InputMethodService service) {
        InputConnection conn = service.getCurrentInputConnection();
        if (this.isReadyForPassword()) {
            conn.commitText("world", 1);
        } else {
            this.displayToast("not a password input box");
        }
    }

    private void displayToast(String text) {
        Context ctx = this.getContext();
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }


    private boolean isReadyForPassword() {
        IMEContext ctx = mImeContext;
        if (ctx == null) {
            return false;
        }
        EditorInfo info = ctx.editorInfo;
        if (info == null) {
            return false;
        }
        switch (info.inputType) {
            case InputType.TYPE_NUMBER_VARIATION_PASSWORD:
            case InputType.TYPE_TEXT_VARIATION_PASSWORD:
            case InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
            case InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD:
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onClick(View view) {

    }

}
