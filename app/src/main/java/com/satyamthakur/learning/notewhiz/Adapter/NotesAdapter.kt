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

class NotesAdapter(private val context: Context, val listener: NotesClickListener):
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()


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

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),
            null))

        holder.notes_layout.setOnClickListener {
            listener.onitemClicked(notesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            if (item.title!!.lowercase().contains(search.lowercase()) ||
                item.note!!.lowercase().contains(search.lowercase())) {
                notesList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun randomColor(): Int {
        val colors = arrayListOf<Int>()
        colors.add(R.color.note_color_1)
        colors.add(R.color.note_color_2)
        colors.add(R.color.note_color_3)
        colors.add(R.color.note_color_4)
        colors.add(R.color.note_color_5)
        colors.add(R.color.note_color_6)
        colors.add(R.color.note_color_7)
        colors.add(R.color.note_color_8)
        colors.add(R.color.note_color_9)
        colors.add(R.color.note_color_10)

        val random = (0..9).random()
        return colors[random]
    }


    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val notes_layout = itemView.findViewById<CardView>(R.id.card_view_parent_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

    }

    interface NotesClickListener {
        fun onitemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}