package com.bitwormhole.passport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.components.security.PublicKeyHolder;
import com.bitwormhole.passport.components.userspace.UserSpace;
import com.bitwormhole.passport.components.userspace.UserSpaceManager;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.LoginContext;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.supports.rest.RESTException;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.GuiBinder;
import com.bitwormhole.passport.utils.SecurityUtils;
import com.bitwormhole.passport.utils.ThreadChecker;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.HttpMethods;
import com.bitwormhole.passport.web.RestApis;
import com.bitwormhole.passport.web.RestClient;
import com.bitwormhole.passport.web.RestContext;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.RestTypes;
import com.bitwormhole.passport.web.dto.AuthDTO;
import com.bitwormhole.passport.web.dto.KeyPairDTO;
import com.bitwormhole.passport.web.dto.PublicKeyDTO;
import com.bitwormhole.passport.web.dto.ServiceDTO;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;
import com.bitwormhole.passport.web.vo.AuthVO;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.List;
import java.util.Optional;

public class LoginStep3Activity extends BaseActivity {

    // view
    private TextView mTextEmailAddress;
    private EditText mEditVeriCode;


    // data
    private LoginContext mLoginContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OnCreateContext occ = this.onCreateBegin(savedInstanceState, null);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login_step3);

        mTextEmailAddress = findViewById(R.id.text_email_address);
        mEditVeriCode = findViewById(R.id.edit_veri_code);

        GuiBinder gb = GuiBinder.getInstance(this);
        gb.setupOnClickListener(R.id.button_prev, (v) -> {
            this.finish();
        });
        gb.setupOnClickListener(R.id.button_login, (v) -> {
            handleClickLogin();
        });
        gb.setupOnClickListener(R.id.button_send_veri_code, (v) -> {
            handleClickSendVerificationCode();
        });

        this.onCreateEnd(occ);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IPassportApplication app = PassportApplication.getInstance(this);
        mLoginContext = app.getClient().getLoginContext(false);
        mTextEmailAddress.setText(mLoginContext.email);

    }


    private void handleClickSendVerificationCode() {

        ThreadChecker.checkInUI();

        AuthDTO dto = new AuthDTO();
        AuthVO vo = new AuthVO();
        vo.Auth = new AuthDTO[]{dto};

        dto.Mechanism = "email";
        dto.Account = mLoginContext.email;
        dto.Secret = "";

        RestRequest req = new RestRequest();
        req.method = HttpMethods.POST;
        req.api = RestApis.Default;
        req.type = RestTypes.Auth;
        req.id = "send-verification-mail";
        req.data = HttpEntity.createWithPOJO(vo);

        TaskContext tc = Promise.createTaskContext(this);
        Promise.execute(tc, RestResponse.class, () -> {
            return sendVerificationCode(req);
        }).onThen((res) -> {
            this.showSendOKDialog();
            return null;
        }).onCatch((res) -> {
            return null;
        });
        Promise.start(tc);
    }


    private void showSendOKDialog() {
        ThreadChecker.checkInUI();
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Done");
        b.setMessage("Send OK");
        b.setNegativeButton(R.string.close, (v1, v2) -> {
        });
        b.show();
    }

    private RestResponse sendVerificationCode(RestRequest req) throws IOException {

        ThreadChecker.checkInWorker();

        IPassportApplication app = PassportApplication.getInstance(this);
        ISession session = app.getServices().getSessions().openCurrent();
        RestClient rest = session.getRESTClient();
        return rest.Do(req);
    }

    private RestResponse login(RestRequest req, AuthVO vo, AuthDTO dto) throws IOException {
        ThreadChecker.checkInWorker();
        IPassportApplication app = PassportApplication.getInstance(this);

        ISession session = app.getServices().getSessions().openCurrent();
        RestClient rest = session.getRESTClient();
        PublicKeyHolder publicKey = session.getKeyPair().getPublicKey();

        String pk = this.encodePublicKeyAsDTO(publicKey);
        dto.setParam("public_key", pk);

        req.method = HttpMethods.POST;
        req.api = RestApis.Default;
        req.type = RestTypes.Auth;
        req.id = "sign-up-in";
        req.data = HttpEntity.createWithPOJO(vo);

        return rest.Do(req);
    }


    private String encodePublicKeyAsDTO(PublicKeyHolder pk) {
        PublicKeyDTO dto = pk.export();
        Gson gs = new Gson();
        String json = gs.toJson(dto);
        return Base64.encodeToString(json.getBytes(), 0);
    }


    private void handleClickLogin() {

        ThreadChecker.checkInUI();

        AuthDTO dto = new AuthDTO();
        AuthVO vo = new AuthVO();
        RestRequest req = new RestRequest();

        vo.Auth = new AuthDTO[]{dto};
        dto.Mechanism = "email";
        dto.Account = mLoginContext.email;
        dto.Secret = Base64.encodeToString(getInputVCode(), 0);

        TaskContext tc = Promise.createTaskContext(this);
        Promise.execute(tc, RestResponse.class, () -> {
            return login(req, vo, dto);
        }).onThen((res) -> {
            AuthVO result = res.data.bindJSON(AuthVO.class);
            result.toString(); // demo
            return null;
        }).onCatch((res) -> {
            this.handleSignInError(res);
            return null;
        });
        Promise.start(tc);
    }

    private void handleSignInError(Throwable t) {
        ThreadChecker.checkInUI();
        if (t instanceof RESTException) {
            RESTException exp = (RESTException) t;

            String str = exp.getResponse().data.getString();

            AuthVO vo = exp.getResponse().data.bindJSON(AuthVO.class);
            Log.w("Sign-In Error", "" + vo.error);
        }
    }

    private byte[] getInputVCode() {
        ThreadChecker.checkInUI();
        final Charset enc = StandardCharsets.UTF_8;
        String code = mEditVeriCode.getText().toString();
        return code.trim().getBytes(enc);
    }
}
