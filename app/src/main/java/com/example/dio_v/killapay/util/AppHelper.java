package com.example.dio_v.killapay.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.dio_v.killapay.R;
import com.example.dio_v.killapay.model.AppInfo;

import java.util.List;

/**
 * Description: 用于获取App信息的工具类
 * <p/>
 * Created by dio_v on 下午1:24.
 */
public class AppHelper {

    public static String getPackageName(Context context, CharSequence s) {

        PackageManager packageManager = context.getPackageManager();

        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);

        for (ApplicationInfo application : installedApplications) {
            String packageName = application.packageName;
            if (packageName.contains(s))
                return packageName;
        }

        return "";
    }

    public static AppInfo getAppInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        AppInfo appInfo = new AppInfo();

        try {
            // 获取应用图标
            appInfo.setIcon(packageManager.getApplicationIcon(packageName));
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            // 获取应用名
            appInfo.setName(applicationInfo.loadLabel(packageManager).toString());
            // 获取应用包名
            appInfo.setPackageName(applicationInfo.className);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appInfo.setName("应用未安装");
            appInfo.setPackageName("");
            appInfo.setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher));
        }
        return appInfo;
    }

}
