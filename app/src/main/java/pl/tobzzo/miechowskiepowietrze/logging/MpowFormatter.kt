package pl.tobzzo.miechowskiepowietrze.logging

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.Formatter
import java.util.logging.LogRecord

class MpowFormatter : Formatter(), TimberFormatter {
  companion object {
    const val MAX_LOG_CAPACITY = 1000
    const val NO_THREAD_ID_TEXT = "NO_THREAD_ID"
    val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS", Locale.getDefault())
  }

  override fun format(level: String?, tag: String?, record: LogRecord?): String {
    val builder = StringBuilder(MAX_LOG_CAPACITY)
    with(builder) {
      append(level).append(" ")
      append("[tid:").append(record?.threadID ?: NO_THREAD_ID_TEXT).append("] - ")
      append("[").append(dateFormat.format(record?.millis?.let { Date(it) })).append("] - ")
      append("[").append(tag).append("] - ")
      append(formatMessage(record))
      append("\n\n")
    }
    return builder.toString()
  }

  override fun format(record: LogRecord?): String = format(null, null, record)
}