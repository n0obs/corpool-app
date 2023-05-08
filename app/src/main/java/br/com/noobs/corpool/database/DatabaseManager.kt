package br.com.noobs.corpool.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.model.TripItem
import com.beust.klaxon.Klaxon
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class DatabaseManager(context: Context, name: String) : SQLiteOpenHelper(context, name, null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE IF NOT EXISTS tbl_trip (id INTEGER PRIMARY KEY , address VARCHAR(200), date VARCHAR(200), location VARCHAR(200), price DOUBLE)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS tbl_salutations")
        onCreate(p0)
    }

    fun insertTrip(tripItem: TripItem) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("address", tripItem.address)
        cv.put("date", tripItem.date.format(DateTimeFormatter.ISO_DATE_TIME))
        cv.put("location", Klaxon().toJsonString(tripItem.location))
        cv.put("price", tripItem.price)
        db.insert("tbl_trip", null, cv)
    }

    @SuppressLint("Range")
    fun getAll(): List<TripItem> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tbl_trip" , null)
        val list = mutableListOf<TripItem>()
        if (cursor.count == 0) {
            cursor.close()
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val locationName = cursor.getString(cursor.getColumnIndex("address"))
                val date = ZonedDateTime.parse(
                    cursor.getString(cursor.getColumnIndex("date")),
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val location = cursor.getString(cursor.getColumnIndex("location"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))
                val tripItem =
                    TripItem(id, locationName, date, Klaxon().parse<Location>(location)!!, price)
                list.add(tripItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

}