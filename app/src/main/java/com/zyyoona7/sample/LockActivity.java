package com.zyyoona7.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.yokeyword.fragmentation.SupportActivity;

public class LockActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        loadRootFragment(R.id.fl_fragment_container,LockFragment.newInstance());
    }


}
