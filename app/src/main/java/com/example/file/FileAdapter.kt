package com.example.file

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(
    private val context: Context,
    private val fileList: List<File>
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.nameTextView.text = file.name

        // Kiểm tra xem đây có phải là thư mục không
        if (file.isDirectory) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, FileExplorerActivity::class.java)
                intent.putExtra("directoryPath", file.absolutePath)
                context.startActivity(intent)
            }
        } else {
            // Nếu là file, mở file
            holder.itemView.setOnClickListener {
                openFile(file)
            }
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    private fun openFile(file: File) {
        // Thực hiện xử lý mở file (ví dụ: nếu là file văn bản .txt)
        if (file.name.endsWith(".txt")) {
            val intent = Intent(context, FileViewerActivity::class.java)
            intent.putExtra("filePath", file.absolutePath)
            context.startActivity(intent)
        }
    }

    class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.fileName)
    }
}