package com.dev.thorugoh.sampleapp.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.thorugoh.sampleapp.R
import com.dev.thorugoh.sampleapp.broadcastreceiver.LowBatteryBroadcastReceiver
import com.dev.thorugoh.sampleapp.databinding.ActivityMainBinding
import com.dev.thorugoh.sampleapp.services.SyncDataService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val lowBatteryBroadcastReceiver = LowBatteryBroadcastReceiver()
    private val lowBatteryIntentFilter = IntentFilter("android.intent.action.BATTERY_LOW")

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("MainActivity", "Permission granted")
            } else {
                Log.d("MainActivity", "Permission denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        showToast(this)

//        val url = "https://www.google.com"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(intent)

        val myClass = MyClass(context = applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Not so safe since you can pass a wrong id, rely on view binding instead
        val tvHelloWorld = findViewById<TextView>(R.id.tvHelloWorld)
        with(tvHelloWorld) {
            text = "Hello World!"
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }

        supportFragmentManager.beginTransaction().add(
            R.id.flMainContainer, BlankFragment.newInstance(
                name = "Bernardo",
                age = 28,
                isMale = true
            )
        ).commit()


        // it will cause a crash if the activity_main_2.xml is not inflated
//        val tvHelloWorld2 = findViewById<TextView>(R.id.tvHelloWorld2)
//        with(tvHelloWorld2){
//            text = "Hello World 2!"
//            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
//        }
//

        // safer since viewbinding will check if the id is correct and if the view is inflated
        with(binding.tvHelloWorld) {
            this?.text = "Hello Bidning!"
            this?.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }

        fun showGoToMainActivity2Notification() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ) {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
                return
            }

            val intentGoToMainActivity2 = Intent(this, MainActivity2::class.java)
            val pendingIntentGoToMainActivity2 = PendingIntent.getActivity(
                this,
                0,
                intentGoToMainActivity2,
                PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(this.applicationContext, "channel_id")
                .setContentTitle("Notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText("Touch to open MainActivity2")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentIntent(pendingIntentGoToMainActivity2)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "channel_id",
                    "Go to activity 2 channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                manager.createNotificationChannel(channel)
            }

            manager.notify(1, notification.build())
        }

            binding.btnGoToMainActivity2?.setOnClickListener {
                showGoToMainActivity2Notification()
//            startActivity(Intent(ACTION_VIEW, Uri.parse("https://www.google.com")))
//            startActivity(Intent(this, MainActivity2::class.java))
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            unregisterReceiver(lowBatteryBroadcastReceiver)
            Log.d("MainActivity", "onDestroy")
        }

        fun showToast(context: Context) {
            Toast.makeText(context, "Hello World!", Toast.LENGTH_SHORT).show()
        }

    }