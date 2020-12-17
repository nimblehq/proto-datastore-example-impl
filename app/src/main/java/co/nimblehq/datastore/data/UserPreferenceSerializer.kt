package co.nimblehq.datastore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import co.nimblehq.datastore.UserPreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferenceSerializer : Serializer<UserPreference> {
    override val defaultValue: UserPreference
        get() = UserPreference.getDefaultInstance()

    override fun readFrom(input: InputStream): UserPreference {
        try {
            return UserPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: UserPreference, output: OutputStream) {
        t.writeTo(output)
    }
}
