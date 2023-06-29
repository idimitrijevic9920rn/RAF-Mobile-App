package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.adapter

import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.databinding.NoteItemBinding
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.diff.NotesDiffCallback
import timber.log.Timber
import java.util.function.Consumer

class NotesAdapter(onNoteClicked: Consumer<Pair<Note,String>>?) : ListAdapter<Note, NotesAdapter.ViewHolder>(NotesDiffCallback()) {


    private var onNoteClicked: Consumer<Pair<Note, String>>? = null

    init {
        this.onNoteClicked = onNoteClicked
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, Consumer { data: Pair<String, Int> ->
                val note = getItem(data.second)
                onNoteClicked?.accept(Pair(note, data.first))
            })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder (private val itemBinding: NoteItemBinding,onItemClicked: Consumer<Pair<String,Int>>) : RecyclerView.ViewHolder(itemBinding.root) {

        init {

            val deleteBtn = itemBinding.btnDeleteNote
            val archiveBtn = itemBinding.btnArchive
            val editBtn = itemBinding.btnPen

            itemView.setOnClickListener{
                deleteBtn.setOnClickListener{
                    onItemClicked.accept(Pair("delete", bindingAdapterPosition))
                }
                archiveBtn.setOnClickListener{
                    onItemClicked.accept(Pair("archive", bindingAdapterPosition))
                }
                editBtn.setOnClickListener{
                    onItemClicked.accept(Pair("edit", bindingAdapterPosition))
                }

            }
        }

        fun bind(note: Note){
            Timber.e("binding")
            itemBinding.noteTitle.text = note.title
            itemBinding.noteContent.text = note.content
        }

    }

}