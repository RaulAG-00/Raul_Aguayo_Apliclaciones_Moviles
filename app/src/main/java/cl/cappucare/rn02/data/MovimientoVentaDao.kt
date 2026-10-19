package cl.cappucare.rn02.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoVentaDao {
    @Insert
    suspend fun guardar(movimiento: MovimientoVenta): Long

    @Query("SELECT * FROM movimientos_venta ORDER BY fecha DESC, id DESC")
    fun observarMovimientos(): Flow<List<MovimientoVenta>>
}
