package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.databinding.RafDataItemBinding
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.diff.RafDataDiffCallback
import timber.log.Timber


class RafDataAdapter : ListAdapter<Data, RafDataAdapter.ViewHolder>(RafDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.e("onCreateViewHolder")
        val itemBinding = RafDataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.e("onBindViewHolder")
        holder.bind(getItem(position))
    }

    class ViewHolder (private val itemBinding: RafDataItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: Data){
            Timber.e("RafDataViewHolder")
            itemBinding.inputPredmet.text = data.predmet
            itemBinding.inputProfesor.text = data.nastavnik
            itemBinding.inputUcionica.text = data.ucionica
            itemBinding.inputGrupe.text = data.grupe
            itemBinding.inputDan.text = data.dan
            itemBinding.inputVreme.text = data.termin
            itemBinding.inputTip.text = data.tip
        }

    }

}

