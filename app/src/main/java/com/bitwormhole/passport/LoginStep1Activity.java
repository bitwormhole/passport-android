package com.bitwormhole.passport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.LoginContext;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.GuiBinder;
import com.bitwormhole.passport.utils.SecurityUtils;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.KeyPairDTO;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

import java.security.KeyPair;

public class LoginStep1Activity extends BaseActivity {

    private EditText mEditEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OnCreateContext occ = this.onCreateBegin(savedInstanceState, null);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login_step1);

        mEditEmail = findViewById(R.id.edit_email);

        GuiBinder binder = GuiBinder.getInstance(this);
        binder.setupOnClickListener(R.id.button_prev, (view) -> {
            handlePrev();
        });
        binder.setupOnClickListener(R.id.button_next, (view) -> {
            handleNext();
        });

        this.onCreateEnd(occ);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void handleNext() {
        IPassportApplication app = PassportApplication.getInstance(this);
        LoginContext lc = app.getClient().getLoginContext(true);
        lc.email = this.mEditEmail.getText().toString();

        Intent i = new Intent(this, LoginStep2Activity.class);
        this.startActivity(i);
    }

    private void handlePrev() {
        this.finish();
    }
}
