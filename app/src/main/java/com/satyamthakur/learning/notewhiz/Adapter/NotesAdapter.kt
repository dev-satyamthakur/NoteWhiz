package com.satyamthakur.learning.notewhiz.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.satyamthakur.learning.notewhiz.Models.Note
import com.satyamthakur.learning.notewhiz.R
import java.util.concurrent.RecursiveAction

class NotesAdapter(private val context: Context):
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList: ArrayList<Note>()
    private val fullList: ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notesList[position]

        holder.title.text = note.title
        holder.title.isSelected = true

        holder.note.text = note.note
        holder.date.text = note.date
        holder.date.isSelected = true

    }


    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_view_parent_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)



    }
}