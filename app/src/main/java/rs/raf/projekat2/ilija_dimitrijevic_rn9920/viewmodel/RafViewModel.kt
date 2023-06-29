package rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Resource
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.repositories.RafRepository
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract.MainContract
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.AddNoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NotesByDateState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.RafDataState
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class RafViewModel(
    private val rafRepository: RafRepository
) : ViewModel(), MainContract.ViewModel {

    override val data: MutableLiveData<List<Data>> = MutableLiveData()
    override val rafDataState: MutableLiveData<RafDataState> = MutableLiveData()
    override val noteState: MutableLiveData<NoteState> = MutableLiveData()
    override val addDone: MutableLiveData<AddNoteState> = MutableLiveData()
    override val notesByDayState: MutableLiveData<NotesByDateState> = MutableLiveData()


    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    private val subscriptions = CompositeDisposable()


    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                rafRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAll() {
        val subscription = rafRepository
            .fetchAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> rafDataState.value = RafDataState.Loading
                        is Resource.Success -> rafDataState.value = RafDataState.DataFetched
                        is Resource.Error -> rafDataState.value = RafDataState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    rafDataState.value = RafDataState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = rafRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    rafDataState.value = RafDataState.Success(it)
                },
                {
                    rafDataState.value = RafDataState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getFilteredData(grupa: String, dan: String, profesorPredmet: String) {
        val subscription = rafRepository
            .getFilteredData(grupa = grupa,dan = dan,profesorPredmet = profesorPredmet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    rafDataState.value = RafDataState.Success(it)
                },
                {
                    rafDataState.value = RafDataState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun addNote(title: String, content: String) {
        val subscription = rafRepository
            .addNote(title,content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddNoteState.Success
                    Timber.e("Uspesno dodat note")
                },
                {
                    addDone.value = AddNoteState.Error("Error happened while adding note")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun getAllNotes() {
        val subscription = rafRepository
            .getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteState.value = NoteState.Success(it)
                },
                {
                    noteState.value = NoteState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun removeNote(id: Int) {
        val subscription = rafRepository
            .deleteNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Deleted Note")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun archiveNote(id: Int, archive: Boolean) {
        val subscription = rafRepository
            .archiveNote(id = id,archived = archive)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Archived Note")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun editNote(id: Int, title: String, content: String) {
        val subscription = rafRepository
            .editNote(id = id, title = title,content = content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Edited Note")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getNotesByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getNotesByDate() {
        val subscription = rafRepository
            .getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val dateSub = LocalDate.now().minusDays(5)
                    val notes: ArrayList<Int> = arrayListOf()
                    for(note in it) {
                        val date: LocalDate = note.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        if(date>dateSub){
                            notes.add(date.dayOfYear)
                        }
                    }
                    val map = (notes.groupingBy { it }.eachCount().filter { it.value > 0 })
                    notes.clear()
                    for(note in map){
                        notes.add(note.value)
                    }
                    notesByDayState.value = NotesByDateState.Success(notes)
                },
                {
                    notesByDayState.value = NotesByDateState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }


    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}