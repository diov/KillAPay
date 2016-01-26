package com.example.dio_v.killapay.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.text.format.Formatter;

import java.util.List;

/**
 * Description: 用于获取进程信息的工具类
 * <p/>
 * Created by dio_v on 下午2:17.
 */
public class ProcessHelper {

    public static String getProcessInfo(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
            if (processInfo.processName != packageName) {
                continue;
            }

            // 获取进程信息
            Debug.MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(new
                    int[processInfo.pid]);
            // 获取内存的字节数(直接getTotalPss获取的以kb为单位)
            long memorySize = memoryInfo[0].getTotalPss() * 1024;
            String memory = Formatter.formatFileSize(context, memorySize);

            return memory;
        }

        return "应用未运行";
    }
}


