package pl.tobzzo.miechowskiepowietrze.logging

import android.util.Log
import timber.log.Timber.DebugTree
import java.util.logging.Level
import java.util.logging.LogRecord

abstract class BaseLoggingTree(private val formatter: TimberFormatter) : DebugTree() {
  open var loggerLevel: Int = 3 //Debug

  private fun formatLog(priority: Int, tag: String?, message: String?): String {
    val record = LogRecord(Level.ALL, message)
    return formatter.format(getLevelTag(priority), tag, record)
  }

  private fun getLevelTag(priority: Int): String =
    when (priority) {
      Log.VERBOSE -> "VERBOSE"
      Log.INFO -> "INFO"
      Log.DEBUG -> "DEBUG"
      Log.WARN -> "WARN"
      Log.ERROR -> "ERROR"
      Log.ASSERT -> "ASSERT"
      else -> {
        "WTF"
      }
    }

  override fun createStackElementTag(element: StackTraceElement): String? {
    return "${element.lineNumber}: ${element.className}.${element.methodName}"
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority < loggerLevel) return else super.log(priority, tag, formatLog(priority, tag, message), t)
  }
}