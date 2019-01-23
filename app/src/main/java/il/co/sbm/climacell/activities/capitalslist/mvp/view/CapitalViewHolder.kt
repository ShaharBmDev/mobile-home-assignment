package il.co.sbm.climacell.activities.capitalslist.mvp.view

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.jakewharton.rxbinding2.view.RxView
import il.co.sbm.climacell.R
import il.co.sbm.climacell.app.network.services.restcountries.data.Country
import io.reactivex.subjects.PublishSubject

class CapitalViewHolder(iCellView: View) : RecyclerView.ViewHolder(iCellView) {

    @BindView(R.id.iv_cellCapitals_flag)
    lateinit var ivFlag: ImageView

    @BindView(R.id.tv_cellCapitals_capital)
    lateinit var tvCapital: TextView

    @BindView(R.id.tv_cellCapitals_country)
    lateinit var tvCountry: TextView

    init {
        ButterKnife.bind(this, iCellView)
    }

    internal fun onBind(iCountry: Country, iPublishSubject: PublishSubject<Country>) {

        GlideToVectorYou
            .init()
            .with(itemView.context as Activity?)
            .load(Uri.parse(iCountry.flag), ivFlag)

        tvCapital.text = iCountry.capital
        tvCountry.text = iCountry.name

        setListeners(iCountry, iPublishSubject)
    }

    @SuppressLint("CheckResult")
    private fun setListeners(iCountry: Country, iPublishSubject: PublishSubject<Country>) {

        RxView.clicks(itemView)
            .map {
                iCountry
            }
            .subscribe(iPublishSubject)
    }
}