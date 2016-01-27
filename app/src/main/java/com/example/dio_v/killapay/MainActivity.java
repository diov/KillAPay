package com.example.dio_v.killapay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dio_v.killapay.model.AppInfo;
import com.example.dio_v.killapay.util.AppHelper;
import com.example.dio_v.killapay.util.ForcecloseAccessibilityService;

public class MainActivity extends AppCompatActivity {

    private static final String APP_NAME = "Alipay";
    private String packageName;
    private boolean isInstall;
    private AppInfo appInfo;
    private TextView appName;
    private ImageView appIcon;
    private TextView processInfo;
    private String memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appName = (TextView) findViewById(R.id.appName);
        processInfo = (TextView) findViewById(R.id.processInfo);
        appIcon = (ImageView) findViewById(R.id.appIcon);

        // 判断是否存在应用
        isInstalled();

        // 判断应用是否运行


        // 根据应用是否存在，是否运行初始化View
        initView();
    }

    private void initView() {
        appInfo = AppHelper.getAppInfo(this, packageName);
        appName.setText(appInfo.getName());
        appIcon.setImageDrawable(appInfo.getIcon());
        if (isInstall) {
            processInfo.setText(memory);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInstall) {
                    // 根据包名跳转系统自带的应用程序信息界面,并自动关闭
                    jumpDetailInfo(packageName);
                } else {
                    Snackbar.make(view, "App not installed", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    private void jumpDetailInfo(String packageName) {
        ForcecloseAccessibilityService.INVOKE_TYPE = ForcecloseAccessibilityService.INVOKE_KILL;
        Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.setData(Uri.parse("package:" + packageName));
        startActivity(i);
    }


    private void isInstalled() {
        packageName = AppHelper.getPackageName(this, APP_NAME);
        if (TextUtils.isEmpty(packageName)) {
            isInstall = false;
        } else {
            isInstall = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
