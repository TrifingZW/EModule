package com.trifingzw.emodule.helper

import android.os.Parcelable
import com.google.gson.Gson
import java.io.File
import kotlinx.parcelize.Parcelize

object Constants {
    //OS
    var PACKAGE_NAME: String = ""
    var EXTERNAL_FILE_DIR_PATH: String = ""
    var EXTERNAL_CACHE_DIR_PATH: String = ""

    //File
    val PROJECT_FILE_PATH: String get() = "$EXTERNAL_FILE_DIR_PATH/project.json"
    val WORK_TXT_FILE_PATH: String get() = "$EXTERNAL_FILE_DIR_PATH/WorkPath.txt"
    val BTL_TXT_FILE_PATH: String get() = "$EXTERNAL_FILE_DIR_PATH/BtlPath.txt"

}

fun getBtlPath(dirPath: String) = "$dirPath/assets/stage"

var projects: ArrayList<Project> = arrayListOf()
fun saveProject() {
    val file = File(Constants.PROJECT_FILE_PATH)
    file.writeText(Gson().toJson(projects))
}

data class FileData(
    val name: String,
    val path: String,
    val size: String,
    val time: String
)

@Parcelize
data class Project(
    val name: String,
    val description: String,
    val workPath: String,
) : Parcelable
