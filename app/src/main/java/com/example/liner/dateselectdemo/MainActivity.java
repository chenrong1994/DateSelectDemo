package com.example.liner.dateselectdemo;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private WheelMain wheelMainDate;

    private String beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text11);

        final Button button = (Button) findViewById(R.id.button);

        final java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
                Display defaultDisplay = manager.getDefaultDisplay();
                DisplayMetrics outMetrics = new DisplayMetrics();
                defaultDisplay.getMetrics(outMetrics);
                int width = outMetrics.widthPixels;
                View menuView = LayoutInflater.from(MainActivity.this).inflate(R.layout.show_popup_window,null);
                final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
                        ActionBar.LayoutParams.WRAP_CONTENT);
                ScreenInfo screenInfoDate = new ScreenInfo(MainActivity.this);
                wheelMainDate = new WheelMain(menuView, true);
                wheelMainDate.screenheight = screenInfoDate.getHeight();
                String time = DateUtils.currentMonth().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
                    try {
                        calendar.setTime(new Date(time));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                wheelMainDate.initDateTimePicker(year, month, day, hours,minute,second);
                final String currentTime = wheelMainDate.getTime().toString();
                mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.showAtLocation(button, Gravity.CENTER, 0, 0);
                mPopupWindow.setOnDismissListener(new poponDismissListener());
                backgroundAlpha(0.6f);
                TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
                TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
                TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
                tv_pop_title.setText("选择时间");
                tv_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mPopupWindow.dismiss();
                        backgroundAlpha(1f);
                    }
                });
                tv_ensure.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        beginTime = wheelMainDate.getTime().toString();
                        try {
                            Date begin = dateFormat.parse(currentTime);
                            Date end = dateFormat.parse(beginTime);
                            textView.setText(beginTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mPopupWindow.dismiss();
                        backgroundAlpha(1f);
                    }
                });
            }
        });
    }
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
