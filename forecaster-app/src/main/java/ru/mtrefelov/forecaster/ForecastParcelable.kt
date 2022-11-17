package ru.mtrefelov.forecaster

import android.os.Parcel
import android.os.Parcelable

class ForecastParcelable(val temperatureCelsius: Double, val timestampSeconds: Long) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readDouble(), parcel.readLong())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeDouble(temperatureCelsius)
            writeLong(timestampSeconds)
        }
    }

    companion object CREATOR : Parcelable.Creator<ForecastParcelable> {
        override fun createFromParcel(parcel: Parcel): ForecastParcelable {
            return ForecastParcelable(parcel)
        }

        override fun newArray(size: Int): Array<ForecastParcelable?> {
            return arrayOfNulls(size)
        }
    }
}