package com.yibao.croutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_main)
        loadData()

    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GankService::class.java)
        btn.setOnClickListener {
            pb.visibility = View.VISIBLE
            launch {
                val result = withContext(Dispatchers.IO) {
                retrofit.getListRepos(edit.text.toString()).execute()
            }
                if (result.isSuccessful) {
                    pb.visibility = View.GONE
                    Log.d("lsp", result.body().toString())
                    result.body()?.forEach {
                        tv_result.append(it.fullName)
                    }
                }
            }
        }
    }
}
