package com.kpa.compiletheplugpile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.name
    }

    val view: View? = null
    lateinit var view1: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startSecond.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        /**
         * 验证是那个类加载器加载的
         * PathClassLoader
         *  dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.kpa.compiletheplugpile-
         *  a5KytpSrVE-Z9PNeO60oOA==/base.apk"],nativeLibraryDirectories=
         *  [/data/app/com.kpa.compiletheplugpile-a5KytpSrVE-Z9PNeO60oOA==/lib/arm64, /system/lib64]]]
         */
        val classLoader = MainActivity::class.java.classLoader
        Log.e(TAG, classLoader.toString())

        /**
         * DexClassLoader 可以从SD卡加载包含class.dex 的.jar和.apk 文件
         */

        GlobalScope.launch(Dispatchers.Main) {
            println("ThreadName--1-->${Thread.currentThread().name}")
            withContext(Dispatchers.IO){
                println("ThreadName--2->${Thread.currentThread().name}")
            }
            println("ThreadName--3->${Thread.currentThread().name}")
        }
    }
}
