package com.mashup.lemonsatang.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

// 현재 프로젝트의 해시키 반환
fun getHashKey(context: Context): String? {
    val tag = "KeyHash"
    var keyHash = ""
    try {
        val info = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_SIGNATURES
        )
        val root = info.signatures

        for (signature in root) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            keyHash = String(Base64.encode(md.digest(), 0))
            Log.d(tag, keyHash)
        }
    } catch (e: Exception) {
        Log.e("name not found", e.toString())
    }

    return keyHash
}