package com.satyamthakur.learning.notewhiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.satyamthakur.learning.notewhiz.Models.Note
import com.satyamthakur.learning.notewhiz.databinding.ActivityAddNodeBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddNodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNodeBinding

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try {

            old_note = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(old_note.title)
            binding.etDescription.setText(old_note.note)
            isUpdate = true

        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheckBtn.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etDescription.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                if (isUpdate) {
                    note = Note(old_note.id, title, note_desc, formatter.format(Date()))
                }
                else {
                    note = Note(null, title, note_desc, formatter.format(Date()))
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBackBtn.setOnClickListener {
            onBackPressed()
        }

    }
}