package com.example.dio_v.killapay.util;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Description: 辅助类
 * <p/>
 * Created by dio_v on 下午4:17.
 */
public class ForcecloseAccessibilityService extends AccessibilityService {

    public static final int INVOKE_KILL = 1;
    public static int INVOKE_TYPE = 0;
    // 根视窗的NodeInfo
    AccessibilityNodeInfo mRootNodeInfo = null;

    private void reset() {
        INVOKE_TYPE = 0;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        mRootNodeInfo = event.getSource();
        if (null == mRootNodeInfo) {
            return;
        } else {
            if (INVOKE_TYPE == INVOKE_KILL) {
                findAndPerformAction("强行停止");
                if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                    findAndPerformAction("确定");
                    reset();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }


    private void findAndPerformAction(String text) {
        // 查找当前窗口中包含文字的按钮
        if (getRootInActiveWindow() == null)
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }
}