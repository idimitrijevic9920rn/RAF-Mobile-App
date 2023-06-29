package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note

class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title.equals(newItem.title) && oldItem.content.equals(newItem.content) && oldItem.archived.equals(newItem.archived)
    }


}