package com.jaeger.ninegridimgdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * @author YanLu
 * @since 16/7/4
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                 onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
