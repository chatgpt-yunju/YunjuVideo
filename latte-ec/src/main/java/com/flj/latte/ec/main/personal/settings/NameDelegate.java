package com.flj.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.delegates.LatteDelegate;

/**
 * Created by 傅令杰
 */

public class NameDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
         final Button btn_name_submit=$(R.id.btn_name_submit);
        btn_name_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"后端数据库维护中，暂不支持修改",Toast.LENGTH_LONG).show();
            }
        });

    }
}
