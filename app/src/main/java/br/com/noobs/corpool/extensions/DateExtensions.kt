package br.com.noobs.corpool.extensions

import com.google.firebase.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

fun ZonedDateTime.toTimestamp() = Timestamp(Date.from(this.toInstant()))

fun Timestamp.toZonedDateTime() = this.toDate().toInstant().atZone(ZoneId.systemDefault())