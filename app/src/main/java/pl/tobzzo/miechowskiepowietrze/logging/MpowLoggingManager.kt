package pl.tobzzo.miechowskiepowietrze.logging

import android.content.Context
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT = "ddMMyyyy'T'HHmmss'Z'"
class MpowLoggingManager(context: Context) : LoggingManager{
  private val debugTree = LogcatTree(formatter = MpowFormatter())
  private val mpowTree = MpowTree(context = context, formatter = MpowFormatter(), dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()))

  override fun initialize() {
    plantTrees()
  }

  private fun plantTrees() {
    Timber.d("plantTrees")
    Timber.uprootAll()
    Timber.plant(debugTree)
    Timber.plant(mpowTree)
    configure()
  }

  private fun configure() {
  }
}