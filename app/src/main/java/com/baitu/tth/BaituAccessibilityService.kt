package com.baitu.tth

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.baitu.tth.utils.PackageUtils


/**
 * Created by ZhongMaiNeng on 2019/4/11.
 *
 */
class BaituAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("testAccess", "onAccessibilityEvent")
        Log.d("testAccess", "event:"+event?.packageName)
        Log.d("testAccess", "event:"+event?.action)
    }

    /**
     * 当系统连接上你的服务时被调用
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("testAccess", "onServiceConnected")
        //com.ss.android.ugc.aweme:id/bu7
        startWork()
        Handler(Looper.getMainLooper()).postDelayed({

        performClick("com.ss.android.ugc.aweme:id/bu7")
        }, 5000)
    }

    private fun startWork() {
        if (PackageUtils.isPkgInstalled(this, PackageUtils.APPPAKAGE_TIKTOK)) {
            PackageUtils.launchApk(this, PackageUtils.APPPAKAGE_TIKTOK)
        } else {
            Toast.makeText(this, "没有安装抖音", Toast.LENGTH_SHORT).show()
        }
    }

    //借箭（点击）
    private fun performClick(resourceId: String) {
        val nodeInfo = this.rootInActiveWindow
        var targetNode: AccessibilityNodeInfo? = null
//        targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/bu7")
        targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/c8y")
        Log.d("testAccess", "targetNode:${targetNode!!.text}")
        if (targetNode!!.isClickable) {
        }
        targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    //调用兵力（通过id查找）
    fun findNodeInfosById(nodeInfo: AccessibilityNodeInfo, resId: String): AccessibilityNodeInfo? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val list = nodeInfo.findAccessibilityNodeInfosByViewId(resId)
            if (list != null && !list.isEmpty()) {
                return list[0]
            }
        }
        return null
    }

    //调用船只（通过文本查找）
    fun findNodeInfosByText(nodeInfo: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
        val list = nodeInfo.findAccessibilityNodeInfosByText(text)
        return if (list == null || list.isEmpty()) {
            null
        } else list[0]
    }
}