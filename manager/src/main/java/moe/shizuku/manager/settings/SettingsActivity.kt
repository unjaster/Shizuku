package moe.shizuku.manager.settings

import android.os.Bundle
import android.view.MenuItem
import moe.shizuku.manager.R
import moe.shizuku.manager.ShizukuManagerSettings
import moe.shizuku.manager.app.AppBarFragmentActivity
import moe.shizuku.manager.starter.ServerLauncher

class SettingsActivity : AppBarFragmentActivity() {

    private var isKeepSuContext = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment())
                    .commit()
            isKeepSuContext = ShizukuManagerSettings.isKeepSuContext()
        } else {
            isKeepSuContext = savedInstanceState.getBoolean("keep_su_context", true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("keep_su_context", isKeepSuContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ShizukuManagerSettings.isKeepSuContext() != isKeepSuContext) {
            ServerLauncher.writeFiles(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}