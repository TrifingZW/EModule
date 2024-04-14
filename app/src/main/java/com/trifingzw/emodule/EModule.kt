package com.trifingzw.emodule

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trifingzw.emodule.helper.Constants.EXTERNAL_CACHE_DIR_PATH
import com.trifingzw.emodule.helper.Constants.EXTERNAL_FILE_DIR_PATH
import com.trifingzw.emodule.helper.Constants.PACKAGE_NAME
import com.trifingzw.emodule.helper.Constants.PROJECT_FILE_PATH
import com.trifingzw.emodule.helper.Project
import com.trifingzw.emodule.helper.projects
import java.io.File

class EModule : Application() {
    override fun onCreate() {
        super.onCreate()
        PACKAGE_NAME = packageName
        EXTERNAL_FILE_DIR_PATH = getExternalFilesDir(null)!!.path
        EXTERNAL_CACHE_DIR_PATH = externalCacheDir!!.path

        val listType = object : TypeToken<ArrayList<Project>>() {}.type
        val file = File(PROJECT_FILE_PATH)
        if (file.exists())
            projects = Gson().fromJson(File(PROJECT_FILE_PATH).readText(), listType)

    }
}