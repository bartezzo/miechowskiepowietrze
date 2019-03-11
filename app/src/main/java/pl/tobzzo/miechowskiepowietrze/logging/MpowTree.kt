package pl.tobzzo.miechowskiepowietrze.logging

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MpowTree(formatter : MpowFormatter, private val context: Context, dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale
  .getDefault())
) : BaseLoggingTree(formatter) {

  private val fileNameTimestamp: String = dateFormat.format(Date())

  private fun saveLog(message: String?) {
    try {
      val fileName = "$fileNameTimestamp.log"

      val file = File(context.filesDir, (fileName))

      file.createNewFile()

      if (file.exists()) {
        val fos: OutputStream = FileOutputStream(file, true)
        fos.write(message?.toByteArray())
        fos.close()
      }

    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    saveLog(message)
  }
}