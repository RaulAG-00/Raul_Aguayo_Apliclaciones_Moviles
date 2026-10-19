package cl.cappucare.rn02.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.cappucare.rn02.ui.VentaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(viewModel: VentaViewModel, volver: () -> Unit) {
    val movimientos by viewModel.historial.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historial de ventas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        AnimatedVisibility(visible = movimientos.isEmpty()) {
            Text("Todavía no hay movimientos registrados")
        }
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(movimientos, key = { it.id }) { movimiento ->
                ElevatedCard(Modifier.fillMaxWidth().animateContentSize()) {
                    Column(Modifier.padding(14.dp)) {
                        Text(movimiento.producto, style = MaterialTheme.typography.titleMedium)
                        Text("Cantidad: ${movimiento.cantidad}")
                        Text("Tipo: ${movimiento.tipo} · Motivo: ${movimiento.motivo}")
                        Text("Responsable: ${movimiento.responsable}")
                        Text("Fecha: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "CL")).format(Date(movimiento.fecha))}")
                    }
                }
            }
        }
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
    }
}
