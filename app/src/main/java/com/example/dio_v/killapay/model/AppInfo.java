package com.example.dio_v.killapay.model;

import android.graphics.drawable.Drawable;

/**
 * Description: 关于App信息的Bean类
 * <p/>
 * Created by dio_v on 下午1:44.
 */
public class AppInfo {
    // 应用名
    private String name;
    // 应用包名
    private String packageName;
    // 应用图标
    private Drawable icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
