package com.bitwormhole.passport;

import android.inputmethodservice.InputMethodService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.bitwormhole.passport.ime.IMEContext;
import com.bitwormhole.passport.ime.PassportKeyboardView;

public class PassportInputMethodService extends InputMethodService {

    private final IMEContext mImeContext;


    public PassportInputMethodService() {
        this.mImeContext = new IMEContext();
    }


    @Override
    public View onCreateInputView() {

        PassportKeyboardView view = new PassportKeyboardView(this);
        LayoutInflater li = getLayoutInflater();

        //  inputView.setOnKeyboardActionListener(this);
        //  inputView.setKeyboard(latinKeyboard);

        view.init(this.mImeContext);
        view = view.layout(li);
        return view;
    }


    @Override
    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
        // super.onStartInputView(editorInfo, restarting);
        // this.getCurrentInputConnection() ;

        this.mImeContext.service = this;
        this.mImeContext.editorInfo = editorInfo;
    }

}
