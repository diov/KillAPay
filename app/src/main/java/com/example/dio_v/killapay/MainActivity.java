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
    private boolean isRunning;
    private AppInfo appInfo;
    private TextView appName;
    private ImageView appIcon;
    private TextView runningInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appName = (TextView) findViewById(R.id.appName);
        runningInfo = (TextView) findViewById(R.id.runningInfo);
        appIcon = (ImageView) findViewById(R.id.appIcon);

        // 判断是否存在应用
        isInstalled();

        // 根据应用是否存在，是否运行初始化View
        initView();
    }

    private void initView() {
        appInfo = AppHelper.getAppInfo(this, packageName);
        appName.setText(appInfo.getName());
        appIcon.setImageDrawable(appInfo.getIcon());
        if (isRunning) {
            runningInfo.setText("后台正在运行");
        }

        //FAB的点击事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInstall) {
                    if (isRunning) {
                        if (!AppHelper.isServiceRunning(MainActivity.this, getPackageName())) {
                            Snackbar.make(view, "服务未开启,请打开", Snackbar.LENGTH_SHORT).setAction("打开",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            openAccessibleSetting();
                                        }
                                    }).show();
                        } else {
                            // 根据包名跳转系统自带的应用程序信息界面,并自动关闭
                            jumpDetailInfo(packageName);
                        }
                    } else {
                        Snackbar.make(view, "后台未运行,无需关闭", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(view, "应用未安装", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 跳转到应用详情页面
     *
     * @param packageName 指定应用的包名
     */
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
            isRunning = AppHelper.isServiceRunning(this, APP_NAME);
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
            openAccessibleSetting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 开启辅助功能设置界面
     */
    private void openAccessibleSetting() {
        Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(accessibleIntent);
    }
}
