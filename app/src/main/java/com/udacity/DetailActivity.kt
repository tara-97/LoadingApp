package com.udacity

import android.app.PendingIntent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val extras = intent.extras!!
        val fileName = extras.getString("filename")
        val status = extras.getString("status")
        val url = extras.getString("url")
        findViewById<TextView>(R.id.download_id).text = fileName
        findViewById<TextView>(R.id.download_url).text = url
        findViewById<TextView>(R.id.download_status).text =status
        findViewById<Button>(R.id.button).setOnClickListener{
            finish()
        }

    }

}
