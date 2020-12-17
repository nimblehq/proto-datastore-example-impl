package co.nimblehq.datastore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.nimblehq.datastore.R
import co.nimblehq.datastore.data.UserEntity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        with(intent.extras?.get(EXTRA_USER_PREF) as UserEntity) {
            tvUsername.text = getString(R.string.username_title, username)
            tvFavoriteColor.text = getString(R.string.favorite_color_title, favoriteColor)
            tvFavoriteNumber.text = getString(R.string.favorite_number_title, favoriteNumber)
        }
    }
}
