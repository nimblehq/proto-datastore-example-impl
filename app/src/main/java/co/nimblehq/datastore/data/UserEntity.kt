package co.nimblehq.datastore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val username: String?,
    val favoriteColor: String?,
    val favoriteNumber: Int,
    val isLogin: Boolean
) : Parcelable
