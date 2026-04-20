package com.lkwg.maptracker

import android.app.Application
import android.util.Log
import org.opencv.android.OpenCVLoader

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val initialized = try {
            OpenCVLoader.initLocal()
        } catch (e: Throwable) {
            Log.e(TAG, "OpenCV initLocal 失败", e)
            false
        }

        if (initialized) {
            Log.i(TAG, "OpenCV 初始化成功")
            return
        }

        // 某些打包方式下 initLocal() 可能不可用，兜底直接加载 JNI 库
        try {
            System.loadLibrary("opencv_java4")
            Log.i(TAG, "OpenCV 通过 System.loadLibrary 初始化成功")
        } catch (e: Throwable) {
            Log.e(TAG, "OpenCV 初始化失败，请检查 opencv_sdk 模块和 ABI", e)
        }
    }

    companion object {
        private const val TAG = "LuoKeMapTrackerApp"
    }
}
