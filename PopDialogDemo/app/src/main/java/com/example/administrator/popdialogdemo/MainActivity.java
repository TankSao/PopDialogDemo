package com.example.administrator.popdialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int month_price = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop("谭桂涛","20131814224");
            }
        });
    }
    private void showPop(String childName, String cardNum) {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View view = View.inflate(this, R.layout.pop_buy_service, null);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.setCancelable(true);
        bottomDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        view.findViewById(R.id.service_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.child_name)).setText(childName);
        ((TextView)view.findViewById(R.id.card_num)).setText(cardNum);
        final TextView priceTv  = view.findViewById(R.id.service_num);
        final LinearLayout llOther  = view.findViewById(R.id.ll_other);
        final EditText etOther  = view.findViewById(R.id.et_other);
        view.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        ArrayList<String> mData1 = new ArrayList<>();
        mData1.add("1个月");
        mData1.add("3个月");
        mData1.add("12个月");
        mData1.add("其他");
        ChooseFwLayout rbLayout = view.findViewById(R.id.choose_money);
        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    priceTv.setText("0");
                }else {
                    int num = Integer.parseInt(editable.toString());
                    priceTv.setText(num * month_price + "");
                }
            }
        };
        rbLayout.setMoneyData(mData1);
        //设置默认选中项
        rbLayout.setDefaultPositon(0);
        //金额选择监听
        rbLayout.setOnChoseMoneyListener(new ChooseFwLayout.onChoseMoneyListener() {
            @Override
            public void chooseMoney(int position, boolean isCheck, String moneyNum) {
                if (isCheck) {
                    if(position>2){
                        priceTv.setText("0");
                        llOther.setVisibility(View.VISIBLE);
                        etOther.addTextChangedListener(watcher);
                    }else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        boolean isOpen=imm.isActive();
                        if(isOpen){
                            imm.hideSoftInputFromWindow(etOther.getWindowToken(), 0);
                        }
                        etOther.setText("");
                        llOther.setVisibility(View.GONE);
                        etOther.removeTextChangedListener(watcher);
                        //单月服务价格
                        String numStr = moneyNum.replace("个月","");
                        int num = Integer.parseInt(numStr);
                        priceTv.setText(num*month_price+"");
                    }
                } else {

                }
            }
        });
        bottomDialog.show();
    }
}
