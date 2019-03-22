package pl.tobzzo.miechowskiepowietrze.utils.extensions

import android.content.res.Resources
import pl.tobzzo.miechowskiepowietrze.R.color
import pl.tobzzo.miechowskiepowietrze.R.drawable


inline fun Double.mapToBarColor(resources: Resources): Int {
  return when {
    this >= 100 -> resources.getColor(color.caqiVeryHigh)
    this >= 75 -> resources.getColor(color.caqiHigh)
    this >= 50 -> resources.getColor(color.caqiMedium)
    this >= 25 -> resources.getColor(color.caqiLow)
    this >= 0 -> resources.getColor(color.caqiVeryLow)
    else -> resources.getColor(color.caqiUnknown)
  }
}

inline fun Double.mapToLogoImage(): Int {
  return when {
    this >= 100 -> drawable.explosion_123690_640
    this >= 75 -> drawable.industry_611668_640
    this >= 50 -> drawable.storm_clouds_1967017_640
    this >= 25 -> drawable.barn_1302114_640
    this >= 0 -> drawable.clouds_1552166_640
    else -> drawable.question_mark_1421013_640
  }
}