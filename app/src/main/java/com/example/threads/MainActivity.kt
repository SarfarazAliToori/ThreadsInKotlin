package com.example.threads

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    private val TAG : String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "coCreate : ${Thread.currentThread().name}")
        functionCalls()



    }

    private fun functionCalls() {
        logRunningTask()
        mainThread()
        longRunningTaskWithWorkerThread()
        logRunningTaskUsingCoroutine()

        //Coroutine Suspending functions
        suspendFunctionsInCoroutines()
    }


    private fun logRunningTask() {
        btn_long_running_task.setOnClickListener {
            myLogRunningTask()
        }
    }

    private fun myLogRunningTask() {
        // Hahahahaha it's for fun
        for (i in 1..10000000L) {
            Log.i(TAG,"Long Running Task : $i")
        }
    }

    private fun mainThread() {
        btn_counter.setOnClickListener {
            Log.i(TAG, "mainThreadFunction : ${Thread.currentThread().name}")
            text_view.text = "${text_view.text.toString().toInt() + 1}"

//            var ss : String? = null
//            text_view.text = ss.let { "helllllll" }
        }
    }

    private fun longRunningTaskWithWorkerThread() {
        btn_worder_thread.setOnClickListener {
            // We can use Coroutines instead of thread.
            // this is background thread or worker thread
            thread(start = true) {
                myLogRunningTask()
            }
        }

    }

    private fun logRunningTaskUsingCoroutine() {

        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "Coroutine 1 :${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "Coroutine 2 :${Thread.currentThread().name}")
        }

        CoroutineScope(Dispatchers.Default).launch {
            Log.i(TAG, "Coroutine 3 :${Thread.currentThread().name}")
        }
    }



    private fun suspendFunctionsInCoroutines() {
        // here I want to launch two coroutine for task1 and for task 2
        CoroutineScope(Dispatchers.Main).launch {
            task1()
        }

        CoroutineScope(Dispatchers.Main).launch {
            task2()
        }
    }

    suspend fun task1() {
        Log.i(TAG, "Start of Task 1")
        // yield is default function in coroutine for suspending
        delay(409000)
        Log.i(TAG, "End of Task 1")
    }

    suspend fun task2() {
        Log.i(TAG, "Start of Task 2")
        // yield id a default function in Coroutine for suspending
        yield()
        Log.i(TAG, "End of Task 2")
    }
}