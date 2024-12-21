package com.dev.thorugoh.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AppProcesses {
    fun startMusic(){
        Log.d("MainActivity2", "Approcesses: startMusic")
    }

    fun stopMusic(){
        Log.d("MainActivity2", "Approcesses: stopMusic")
    }

    fun deleteTemporaryCacheFiles(){
        Log.d("MainActivity2", "Approcesses: deleteTemporaryCacheFiles")
    }

    fun createTemporaryCacheFiles(){
        Log.d("MainActivity2", "Approcesses: createTemporaryCacheFiles")
    }

    fun startLongRunningTask(){
        Log.d("MainActivity2", "Approcesses: startLongRunningTask")
    }

    fun stopLongRunningTasks(){
        Log.d("MainActivity2", "Approcesses: stopLongRunningTasks")
    }

    fun setupLayout(){
        Log.d("MainActivity2", "Approcesses: setupLayout")
    }
}

class MainActivity2 : AppCompatActivity() {

    private val myAppProcesses by lazy {
        AppProcesses()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity2", "onCreate")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myAppProcesses.createTemporaryCacheFiles()
        myAppProcesses.setupLayout()
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity2", "onStart")
        myAppProcesses.startLongRunningTask()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity2", "onResume")
        myAppProcesses.startMusic()
        //myAppProcesses.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity2", "onPause")
        myAppProcesses.stopMusic()
        //myAppProcesses.stopAnimation()
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity2", "onStop")
        myAppProcesses.stopLongRunningTasks()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity2", "onRestart")
//        myAppProcesses.restartLongRunningTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity2", "onDestroy")
        myAppProcesses.deleteTemporaryCacheFiles()
    }

}