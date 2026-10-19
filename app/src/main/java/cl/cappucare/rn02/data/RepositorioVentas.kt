package cl.cappucare.rn02.data

class RepositorioVentas(private val dao: MovimientoVentaDao) {
    val movimientos = dao.observarMovimientos()

    // RN-02: en una venta, el motivo siempre se guarda como "venta".
    suspend fun registrarVenta(producto: String, cantidad: Int, responsable: String): Long {
        require(producto.isNotBlank()) { "Seleccione un producto" }
        require(cantidad > 0) { "La cantidad debe ser mayor que cero" }
        require(responsable.isNotBlank()) { "Ingrese el responsable" }

        return dao.guardar(
            MovimientoVenta(
                producto = producto.trim(),
                cantidad = cantidad,
                responsable = responsable.trim(),
                tipo = "SALIDA",
                motivo = "venta"
            )
        )
    }
}
