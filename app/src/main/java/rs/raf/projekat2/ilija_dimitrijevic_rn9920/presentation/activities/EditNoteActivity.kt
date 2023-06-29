package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel

class EditNoteActivity : AppCompatActivity() {

    private val rafViewModel: MainContract.ViewModel by viewModel<RafViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        init()
    }

    private fun init(){
        initUI()
        initListenners()
    }

    private fun initListenners(){
        val btnIzmeni = findViewById<Button>(R.id.btnIzmeni)
        val btnOdustani = findViewById<Button>(R.id.btnOdustani)
        val inputTitle = findViewById<EditText>(R.id.inputEditNoteTittle)
        val inputContent = findViewById<EditText>(R.id.inputEditNoteContent)

        val bundle = intent.extras
        val id = bundle!!.getInt("id")

        btnIzmeni.setOnClickListener{
            val title = inputTitle.text.toString()
            val content = inputContent.text.toString()
            rafViewModel.editNote(id = id,title = title,content = content)
            redirect()
        }

        btnOdustani.setOnClickListener{
            redirect()
        }

    }

    private fun initUI(){
        val inputTitle = findViewById<EditText>(R.id.inputEditNoteTittle)
        val inputContent = findViewById<EditText>(R.id.inputEditNoteContent)

        val bundle = intent.extras
        val title = bundle!!.getString("title")
        val content = bundle!!.getString("content")

        inputTitle.setText(title)
        inputContent.setText(content)

    }

    fun redirect(){
        val intent = Intent(this@EditNoteActivity, NotesActivity::class.java)
        this.startActivity(intent)
        finish()
    }

}