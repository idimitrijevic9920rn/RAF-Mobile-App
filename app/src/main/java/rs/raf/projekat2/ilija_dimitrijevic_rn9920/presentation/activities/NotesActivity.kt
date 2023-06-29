package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.adapter.NotesAdapter
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel
import timber.log.Timber


class NotesActivity : AppCompatActivity() {

    private lateinit var adapter: NotesAdapter
    private val rafViewModel: MainContract.ViewModel by viewModel<RafViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        init()
    }

    private fun init(){
        initUI()
        initObservers()
    }

    private fun initUI(){
        initRecycler()
        initListenners()
    }

    private fun initRecycler(){
        findViewById<RecyclerView>(R.id.rvNotes).layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter { data ->
            Timber.e(data.first.title)
            noteAction(data.first,data.second)}
        findViewById<RecyclerView>(R.id.rvNotes).adapter = adapter
    }


    private fun initListenners(){
        val btnAddNote = findViewById<ImageView>(R.id.btnAddNote)
        val switch = findViewById<Switch>(R.id.notesSwitch)
        val search = findViewById<EditText>(R.id.notesSearchBar)
        val btnScheduler = findViewById<ImageView>(R.id.btnSchedule)
        val btnStatitics = findViewById<ImageView>(R.id.btnStatistics)

        search.doAfterTextChanged {
            val filter = it.toString()
            rafViewModel.getNotesByName(filter)
        }

        btnAddNote.setOnClickListener{
            val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
            this@NotesActivity.startActivity(intent)
            finish()
        }
        btnScheduler.setOnClickListener{
            redirectScheduler()
        }

        btnStatitics.setOnClickListener{
            redirectStatistics()
        }


        switch.setOnCheckedChangeListener { _, isChecked ->
            rafViewModel.getAllNotes()
        }

    }

    private fun noteAction(note: Note, type: String){
        if(type=="delete"){
            note.id?.let { rafViewModel.removeNote(it) }
        }else if(type=="archive"){
            note.id?.let { rafViewModel.archiveNote(it,note.archived) }
        }else if(type=="edit"){
            val intent = Intent(this@NotesActivity, EditNoteActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("content", note.content)
            startActivity(intent)
            this@NotesActivity.startActivity(intent)
        }
    }

    private fun initObservers() = with(rafViewModel) {
        noteState.observe(this@NotesActivity){
            renderState(it)
        }
        this.getAllNotes()
    }

    private fun renderState(state: NoteState) {
        val switch = findViewById<Switch>(R.id.notesSwitch)

        when (state) {
            is NoteState.Success -> {

                if(switch.isChecked){
                    adapter.submitList(state.notes)
                    return
                }

                var notes: ArrayList<Note> = arrayListOf()

                for(note in state.notes){
                    if(!note.archived){
                        notes.add(note)
                    }
                }

                adapter.submitList(notes)
            }
        }
    }

    private fun redirectScheduler(){
        val intent = Intent(this@NotesActivity, AppActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private fun redirectStatistics(){
        val intent = Intent(this@NotesActivity, StatisticsActivity::class.java)
        this.startActivity(intent)
        finish()
    }




}