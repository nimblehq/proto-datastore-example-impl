package co.nimblehq.datastore.domain

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import co.nimblehq.datastore.UserPreference
import co.nimblehq.datastore.data.UserEntity
import co.nimblehq.datastore.data.UserPreferenceSerializer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val TAG = "UserPreferenceManager"

class UserPreferenceManager(context: Context) {

    private val dataStore: DataStore<UserPreference> =
        context.createDataStore(
            fileName = "user_prefs.pb",
            serializer = UserPreferenceSerializer
        )

    val userPreferenceFlow = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading sort order preferences.", it)
            emit(UserPreference.getDefaultInstance())
        } else {
            throw it
        }
    }.map { UserEntity(it.username, it.favoriteColor, it.favoriteNumber, it.isLogin) }

    suspend fun updateUserPreference(
        username: String,
        favoriteColor: String,
        favoriteNumber: Int,
        isLogin: Boolean
    ) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setUsername(username)
                .setFavoriteColor(favoriteColor)
                .setFavoriteNumber(favoriteNumber)
                .setIsLogin(isLogin)
                .build()
        }
    }
}
