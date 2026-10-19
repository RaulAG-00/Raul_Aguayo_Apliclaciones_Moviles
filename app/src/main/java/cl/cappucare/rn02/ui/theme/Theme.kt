package cl.cappucare.rn02.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val colores = lightColorScheme(
    primary = AzulSalud,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = AzulOscuro,
    background = FondoClaro,
    surface = androidx.compose.ui.graphics.Color.White
)

@Composable
fun CappucareTheme(contenido: @Composable () -> Unit) {
    MaterialTheme(colorScheme = colores, content = contenido)
}
