package il.co.sbm.climacell.activities.capitalslist.mvp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import il.co.sbm.climacell.R
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CapitalsAdapter(private var mCountries: List<Country>) :
    RecyclerView.Adapter<CapitalViewHolder>() {

    private var mPublishSubject: PublishSubject<Country> = PublishSubject.create<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapitalViewHolder {
        return CapitalViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_capitals_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mCountries.size
    }

    override fun onBindViewHolder(holder: CapitalViewHolder, position: Int) {
        holder.onBind(mCountries[position], mPublishSubject)
    }

    internal fun getViewClickObservable(): Observable<Country> {

        return mPublishSubject
    }
}
