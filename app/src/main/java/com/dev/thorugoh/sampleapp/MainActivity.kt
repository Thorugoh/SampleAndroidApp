package com.dev.thorugoh.sampleapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.thorugoh.sampleapp.broadcastreceiver.LowBatteryBroadcastReceiver
import com.dev.thorugoh.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val lowBatteryBroadcastReceiver = LowBatteryBroadcastReceiver()
    private val lowBatteryIntentFilter = IntentFilter("android.intent.action.BATTERY_LOW")

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

        binding.btnGoToMainActivity2?.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        registerReceiver(lowBatteryBroadcastReceiver, lowBatteryIntentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(lowBatteryBroadcastReceiver)
        Log.d("MainActivity", "onDestroy")
    }

    fun showToast(context:Context) {
        Toast.makeText(context, "Hello World!", Toast.LENGTH_SHORT).show()
    }

}