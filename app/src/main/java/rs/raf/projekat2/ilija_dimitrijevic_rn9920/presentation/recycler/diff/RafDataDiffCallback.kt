package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import timber.log.Timber

class RafDataDiffCallback : DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.predmet.equals(newItem.predmet)

    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.dan.equals(newItem.dan) && oldItem.grupe.equals(newItem.grupe)
                && oldItem.nastavnik.equals(newItem.nastavnik) && oldItem.termin.equals(newItem.termin)
                && oldItem.ucionica.equals(newItem.ucionica) && oldItem.tip.equals(newItem.tip)
    }


}