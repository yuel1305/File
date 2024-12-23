package com.example.file

import android.Manifest

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileExplorerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter
    private var fileList: MutableList<File> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_explorer)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fileAdapter = FileAdapter(this, fileList)
        recyclerView.adapter = fileAdapter

        // Kiểm tra quyền truy cập bộ nhớ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        // Lấy đường dẫn thư mục từ Intent (nếu có)
        val directoryPath = intent.getStringExtra("directoryPath")
        val rootDirectory = if (directoryPath != null) {
            File(directoryPath)
        } else {
            File(Environment.getExternalStorageDirectory().absolutePath)
        }
        loadFiles(rootDirectory)
    }

    private fun loadFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            fileList.clear()
            fileList.addAll(files)
            fileAdapter.notifyDataSetChanged()
        }
    }
}