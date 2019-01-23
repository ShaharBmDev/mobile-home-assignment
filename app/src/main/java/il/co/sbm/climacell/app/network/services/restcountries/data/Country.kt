package il.co.sbm.climacell.app.network.services.restcountries.data

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.gms.maps.model.LatLng

data class Country(
    @JsonProperty("capital")
    val capital: String?,
    @JsonProperty("flag")
    val flag: String?,
    @JsonProperty("latlng")
    val latlng: List<Double?>?,
    @JsonProperty("name")
    val name: String?
) : Parcelable {

    /**
     * IMPORTANT - make sure you invoke [hasLatLng] before using this method as if the values are missing a default 0.0 value will be inserted!
     * @return a [LatLng] object of this country's capital
     */
    fun getLatLng(): LatLng {
        return LatLng(getLatitude() ?: 0.0, getLongitude() ?: 0.0)
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        ArrayList<Double>().apply { source.readList(this, Double::class.java.classLoader) },
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(capital)
        writeString(flag)
        writeList(latlng)
        writeString(name)
    }

    fun isDataValid(): Boolean {

        return !TextUtils.isEmpty(capital) && !TextUtils.isEmpty(name) && hasLatLng()
    }

    fun hasLatLng(): Boolean {

        return latlng != null && latlng.size >= 2 && getLatitude() != null && getLongitude() != null
    }

    private fun getLatitude(): Double? {

        return latlng?.get(0)
    }

    private fun getLongitude(): Double? {

        return latlng?.get(1)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Country> = object : Parcelable.Creator<Country> {
            override fun createFromParcel(source: Parcel): Country = Country(source)
            override fun newArray(size: Int): Array<Country?> = arrayOfNulls(size)
        }
    }
}