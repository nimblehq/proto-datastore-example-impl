package co.nimblehq.datastore.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import co.nimblehq.datastore.R
import co.nimblehq.datastore.data.UserEntity
import co.nimblehq.datastore.domain.UserPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

const val EXTRA_USER_PREF = "user_pref"

class MainActivity : AppCompatActivity() {

    private lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreferenceManager = UserPreferenceManager(applicationContext)

        initView()
        observePreferences()
    }

    private fun initView() {
        btRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val favoriteColor = etFavoriteColor.text.toString()
            val favoriteNumber = etFavoriteNumber.text.toString().toInt()

            if (username.isNotEmpty() && favoriteColor.isNotEmpty()) {
                lifecycleScope.launch {
                    userPreferenceManager.updateUserPreference(
                        username = username,
                        favoriteColor = favoriteColor,
                        favoriteNumber = favoriteNumber,
                        isLogin = true
                    )
                }
            } else {
                Toast.makeText(this, "Please fill enter in the bank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observePreferences() {
        userPreferenceManager.userPreferenceFlow.asLiveData().observe(this) {
            redirectToDetailActivity(it)
        }
    }

    private fun redirectToDetailActivity(userEntity: UserEntity) {
        if (userEntity.isLogin) {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(EXTRA_USER_PREF, userEntity)
                startActivity(this)
                finish()
            }
        }
    }
}
