package cl.cappucare.rn02.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen(irVenta: () -> Unit, irHistorial: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Inventory2, contentDescription = null, modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(18.dp))
        Text("Cappucare", style = MaterialTheme.typography.headlineLarge)
        Text("RN-02 · Salidas por venta", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))
        Text("Prototipo académico con datos ficticios")
        Spacer(Modifier.height(30.dp))
        Button(onClick = irVenta, modifier = Modifier.fillMaxWidth()) { Text("Registrar venta") }
        OutlinedButton(onClick = irHistorial, modifier = Modifier.fillMaxWidth()) { Text("Ver historial") }
    }
}
