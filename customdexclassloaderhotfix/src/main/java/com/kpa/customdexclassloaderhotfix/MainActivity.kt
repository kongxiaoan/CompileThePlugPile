package com.kpa.customdexclassloaderhotfix

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.kpa.customdexclassloaderhotfix.exceptions.IAmException
import com.kpa.customdexclassloaderhotfix.interfaces.IAMInterface
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.name
    }

    private val iAmInterface: IAMInterface by lazy {
        IAmException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainButton.setOnClickListener {

            val file =
                File(Environment.getExternalStorageDirectory().path + File.separator + "something_hotfix.jar")
            if (!file.exists()) {
                Log.e(TAG, "hotfix.jar not exists")
                Toast.makeText(this, iAmInterface.doSomething(), Toast.LENGTH_SHORT).show()
            } else {
                val dexClassLoader = DexClassLoader(
                    file.absolutePath,
                    externalCacheDir?.absolutePath,
                    null,
                    classLoader
                )
                try {
                    val clazz =
                        dexClassLoader.loadClass("com.kpa.customdexclassloaderhotfix.IAmHotFix")
                    val iamInterface = clazz.newInstance() as IAMInterface
                    Toast.makeText(this, iamInterface.doSomething(), Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
