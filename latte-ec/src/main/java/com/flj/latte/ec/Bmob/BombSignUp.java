package com.flj.latte.ec.Bmob;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.app.AccountManager;
import com.flj.latte.ec.sign.ISignListener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class BombSignUp {

    public static void onBombSignUp(Context context, String name, String mail, String phone, String password) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(name);
        bmobUser.setEmail(mail);
        bmobUser.setMobilePhoneNumber(phone);
        bmobUser.setPassword(password);
        bmobUser.signUp(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "添加数据成功，返回objectId为："+o, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "register fail:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
