package com.baitu.tth.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.text.TextUtils


object PackageUtils {

    val APPPAKAGE_TIKTOK = "com.ss.android.ugc.aweme"

    /**
     * 要求调用方提供 PackageManager 是为了避免在循环中不断调用这个方法，导致频繁 getPackageManager() 而引发潜在的
     * PackageManager died 或者调用 PackageManager 接口时抛出 NameNotFoundException | NotFoundException 这些导致功能失效的异常。
     * 注：虽然通过查看官方源代码可以看到 getPackageManager() 里面是有缓存的，但不排除厂商改动而引发的问题，所以做防御性编程。
     */
    private fun getApplicationInfo(pm: PackageManager?, pkgName: String): ApplicationInfo? {
        if (TextUtils.isEmpty(pkgName) || pm == null) {
            return null
        }
        try {
            val pkgInfo = pm.getPackageInfo(pkgName, 0)
            if (pkgInfo != null) {
                return pkgInfo.applicationInfo
            }
        } catch (ignore: Exception) {
        }

        return null
    }

    @JvmStatic
    fun isHasPackage(c: Context?, packageName: String?): Boolean {
        if (null == c || null == packageName) {
            return false
        }

        var bHas = true
        try {
            c.packageManager.getPackageInfo(packageName, PackageManager.GET_GIDS)
        } catch (/* NameNotFoundException */e: Exception) {
            // 抛出找不到的异常，说明该程序已经被卸载
            bHas = false
        }

        return bHas
    }

    @JvmStatic
    fun isPkgInstalled(ctx: Context, pkgName: String): Boolean {
        return getApplicationInfo(ctx.packageManager, pkgName) != null
    }

    /**
     * 獲取應用安裝時間
     */
    fun getInstallTime(context: Context): Long {
        try {
            val packageManager = context.applicationContext.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.firstInstallTime
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0L
    }


    fun launchApk(context: Context, pkg: String) {
        val packageManager = context.getPackageManager()
        val it = packageManager.getLaunchIntentForPackage (pkg)
        if (context !is Activity) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(it)
    }
}
