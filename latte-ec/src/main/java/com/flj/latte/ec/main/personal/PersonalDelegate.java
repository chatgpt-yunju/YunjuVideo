package com.flj.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.app.AccountManager;
import com.flj.latte.app.IUserChecker;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.launcher.LauncherDelegate;
import com.flj.latte.ec.main.personal.address.AddressDelegate;
import com.flj.latte.ec.main.personal.list.ListAdapter;
import com.flj.latte.ec.main.personal.list.ListBean;
import com.flj.latte.ec.main.personal.list.ListItemType;
import com.flj.latte.ec.main.personal.order.OrderListDelegate;
import com.flj.latte.ec.main.personal.profile.UserProfileDelegate;
import com.flj.latte.ec.main.personal.settings.SettingsDelegate;
import com.flj.latte.ec.sign.SignInDelegate;
import com.flj.latte.ui.launcher.OnLauncherFinishTag;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by 傅令杰
 */

public class PersonalDelegate extends BottomItemDelegate {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    private void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    private void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final TextView musrname=$(R.id.tv_usr_name);
        if (BmobUser.isLogin()){
            BmobUser bmobuser = BmobUser.getCurrentUser(BmobUser.class);
            String bmobusername = (String) BmobUser.getObjectByKey("username");
            musrname.setText(bmobusername);
        }else{
            musrname.setText("游客账号");
        }

        final RecyclerView rvSettings = $(R.id.rv_personal_setting);
        $(R.id.tv_all_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAllOrder();
            }
        });
        $(R.id.img_user_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountManager.checkAccount(new IUserChecker() {
                    @Override
                    public void onSignIn() {
                        onClickAvatar();
                    }
                    @Override
                    public void onNotSignIn() {
                        getParentDelegate().getSupportDelegate().start(new SignInDelegate());
//                        getSupportDelegate().startWithPop(new SignInDelegate());
                    }
                });
            }
        });

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();

//        final ListBean loginout = new ListBean.Builder()
//                .setItemType(ListItemType.ITEM_NORMAL)
//                .setId(3)
////                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////                    @Override
////                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                        AccountManager.setSignState(false);
////                    }
////                })
//                .setDelegate(new SignInDelegate())
//                .setText("退出登录")
//                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);
//        data.add(loginout);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        rvSettings.setAdapter(adapter);
        rvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
