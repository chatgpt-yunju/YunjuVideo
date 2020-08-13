package com.flj.latte.ec.Bmob;

import cn.bmob.v3.BmobObject;

public class Person extends BmobObject {
    private String name;
    private String mail;
    private String phone;
    private String password;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String address) {
        this.mail = address;
    }
    public String getphone() {
        return mail;
    }
    public void setPhone(String address) {
        this.mail = address;
    }
    public String getPassword() {
        return mail;
    }
    public void setPassword(String address) {
        this.mail = address;
    }

      /*Person p2 = new Person();
                            p2.setName(mName.getText().toString());
                            p2.setMail(mEmail.getText().toString());
                            p2.setPhone(mPhone.getText().toString());
                            p2.setPassword(mPassword.getText().toString());
                            p2.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
//                                        toast("添加数据成功，返回objectId为："+objectId);
                                        Toast.makeText(getContext(), "添加数据成功，返回objectId为："+s, Toast.LENGTH_LONG).show();
                                    }else{
//                                        toast("创建数据失败：" + e.getMessage());
                                        Toast.makeText(getContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });*/
}
