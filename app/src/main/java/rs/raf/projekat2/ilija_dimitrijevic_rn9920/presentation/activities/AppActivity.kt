package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.R
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.databinding.ActivityAppBinding
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.adapter.RafDataAdapter
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.RafDataState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel
import timber.log.Timber


class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private val rafViewModel: MainContract.ViewModel by viewModel<RafViewModel>()
    val groups: ArrayList<String> = ArrayList<String>()
    val days = arrayListOf("Dan","PON","UTO","SRE","ČET","PET")

    private lateinit var adapter: RafDataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_app)
        init()
    }


    fun init(){
        initUi()
        initObservers()
    }

    private fun initUi(){
        initRecycler()
        initListenners()
    }

    private fun initObservers() = with(rafViewModel) {
        rafDataState.observe(this@AppActivity){
            renderState(it)
        }
        this.getAll()
        this.fetchAll()
    }

    private fun initRecycler(){
        findViewById<RecyclerView>(R.id.rvRafData).layoutManager = LinearLayoutManager(this)
        adapter = RafDataAdapter()
        findViewById<RecyclerView>(R.id.rvRafData).adapter = adapter
    }


    private fun initListenners(){
        val searchBtn = findViewById<Button>(R.id.btnSearchData)
        val notesBtn = findViewById<ImageView>(R.id.notesBtn)
        searchBtn.setOnClickListener{
            filterData()
        }
        notesBtn.setOnClickListener{
            redirect()
        }
    }

    private fun filterData(){
        val spinnerGrupa = findViewById<Spinner>(R.id.spinnerGrupa)
        val spinnerDan = findViewById<Spinner>(R.id.spinnerDan)
        val searchBar = findViewById<EditText>(R.id.searchBar)

        val grupa = spinnerGrupa.selectedItem.toString()
        val dan = spinnerDan.selectedItem.toString()
        val profesorPredmet = searchBar.text.toString()

        rafViewModel.getFilteredData(grupa = grupa, dan = dan, profesorPredmet = profesorPredmet)
    }

    private fun renderState(state: RafDataState) {
        when (state) {
            is RafDataState.Success -> {
                adapter.submitList(state.data)
                showLoadingState(false)
                if(groups.size<2){
                    fillGroups(state.data)
                }
            }
            is RafDataState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is RafDataState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is RafDataState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun initSpinners(){
        val spinner1: Spinner = findViewById(R.id.spinnerGrupa)
        val spinner2: Spinner = findViewById(R.id.spinnerDan)

        val adapterGroup = ArrayAdapter(this, android.R.layout.simple_spinner_item, groups)
        val adapterDay = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)

        adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = adapterGroup
        spinner2.adapter = adapterDay
    }

    private fun fillGroups(data: List<Data>){
        var grupe: Array<String>? = null
        groups.clear()
        groups.add("Grupa")
        for(value in data){
            grupe = value.grupe.replace(" ", "").split(",").toTypedArray()
            for(grupa in grupe){
                if(grupa !in groups){
                    groups.add(grupa)
                }
            }
        }
        initSpinners()
    }

    private fun showLoadingState(loading: Boolean) {
        var spinnerGrupa = findViewById<Spinner>(R.id.spinnerGrupa)
        var spinnerDan = findViewById<Spinner>(R.id.spinnerDan)
        var loadingPb = findViewById<ProgressBar>(R.id.loadingPb)
        var rvRafData = findViewById<RecyclerView>(R.id.rvRafData)
        var searchBar = findViewById<EditText>(R.id.searchBar)
        var btnSearchData = findViewById<Button>(R.id.btnSearchData)
        var notesBtn = findViewById<ImageView>(R.id.notesBtn)

        spinnerGrupa.isVisible = !loading
        spinnerDan.isVisible = !loading
        loadingPb.isVisible = loading
        rvRafData.isVisible = !loading
        searchBar.isVisible = !loading
        btnSearchData.isVisible = !loading
        notesBtn.isVisible = !loading

    }

    private fun redirect(){
        val intent = Intent(this@AppActivity, NotesActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}