package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Favorite
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Main
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Settings
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity
import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
import javax.inject.Inject

class SensorActivity: AppCompatActivity(), SensorContract.SensorView {
  @Inject lateinit var presenter: SensorPresenter
  private val bottomNavigation: BottomNavigationView by bindView(R.id.bottom_navigation)

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sensor)
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    setListeners()
  }

  override fun updateChart() {
  }

  override fun showChart() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

  override fun hideChart() {
  }

  private fun setListeners() {
    /*
    airQualityImageView.setOnClickListener {
      analyticsComponent.logAction("logAction", "airQualityImageView")
      fakeCAQI = (fakeCAQI + 25) % 168 - 1
      airQualityImageView.setImageResource(fakeCAQI.mapToLogoImage())
    }
    */

    bottomNavigation.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_main -> {
          presenter.onNavigationItemClicked(Main)
        }

        R.id.action_favorite -> {
          presenter.onNavigationItemClicked(Favorite)
        }

        R.id.action_settings -> {
          presenter.onNavigationItemClicked(Settings)
        }
      }
      return@setOnNavigationItemSelectedListener true
    }
  }
}