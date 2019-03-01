package pl.tobzzo.miechowskiepowietrze.logging

import android.content.Context
import timber.log.Timber

class MpowLoggingManager(private val context: Context): LoggingManager{
  private val debugTree = LogcatTree()
  private val mpowTree = MpowTree()

  init {
    initialize()
  }

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