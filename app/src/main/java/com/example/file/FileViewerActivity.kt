package com.example.file

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class FileViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)

        val contentView: TextView = findViewById(R.id.contentView)
        val filePath = intent.getStringExtra("filePath")
        val file = File(filePath)

        if (file.exists()) {
            try {
                val fis = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fis))
                val content = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    content.append(line).append("\n")
                }
                contentView.text = content.toString()
                reader.close()
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}