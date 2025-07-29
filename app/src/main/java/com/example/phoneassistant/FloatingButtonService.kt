package com.example.phoneassistant

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import android.widget.Toast
import android.app.Activity
import android.media.projection.MediaProjectionManager
import android.content.Context

class FloatingButtonService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var floatingButton: View
    private lateinit var params: WindowManager.LayoutParams
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private val client = OkHttpClient()
    
    private var lastX = 0
    private var lastY = 0
    private var isDragging = false
    private val CLICK_THRESHOLD = 10
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createFloatingButton()
    }

    private fun createFloatingButton() {
        floatingButton = LayoutInflater.from(this).inflate(R.layout.floating_button, null)
        
        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 100
        }

        windowManager.addView(floatingButton, params)
        setupTouchListener()
    }

    private fun setupTouchListener() {
        floatingButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    isDragging = false
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY
                    
                    if (Math.abs(deltaX) > CLICK_THRESHOLD || Math.abs(deltaY) > CLICK_THRESHOLD) {
                        isDragging = true
                        params.x = (initialX + deltaX).toInt()
                        params.y = (initialY + deltaY).toInt()
                        windowManager.updateViewLayout(floatingButton, params)
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isDragging) {
                        onFloatingButtonClick()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun onFloatingButtonClick() {
        Toast.makeText(this, "正在获取屏幕内容...", Toast.LENGTH_SHORT).show()
        
        // TODO: 实现截屏功能
        val screenContent = "这里是模拟的屏幕内容"
        
        serviceScope.launch {
            generateReply(screenContent)
        }
    }

    private suspend fun generateReply(screenContent: String) {
        withContext(Dispatchers.IO) {
            try {
                val json = JSONObject().apply {
                    put("prompt", "根据以下内容生成一个自然的回复：$screenContent")
                    put("max_tokens", 150)
                }

                val requestBody = json.toString().toRequestBody("application/json".toMediaType())
                
                // TODO: 替换为您的AI端点URL
                val request = Request.Builder()
                    .url("https://your-ai-endpoint.com/generate")
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val responseJson = JSONObject(responseBody ?: "{}")
                        val generatedReply = responseJson.optString("reply", "生成失败")
                        
                        withContext(Dispatchers.Main) {
                            showReply(generatedReply)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@FloatingButtonService, "请求失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FloatingButtonService, "错误: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showReply(reply: String) {
        // TODO: 实现显示回复的UI
        Toast.makeText(this, "生成的回复: $reply", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        if (::floatingButton.isInitialized) {
            windowManager.removeView(floatingButton)
        }
    }
}