package com.flj.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ec.Bmob.BmobSignIn;
import com.flj.latte.ec.Bmob.BombSignUp;
import com.flj.latte.ec.main.EcBottomDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.net.callback.ISuccess;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.wechat.LatteWeChat;
import com.flj.latte.wechat.callbacks.IWeChatSignInCallback;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by 傅令杰 on 2017/4/22
 */

public class SignInDelegate extends LatteDelegate implements View.OnClickListener {

    private TextInputEditText mName = null;
    private TextInputEditText mPassword = null;
    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    private void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://mock.fulingjie.com/mock/data/user_profile.json")
                    .params("name", mName.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            BmobSignIn.onBombSignIn(getSupportDelegate(),getContext(),mName.getText().toString(),mPassword.getText().toString());
                            BmobUser userlogin=new BmobUser();
                            userlogin.setUsername(mName.getText().toString());
                            userlogin.setPassword(mPassword.getText().toString());
                            userlogin.login(new SaveListener<Object>() {
                                @Override
                                public void done(Object o, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getContext(), "登录成功，返回objectId为："+o, Toast.LENGTH_LONG).show();
                                        SignHandler.onSignIn(response, mISignListener);
                                        getSupportDelegate().startWithPop(new EcBottomDelegate());
                                    } else {
                                        Toast.makeText(getContext(), "login fail:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    })
                    .build()
                    .post();
        }
    }

    private void onClickWeChat() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {
                        Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
                    }
                })
                .signIn();
    }

    private void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
//            mName.setError("错误的邮箱格式");
            isPass = true;
        } else {
            mName.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mName = $(R.id.edit_sign_in_name);
        mPassword = $(R.id.edit_sign_in_password);
        $(R.id.btn_sign_in).setOnClickListener(this);
        $(R.id.tv_link_sign_up).setOnClickListener(this);
        $(R.id.icon_sign_in_wechat).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_sign_in) {
            onClickSignIn();
        } else if (i == R.id.tv_link_sign_up) {
            onClickLink();
        } else if (i == R.id.icon_sign_in_wechat) {
            onClickWeChat();
        }
    }
}
