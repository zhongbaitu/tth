package com.baitu.tth

import android.accessibilityservice.AccessibilityService
import android.app.Instrumentation
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import com.baitu.tth.utils.PackageUtils


/**
 * Created by ZhongMaiNeng on 2019/4/11.
 *
 */
class BaituAccessibilityService : AccessibilityService() {

    val mainHandler = Handler(Looper.getMainLooper())

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
        mainHandler.postDelayed({
            performClick("com.ss.android.ugc.aweme:id/bu7")
        }, 3000)
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
        //点赞
//        targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/c8y")
        //评论：com.ss.android.ugc.aweme:id/bk4
        //列表com.ss.android.ugc.aweme:id/ts
//        targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/ts")
        targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/aad")
        targetNode?.performAction(AccessibilityNodeInfo.ACTION_CLICK)

//        val item  = targetNode!!.getChild(0)
//        item.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//        Log.d("testAccess", "item：$item")

//        for(child in 0..5){
//
//            mainHandler.postDelayed({
//                val item  = targetNode!!.getChild(child)
//                item.performAction(AccessibilityNodeInfo.ACTION_CLICK)
//                Log.d("testAccess", "item：$item")
////                val likeItem = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/c8y")
////                likeItem?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
////                actionKey(KeyEvent.KEYCODE_BACK)
//            }, 2000)
//        }
//                targetNode = findNodeInfosById(nodeInfo, "com.ss.android.ugc.aweme:id/c8y")
//        targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    /**
     * 模拟键盘事件方法
     * @param keyCode
     */
    fun actionKey(keyCode: Int) {
        object : Thread() {
            override fun run() {
                try {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(keyCode)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()
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