package com.example.dio_v.killapay.util;

import android.app.ActivityManager;
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

    /**
     * 获取带有指定字段的包名
     *
     * @param context 上下文
     * @param s       指定的字段
     * @return 包名
     */
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

    /**
     * 获取应用信息
     *
     * @param context     上下文
     * @param packageName 指定的包名
     * @return 应用信息
     */
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


    /**
     * 带有指定字段的服务是否开启
     *
     * @param context 上下文
     * @param pkgName 指定的字段
     * @return 是否开启
     */
    public static boolean isServiceRunning(Context context, String pkgName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            String name = runningServiceInfo.process;
            if (name.contains(pkgName)) {
                return true;
            }
        }
        return false;
    }
}
