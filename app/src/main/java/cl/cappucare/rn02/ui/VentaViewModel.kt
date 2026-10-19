package cl.cappucare.rn02.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cl.cappucare.rn02.data.MovimientoVenta
import cl.cappucare.rn02.data.RepositorioVentas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EstadoFormulario(
    val producto: String = "",
    val cantidad: String = "",
    val responsable: String = "",
    val errorProducto: String = "",
    val errorCantidad: String = "",
    val errorResponsable: String = "",
    val errorGeneral: String = "",
    val guardando: Boolean = false,
    val guardado: Boolean = false
)

class VentaViewModel(private val repositorio: RepositorioVentas) : ViewModel() {
    private val _formulario = MutableStateFlow(EstadoFormulario())
    val formulario: StateFlow<EstadoFormulario> = _formulario.asStateFlow()

    val historial: StateFlow<List<MovimientoVenta>> = repositorio.movimientos.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )

    fun cambiarProducto(valor: String) = actualizar {
        it.copy(producto = valor, errorProducto = "", errorGeneral = "")
    }
    fun cambiarCantidad(valor: String) = actualizar {
        it.copy(cantidad = valor, errorCantidad = "", errorGeneral = "")
    }
    fun cambiarResponsable(valor: String) = actualizar {
        it.copy(responsable = valor, errorResponsable = "", errorGeneral = "")
    }
    fun limpiarConfirmacion() = actualizar { it.copy(guardado = false) }

    private fun actualizar(cambio: (EstadoFormulario) -> EstadoFormulario) {
        _formulario.value = cambio(_formulario.value)
    }

    fun registrarVenta() {
        val datos = formulario.value
        if (datos.guardando) return
        val cantidad = datos.cantidad.toIntOrNull()
        val errorProducto = if (datos.producto.isBlank()) "Selecciona un producto." else ""
        val errorCantidad = when {
            datos.cantidad.isBlank() -> "Ingresa la cantidad."
            cantidad == null -> "Ingresa un número entero válido."
            cantidad <= 0 -> "La cantidad debe ser mayor que cero."
            else -> ""
        }
        val errorResponsable = if (datos.responsable.isBlank()) {
            "Ingresa el nombre del responsable."
        } else ""

        if (errorProducto.isNotEmpty() || errorCantidad.isNotEmpty() ||
            errorResponsable.isNotEmpty()
        ) {
            actualizar {
                it.copy(
                    errorProducto = errorProducto,
                    errorCantidad = errorCantidad,
                    errorResponsable = errorResponsable,
                    errorGeneral = "",
                    guardado = false
                )
            }
            return
        }

        viewModelScope.launch {
            actualizar {
                it.copy(
                    guardando = true, errorGeneral = "", errorProducto = "",
                    errorCantidad = "", errorResponsable = "", guardado = false
                )
            }
            try {
                repositorio.registrarVenta(datos.producto, cantidad!!, datos.responsable)
                actualizar {
                    it.copy(guardando = false, guardado = true, cantidad = "", errorGeneral = "")
                }
            } catch (e: Exception) {
                actualizar {
                    it.copy(
                        guardando = false,
                        errorGeneral = e.message ?: "No se pudo guardar el movimiento."
                    )
                }
            }
        }
    }

    companion object {
        fun fabrica(repositorio: RepositorioVentas): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    VentaViewModel(repositorio) as T
            }
    }
}
