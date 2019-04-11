package com.baitu.tth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.baitu.tth.utils.AccessibilityUtil
import com.baitu.tth.utils.PackageUtils

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            AccessibilityUtil.openAccessibility("BaituAccessibilityService", this)
        }
        workBtn.setOnClickListener {
            startWork()
        }
    }

    private fun startWork() {
        if (PackageUtils.isPkgInstalled(this, PackageUtils.APPPAKAGE_TIKTOK)) {
            PackageUtils.launchApk(this, PackageUtils.APPPAKAGE_TIKTOK)
        } else {
            Toast.makeText(this, "没有安装抖音", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
