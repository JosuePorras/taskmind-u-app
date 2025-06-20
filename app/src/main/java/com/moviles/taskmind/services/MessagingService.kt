package com.moviles.taskmind.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moviles.taskmind.R
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.network.FcmTokenRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nuevo token generado: $token")

        val prefs = getSharedPreferences("taskmind_prefs", MODE_PRIVATE)
        prefs.edit().putString("pending_fcm_token", token).apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "De: ${remoteMessage.from}")
        Log.d("FCM", "Payload notification: ${remoteMessage.notification}")
        Log.d("FCM", "Payload data: ${remoteMessage.data}")

        val title = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: "Notificación"
        val body = remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""

        if (title.isNotEmpty() && body.isNotEmpty()) {
            createNotificationChannel()
            showNotification(title, body)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "event_reminder_channel"
            val name = "Recordatorio de eventos"
            val descriptionText = "Notificaciones de evaluaciones próximas"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.w("FCM", "No se puede mostrar notificación: permiso denegado.")
                return
            }
        }

        val builder = NotificationCompat.Builder(this, "event_reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(1001, builder.build())
        }
    }


    companion object {
        fun sendFcmTokenToBackend(context: Context, userId: String, token: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.fcmApi.registerFcmToken(
                        FcmTokenRequest(userId = userId, fcmToken = token)
                    )
                    if (response.isSuccessful) {
                        Log.d("FCM", "Token registrado en el backend exitosamente.")
                        // Limpiar token temporal si es necesario
                        val prefs = context.getSharedPreferences("taskmind_prefs", Context.MODE_PRIVATE)
                        prefs.edit().remove("pending_fcm_token").apply()
                    } else {
                        Log.e("FCM", "Error al registrar token en backend: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("FCM", "Excepción al enviar token", e)
                }
            }
        }
    }
}