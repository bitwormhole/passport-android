package com.bitwormhole.passport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.SecurityUtils;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.KeyPairDTO;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;

import java.security.KeyPair;

public class LoginActivity extends BaseActivity {

    private EditText mEditEmail;
    private EditText mEditVeriCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OnCreateContext occ = this.onCreateBegin(savedInstanceState, null);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        mEditEmail = findViewById(R.id.edit_email);
        mEditVeriCode = findViewById(R.id.edit_v_code);

        this.findViewById(R.id.button_login).setOnClickListener((view) -> {
            handleLogin();
        });

        this.findViewById(R.id.button_request_v_code).setOnClickListener((view) -> {
            handleRequestVCode();
        });

        this.onCreateEnd(occ);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.handleListAll();
    }

    private void handleRequestVCode() {
        IPassportApplication app = PassportApplication.getInstance(this);
        RestRequest req = new RestRequest();
        req.method = "POST";
        req.type = "verifications";
        req.id = "get";
        req.data = HttpEntity.createWithPOJO("a-json-string");

        RestClientService rest = app.getServices().getRestClients();
        Promise<RestResponse> p = rest.Execute(req);
        p.onThen((res) -> {
            RestResponse resp = res.getValue();
            Log.i("http ", "msg:" + resp.message);
            return null;
        });
    }

    private void handleLogin() {

        UserDTO session1 = new UserDTO();
        session1.domain = ("passport.bitwormhole.com");
        session1.email = (mEditEmail.getText().toString());

        IPassportApplication app = PassportApplication.getInstance(this);
        ISession session = app.getServices().getSessions().open(session1);

        //  KeyPair kp = app.getClient().getKeyPairs().getUserKeyPair(session, true);
        //  log("user-key-pair", kp);
    }

    private void handleListAll() {
        IPassportApplication app = PassportApplication.getInstance(this);
        //      KeyPairDTO[] all = app.getServices().getKeyPairs().
        //    for (KeyPairDTO item : all) {
        //      Log.i("key-pair", item.getAlias());
        // }
    }

    private void log(String tag, KeyPair kp) {

        byte[] sum = SecurityUtils.computeFingerprintSHA1(kp.getPublic());
        String fingerprint = BinaryUtils.stringify(sum);

        String pem = SecurityUtils.toPEM(kp.getPublic());

        Log.i(tag, "fingerprint:" + fingerprint);
        Log.i(tag, "pem:" + pem);
    }

}
