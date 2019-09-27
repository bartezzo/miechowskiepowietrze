package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.example.ExampleInterface
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Favorite
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Main
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Settings
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseActivity
import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
import javax.inject.Inject

class SensorActivity : BaseActivity(), SensorContract.View {

  @Inject lateinit var sensorPresenter: SensorPresenter
  @Inject lateinit var exampleClass: ExampleInterface

  private val bottomNavigation: BottomNavigationView by bindView(R.id.bottom_navigation)

  override fun getLayoutResId(): Int = R.layout.activity_main

  override fun init() {
    super.init()
  }

  override fun onResume() {
    super.onResume()
    sensorPresenter.takeView(this)
//    analyticsComponent.logAction("logAction", "onResume")
//    analyticsComponent.logScreen("LoginActivity")
//    networkComponent.restartLoading(false)
  }

  override fun onDestroy() {
    super.onDestroy()
//    networkComponent.detachNetworkListener(this)
    sensorPresenter.dropView()
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
  }


  override fun updateChart() {

  }

  override fun showChart() {

  }

  override fun hideChart() {

  }

  override fun setListeners() {
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
          sensorPresenter.onNavigationItemClicked(Main)
        }

        R.id.action_favorite -> {
          sensorPresenter.onNavigationItemClicked(Favorite)
        }

        R.id.action_settings -> {
          sensorPresenter.onNavigationItemClicked(Settings)
        }
      }
      return@setOnNavigationItemSelectedListener true
    }
  }

}