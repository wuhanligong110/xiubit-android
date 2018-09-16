package com.toobei.common.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader




/**
 * Created by 11191 on 2018/6/5.
 */
fun getJson(context: Context, fileName: String): String {
    val stringBuilder = StringBuilder()
    //获得assets资源管理器
    val assetManager = context.assets
    //使用IO流读取json文件内容
    try {
        val bf = BufferedReader(InputStreamReader(
                assetManager.open(fileName), "utf-8"))
        var line: String? = bf.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bf.readLine()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return stringBuilder.toString()
}

class AssetsReadUtil {


}