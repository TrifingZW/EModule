package com.trifingzw.emodule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trifingzw.emodule.windowpreferences.WindowPreferencesManager

abstract class EModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val windowPreferencesManager = WindowPreferencesManager(this)
        windowPreferencesManager.applyEdgeToEdgePreference(window)
        userInterface()
        initialize()
    }

    protected abstract fun userInterface()
    protected abstract fun initialize()
}