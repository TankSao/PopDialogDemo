package com.example.administrator.popdialogdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用: 选择金额的layout
 *
 * @author GT
 * @date 2018/04/18
 * @escribe R币充值
 */

public class ChooseFwLayout extends GridView {

    private List moneyList = new ArrayList();   //数据源

    private LayoutInflater mInflater;

    private MyAdapter adapter;   //适配器

    int defaultChoose = 0;     //默认选中项

    public ChooseFwLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setData();
    }

    public void setData() {
        mInflater = LayoutInflater.from(getContext());
        //配置适配器
        adapter = new MyAdapter();
        setAdapter(adapter);
    }

    /**
     * 设置默认选择项目，
     * @param defaultChoose
     */
    public void setDefaultPositon(int defaultChoose) {
        this.defaultChoose = defaultChoose;
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置数据源
     * @param moneyData
     */
    public void setMoneyData(List moneyData){
        this.moneyList = moneyData;
    }

    class MyAdapter extends BaseAdapter {


        private CheckBox checkBox;


        @Override
        public int getCount() {
            return moneyList.size();
        }

        @Override
        public Object getItem(int position) {
            return moneyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                holder = new MyViewHolder();
                convertView = mInflater.inflate(R.layout.item_money_pay_fw, parent, false);
                holder.moneyPayCb = convertView.findViewById(R.id.money_pay_cb);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            holder.moneyPayCb.setText(getItem(position).toString());

            holder.moneyPayCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //设置选中文字颜色
                        buttonView.setTextColor(Color.RED);
                        Drawable drawables2 = getResources().getDrawable(R.drawable.yjbiankuang_red);
                        buttonView.setBackgroundDrawable(drawables2);
                        //取消上一个选择
                        if (checkBox != null) {
                            checkBox.setChecked(false);
                        }
                        checkBox = (CheckBox) buttonView;
                    } else {
                        checkBox = null;
                        //设置不选中文字颜色
                        Drawable drawables = getResources().getDrawable(R.drawable.yjbiankuang_black);
                        buttonView.setBackgroundDrawable(drawables);
                        buttonView.setTextColor(Color.GRAY);
                    }
                    //回调
                    listener.chooseMoney(position, isChecked,  getItem(position).toString());
                }
            });


            if (position == defaultChoose) {
                defaultChoose = -1;
                holder.moneyPayCb.setChecked(true);
                checkBox = holder.moneyPayCb;
            }

            return convertView;
        }


        private class MyViewHolder {
            private CheckBox moneyPayCb;
        }
    }


    /**
     * 解决嵌套显示不完
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private onChoseMoneyListener listener;

    public void setOnChoseMoneyListener(onChoseMoneyListener listener) {
        this.listener = listener;
    }

    public interface onChoseMoneyListener {
        /**
         * 选择金额返回
         *
         * @param position gridView的位置
         * @param isCheck  是否选中
         * @param moneyNum 钱数
         */
        void chooseMoney(int position, boolean isCheck, String moneyNum);
    }
}