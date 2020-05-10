package com.zyyoona7.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zyyoona7.lock.GestureLockLayout;
import com.zyyoona7.lock.ILockView;
import com.zyyoona7.lock.JDLockView;
import com.zyyoona7.lock.LockViewFactory;
import com.zyyoona7.lock.QQLockView;


public class LauncherActivity extends AppCompatActivity {
    // TODO: 2017/7/10 展示更多样式

    private Button mSettingBtn;
    private Button mQQBtn;
    private Button mJDBtn;
    private GestureLockLayout mGestureLockLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initViews();
        initEvents();
    }

    private void initViews(){
        mSettingBtn = (Button) findViewById(R.id.btn_setting);
        mQQBtn= (Button) findViewById(R.id.btn_qq);
        mJDBtn= (Button) findViewById(R.id.btn_jd);
        mGestureLockLayout= (GestureLockLayout) findViewById(R.id.l_gesture_lock_view);
        mGestureLockLayout.setDotCount(3);
        mGestureLockLayout.setMode(GestureLockLayout.VERIFY_MODE);
        mGestureLockLayout.setTryTimes(100);
        mGestureLockLayout.setAnswer(0,1,2);

    }

    private void initEvents(){
        mSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(LockSettingActivity.class);
            }
        });

        mQQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestureLockLayout.setLockView(new LockViewFactory() {
                    @Override
                    public ILockView newLockView() {
                        return new QQLockView(LauncherActivity.this);
                    }
                });
                mGestureLockLayout.setPathWidth(2);
                mGestureLockLayout.setTouchedPathColor(Color.parseColor("#01A0E5"));
                mGestureLockLayout.setMatchedPathColor(Color.parseColor("#01A0E5"));
                mGestureLockLayout.setUnmatchedPathColor(Color.parseColor("#F7564A"));
            }
        });

        mJDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestureLockLayout.setLockView(new LockViewFactory() {
                    @Override
                    public ILockView newLockView() {
                        return new JDLockView(LauncherActivity.this);
                    }
                });
                mGestureLockLayout.setPathWidth(1);
                mGestureLockLayout.setTouchedPathColor(Color.BLUE);
                mGestureLockLayout.setMatchedPathColor(Color.BLUE);
                mGestureLockLayout.setUnmatchedPathColor(Color.RED);
            }
        });

        mGestureLockLayout.setOnLockVerifyListener(new GestureLockLayout.OnLockVerifyListener() {
            @Override
            public void onGestureSelected(int id) {

            }

            @Override
            public void onGestureFinished(boolean isMatched) {
                if (isMatched) {
                    Toast.makeText(LauncherActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LauncherActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGestureTryTimesBoundary() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 从持久化数据中 取出答案
        SharedPreferences shp = getSharedPreferences("myApp", Context.MODE_PRIVATE);
        MyApplication.getInstance().answer = shp.getString("ANSWER", "");

        if (!TextUtils.isEmpty(MyApplication.getInstance().answer)&& !MyApplication.getInstance().isUnlock) {
            goTo(LockActivity.class);
        }
    }

    public void goTo(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
