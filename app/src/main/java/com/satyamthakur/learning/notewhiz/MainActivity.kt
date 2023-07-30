package com.satyamthakur.learning.notewhiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.satyamthakur.learning.notewhiz.Adapter.NotesAdapter
import com.satyamthakur.learning.notewhiz.Database.NoteDatabase
import com.satyamthakur.learning.notewhiz.Models.Note
import com.satyamthakur.learning.notewhiz.Models.NoteViewModel
import com.satyamthakur.learning.notewhiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Initialize the UI
        initUi()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this) { notes ->
            notes?.let {
                adapter.updateList(it)
            }
        }

        database = NoteDatabase.getDatabase(this)
    }

    private fun initUi() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.getSerializableExtra("note") as Note
                if (data != null) {
                    viewModel.insertNote(data)
                }
            }

        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNodeActivity::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter.filterList(newText)
                }

                return true
            }

        })
    }
}