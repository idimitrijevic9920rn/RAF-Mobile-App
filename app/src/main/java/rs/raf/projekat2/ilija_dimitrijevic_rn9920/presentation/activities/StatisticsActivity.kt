package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NotesByDateState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel
import timber.log.Timber

class StatisticsActivity : AppCompatActivity() {

    private val rafViewModel: MainContract.ViewModel by viewModel<RafViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        init()
    }

    private fun init(){
        initObservers()
        initListennerts()
    }

    private fun initObservers() = with(rafViewModel) {
        notesByDayState.observe(this@StatisticsActivity){
            renderState(it)
        }
        this.getNotesByDate()
    }

    private fun initListennerts(){
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener{
            goBack()
        }
    }

    private fun renderState(state: NotesByDateState) {
        val composeHolder = findViewById<ComposeView>(R.id.statisticsComposeHolder)
        when (state) {
            is NotesByDateState.Success -> {
                composeHolder.setContent {
                    StatisticsScreen(state.notes)
                }
            }
        }
    }

    private fun goBack() {
        val intent = Intent(this@StatisticsActivity, NotesActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    @Composable
    fun StatisticsScreen(
        notes: List<Int>,
        modifier: Modifier = Modifier,
    ){

        Canvas(modifier = Modifier
            .size(500.dp)
            .padding(40.dp).background(Color.White)){
            val canvasWidth = size.width
            val canvasHeight = size.height
            var x = canvasHeight / 10f
            var maxElement = notes.maxOrNull()


            for(note in notes){
                if (maxElement != null) {
                    drawRect(
                        color = Color.Gray,
                        topLeft = Offset(x = x, y = 100f * (maxElement - note + 1) + canvasHeight/3),
                        size = Size(width = 100f, height = 100f * note)
                    )
                    x+=110f
                }
            }

        }

    }



}