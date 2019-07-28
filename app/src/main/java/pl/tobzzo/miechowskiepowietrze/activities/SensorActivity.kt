package pl.tobzzo.miechowskiepowietrze.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.tobzzo.miechowskiepowietrze.MpowApplication
import pl.tobzzo.miechowskiepowietrze.database.DataBaseManager
import javax.inject.Inject

class SensorActivity: AppCompatActivity() {
  @Inject lateinit var dataBaseManager: DataBaseManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (this.application as MpowApplication).createSensorComponent().inject(this)
  }

  override fun onDestroy() {
    (this.application as MpowApplication).releaseSensorComponent()
    super.onDestroy()
  }
}