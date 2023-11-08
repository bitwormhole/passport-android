package com.bitwormhole.passport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bitwormhole.passport.components.bo.UserSpaceBO;
import com.bitwormhole.passport.contexts.IPassportApplication;
import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.contexts.LoginContext;
import com.bitwormhole.passport.services.RestClientService;
import com.bitwormhole.passport.services.ServerDiscoverService;
import com.bitwormhole.passport.services.SessionManager;
import com.bitwormhole.passport.tasks.Promise;
import com.bitwormhole.passport.tasks.TaskContext;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.GuiBinder;
import com.bitwormhole.passport.utils.SecurityUtils;
import com.bitwormhole.passport.web.HttpEntity;
import com.bitwormhole.passport.web.RestRequest;
import com.bitwormhole.passport.web.RestResponse;
import com.bitwormhole.passport.web.dto.KeyPairDTO;
import com.bitwormhole.passport.web.dto.ServiceProvider;
import com.bitwormhole.passport.web.dto.SessionDTO;
import com.bitwormhole.passport.web.dto.UserDTO;
import com.bitwormhole.passport.web.vo.ServicesVO;

import java.security.KeyPair;

public class LoginStep2Activity extends BaseActivity {

    // view
    private EditText mEditDomain;
    private TextView mTextDomain;
    private TextView mTextDomainLabel;
    private Button mButtonSelect;

    // data
    private LoginContext mLoginContext;
    private ServiceProvider[] mProviders;
    private ServiceProvider mProviderSelection;
    private ServicesVO mProviderServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        OnCreateContext occ = this.onCreateBegin(savedInstanceState, null);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login_step2);

        mEditDomain = findViewById(R.id.edit_domain);
        mTextDomain = findViewById(R.id.text_domain);
        mTextDomainLabel = findViewById(R.id.text_domain_label);
        mButtonSelect = findViewById(R.id.button_select_provider);

        GuiBinder binder = GuiBinder.getInstance(this);
        binder.setupOnClickListener(R.id.button_prev, (view) -> {
            this.finish();
        });
        binder.setupOnClickListener(R.id.button_next, (view) -> {
            this.handleClickNext();
        });
        binder.setupOnClickListener(R.id.button_select_provider, (view) -> {
            handleSelectProvider();
        });

        this.onCreateEnd(occ);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loadData();

        final int v = View.INVISIBLE;
        mTextDomainLabel.setVisibility(v);
        mTextDomain.setVisibility(v);
        mEditDomain.setVisibility(v);
    }

    private void loadData() {
        IPassportApplication app = PassportApplication.getInstance(this);
        LoginContext lc = app.getClient().getLoginContext(false);
        ServiceProvider[] providers = app.getServices().getServerDiscoverService().listServiceProviders();
        mProviders = providers;
        mLoginContext = lc;
    }


    private void handleSelectProvider() {

        ServiceProvider[] src = this.mProviders;
        int itemResID = android.R.layout.simple_list_item_1;
        ArrayAdapter<ServiceProvider> adapter = new ArrayAdapter<>(this, itemResID);
        adapter.addAll(src);

        AlertDialog.Builder listDialog = new AlertDialog.Builder(this);
        listDialog.setTitle("请选择服务商");
        listDialog.setAdapter(adapter, (v1, index) -> {
            ServiceProvider item = src[index];
            handleProviderSelected(item);
        });
        listDialog.show();
    }

    private void handleProviderSelected(ServiceProvider item) {
        if (item == null) {
            return;
        }
        this.mProviderSelection = item;
        mButtonSelect.setText(item.label);
        mTextDomain.setText(item.domain);
        if (item.isCustom) {
            mTextDomain.setVisibility(View.GONE);
            mEditDomain.setVisibility(View.VISIBLE);
        } else {
            mTextDomain.setVisibility(View.VISIBLE);
            mEditDomain.setVisibility(View.GONE);
        }
        mTextDomainLabel.setVisibility(View.VISIBLE);
    }

    private Promise<ServicesVO> fetchProviderServices(TaskContext tc, ServiceProvider provider) {
        IPassportApplication app = PassportApplication.getInstance(this);
        ServerDiscoverService discoverService = app.getServices().getServerDiscoverService();
        Promise<ServicesVO> p = discoverService.resolveAsync(tc, provider.domain);
        p.onThen((a) -> {
            ServicesVO value = a;
            mProviderServices = value;
            mLoginContext.services = value.services;
            return null;
        });
        return p;
    }

    private void handleClickNext() {

        ServiceProvider provider = this.mProviderSelection;
        if (provider == null) {
            return;
        }
        if (provider.isCustom) {
            provider.domain = mEditDomain.getText().toString();
        }

        TaskContext tc = Promise.createTaskContext(this);
        Promise.execute(tc, Long.class, () -> {
            initNewSession(provider);
            return Long.valueOf(0);
        }).onThen((res) -> {
            jumpToNext();
            return null;
        }).onCatch((res) -> {
            return null;
        });
        Promise.start(tc);
    }

    private void initNewSession(ServiceProvider provider) {

        UserSpaceBO space = new UserSpaceBO();
        space.domain = provider.domain;
        space.email = this.mLoginContext.email;

        IPassportApplication app = PassportApplication.getInstance(this);
        SessionManager sessionManager = app.getServices().getSessions();
        ISession session = sessionManager.open(space);
        session.getRESTClient();
        session.saveAsCurrent();

        mLoginContext.domain = space.domain;
        mLoginContext.email = space.email;
    }


    private void jumpToNext() {
        Intent i = new Intent(this, LoginStep3Activity.class);
        this.startActivity(i);
    }

}
