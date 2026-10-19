package cl.cappucare.rn02.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovimientoVenta::class], version = 1, exportSchema = false)
abstract class CappucareDatabase : RoomDatabase() {
    abstract fun movimientoDao(): MovimientoVentaDao

    companion object {
        @Volatile private var instancia: CappucareDatabase? = null

        fun obtener(context: Context): CappucareDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    CappucareDatabase::class.java,
                    "cappucare_rn02.db"
                ).build().also { instancia = it }
            }
    }
}
