package com.example.a100contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.InputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_PERMISSION_CODE
        )

        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, "100-contacts.csv")
        val db = DBHelper(this, null)
        val inputStream: InputStream = file.inputStream()
        val lines = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine { lines.add(it) }
        lines.removeAt(0) // remove header
        lines.forEach{ db.addLine(it) }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val editText: EditText = findViewById(R.id.editText)
            try {
                val id: String = editText.text.toString()
                if (id.toInt() in 1..99) {
                    val textView: TextView = findViewById(R.id.textView)
                    val cursor = db.getLine(id)
                    cursor!!.moveToFirst()
                    textView.setText(cursor.getString(cursor.getColumnIndex(DBHelper.CONTACT)))
                } else {
                    Toast.makeText(this, "id must be between 1 and 99", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.printStackTrace().toString(), Toast.LENGTH_LONG).show()
            }
        }

    }
}