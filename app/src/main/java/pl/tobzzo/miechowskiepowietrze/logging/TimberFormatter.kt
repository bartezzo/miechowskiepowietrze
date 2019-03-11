package pl.tobzzo.miechowskiepowietrze.logging

import java.util.logging.LogRecord

interface TimberFormatter {
  fun format(level: String?, tag:String?, record: LogRecord?): String
}