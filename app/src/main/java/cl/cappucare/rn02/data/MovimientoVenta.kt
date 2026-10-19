package cl.cappucare.rn02.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Solo usamos información inventada para esta demostración.
@Entity(tableName = "movimientos_venta")
data class MovimientoVenta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val producto: String,
    val cantidad: Int,
    val responsable: String,
    val tipo: String = "SALIDA",
    val motivo: String = "venta",
    val fecha: Long = System.currentTimeMillis()
)
