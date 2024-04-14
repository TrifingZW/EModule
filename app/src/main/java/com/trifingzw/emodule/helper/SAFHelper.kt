package com.trifingzw.emodule.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract


var root: String = Environment.getExternalStorageDirectory().path

fun createFile(name: String, type: String? = null): Intent {
    val trueType = type ?: "*/*"
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.setType(trueType)
    intent.putExtra(Intent.EXTRA_TITLE, name)
    // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
    return intent
}

fun openDirectory(extraUri: Uri? = null): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    extraUri?.let { intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, extraUri) }
    return intent
}

fun openFile(extraUri: Uri?, type: String? = null): Intent {
    val trueType = type ?: "*/*"
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.setType(trueType)
    extraUri?.let { intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, extraUri) }
    return intent
}

fun treeToPath(folderUri: Uri): String {
    var folderPath: String? = null
    if (folderUri.path != null) folderPath = folderUri.path!!.replace("/tree/primary:", "")
    return "$root/$folderPath"
}

fun documentToPath(folderUri: Uri): String {
    var folderPath = folderUri.path
    if (folderPath != null) folderPath = folderPath.substring(folderPath.lastIndexOf("document/primary:")).replace("document/primary:", "")
    return "$root/$folderPath"
}

fun pathToTree(path: String?): Uri {
    var trimPath = ""
    if (path != null) trimPath = path.replaceFirst("/storage/emulated/0/".toRegex(), "")
    val uriString = "content://com.android.externalstorage.documents/tree/primary%3A" + trimPath.replace("/", "%2F")
    return Uri.parse(uriString)
}

fun pathToDocument(path: String?): Uri {
    var trimPath = ""
    if (path != null) trimPath = path.replaceFirst("/storage/emulated/0/".toRegex(), "")
    val uriString = "content://com.android.externalstorage.documents/document/primary%3A" + trimPath.replace("/", "%2F")
    return Uri.parse(uriString)
}

fun savePermission(activity: Activity, uri: Uri?) {
    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    activity.contentResolver.takePersistableUriPermission(uri!!, takeFlags)
}

fun isGrant(context: Context, path: String?): Boolean {
    for (uriPermission in context.contentResolver.persistedUriPermissions) if (uriPermission.isReadPermission && uriPermission.uri.toString() == pathToTree(
            path
        ).toString()
    ) return true
    return false
}