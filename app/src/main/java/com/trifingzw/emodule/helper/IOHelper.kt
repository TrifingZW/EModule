package com.trifingzw.emodule.helper

import java.io.File

fun getFileData(path: String): FileData {
    val file = File(path)
    return FileData(file.name, file.path, formatSize(file.length()), formatTime(file.lastModified()))
}

fun getFileData(file: File): FileData = FileData(file.name, file.path, formatSize(file.length()), formatTime(file.lastModified()))
