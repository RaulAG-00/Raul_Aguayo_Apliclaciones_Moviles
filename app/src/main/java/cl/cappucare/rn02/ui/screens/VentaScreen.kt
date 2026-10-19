package cl.cappucare.rn02.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.cappucare.rn02.ui.VentaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaScreen(viewModel: VentaViewModel, volver: () -> Unit, verHistorial: () -> Unit) {
    val estado by viewModel.formulario.collectAsStateWithLifecycle()
    val vibracion = LocalHapticFeedback.current
    var desplegado by remember { mutableStateOf(false) }
    val productosFicticios = listOf(
        "Guantes médicos", "Mascarillas", "Gasas estériles", "Alcohol gel"
    )

    LaunchedEffect(estado.guardado) {
        if (estado.guardado) vibracion.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Registrar salida por venta", style = MaterialTheme.typography.headlineSmall)
        Text("El motivo se guarda automáticamente como «venta».")

        ExposedDropdownMenuBox(
            expanded = desplegado,
            onExpandedChange = { desplegado = !desplegado }
        ) {
            OutlinedTextField(
                value = estado.producto,
                onValueChange = {},
                readOnly = true,
                isError = estado.errorProducto.isNotEmpty(),
                label = { Text("Producto ficticio") },
                supportingText = {
                    if (estado.errorProducto.isNotEmpty()) Text(estado.errorProducto)
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegado) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = desplegado,
                onDismissRequest = { desplegado = false }
            ) {
                productosFicticios.forEach { producto ->
                    DropdownMenuItem(text = { Text(producto) }, onClick = {
                        viewModel.cambiarProducto(producto)
                        desplegado = false
                    })
                }
            }
        }

        OutlinedTextField(
            value = estado.cantidad,
            onValueChange = { nuevo ->
                if (nuevo.all(Char::isDigit)) viewModel.cambiarCantidad(nuevo)
            },
            label = { Text("Cantidad") },
            isError = estado.errorCantidad.isNotEmpty(),
            supportingText = {
                if (estado.errorCantidad.isNotEmpty()) Text(estado.errorCantidad)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = estado.responsable,
            onValueChange = viewModel::cambiarResponsable,
            label = { Text("Responsable ficticio") },
            isError = estado.errorResponsable.isNotEmpty(),
            supportingText = {
                if (estado.errorResponsable.isNotEmpty()) Text(estado.errorResponsable)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = "venta",
            onValueChange = {},
            readOnly = true,
            label = { Text("Motivo (RN-02)") },
            supportingText = { Text("Este valor lo asigna el sistema y no se puede editar.") },
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(visible = estado.errorGeneral.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
            Text(estado.errorGeneral, color = MaterialTheme.colorScheme.error)
        }
        AnimatedVisibility(visible = estado.guardado, enter = fadeIn(), exit = fadeOut()) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Venta guardada correctamente", style = MaterialTheme.typography.titleMedium)
                    Text("Motivo registrado: venta")
                    TextButton(onClick = {
                        viewModel.limpiarConfirmacion()
                        verHistorial()
                    }) { Text("Ver movimiento en el historial") }
                }
            }
        }

        Button(
            onClick = viewModel::registrarVenta,
            enabled = !estado.guardando,
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (estado.guardando) "Guardando..." else "Guardar venta") }

        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al inicio")
        }
    }
}
