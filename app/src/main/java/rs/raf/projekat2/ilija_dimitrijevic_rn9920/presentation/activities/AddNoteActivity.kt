package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.databinding.ActivityAddNoteBinding
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.AddNoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val rafViewModel: MainContract.ViewModel by viewModel<RafViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_add_note)
        init()
    }

    private fun init(){
        initListenners()
        initObservers()
    }

    private fun initObservers() = with(rafViewModel) {
        addDone.observe(this@AddNoteActivity){
            renderState(it)
        }
    }

    private fun renderState(state: AddNoteState){
        when(state) {
            is AddNoteState.Success -> Toast.makeText(this, "Note added succesfully", Toast.LENGTH_SHORT)
                .show()
            is AddNoteState.Error -> Toast.makeText(this, "Error happened while adding note", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initListenners(){
        val btnPotvrdi = findViewById<Button>(R.id.btnPotvrdi)
        btnPotvrdi.setOnClickListener{
            val title = findViewById<TextView>(R.id.inputAddNoteTittle).text.toString()
            val content = findViewById<TextView>(R.id.inputAddNoteContent).text.toString()
            rafViewModel.addNote(title,content)
            redirect()
        }
    }

    fun redirect(){
        val intent = Intent(this@AddNoteActivity, NotesActivity::class.java)
        this.startActivity(intent)
        finish()
    }



}
