package cl.cappucare.rn02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.cappucare.rn02.data.CappucareDatabase
import cl.cappucare.rn02.data.RepositorioVentas
import cl.cappucare.rn02.ui.VentaViewModel
import cl.cappucare.rn02.ui.navigation.Rutas
import cl.cappucare.rn02.ui.screens.HistorialScreen
import cl.cappucare.rn02.ui.screens.InicioScreen
import cl.cappucare.rn02.ui.screens.VentaScreen
import cl.cappucare.rn02.ui.theme.CappucareTheme

class MainActivity : ComponentActivity() {
    private val db by lazy { CappucareDatabase.obtener(applicationContext) }
    private val repositorio by lazy { RepositorioVentas(db.movimientoDao()) }
    private val viewModel: VentaViewModel by viewModels { VentaViewModel.fabrica(repositorio) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CappucareTheme { AplicacionRN02(viewModel) }
        }
    }
}

@Composable
fun AplicacionRN02(viewModel: VentaViewModel) {
    val nav = rememberNavController()
    Surface {
        NavHost(navController = nav, startDestination = Rutas.INICIO) {
            composable(Rutas.INICIO) {
                InicioScreen(irVenta = { nav.navigate(Rutas.VENTA) }, irHistorial = { nav.navigate(Rutas.HISTORIAL) })
            }
            composable(Rutas.VENTA) {
                VentaScreen(viewModel, volver = { nav.popBackStack() }, verHistorial = { nav.navigate(Rutas.HISTORIAL) })
            }
            composable(Rutas.HISTORIAL) {
                HistorialScreen(viewModel, volver = { nav.popBackStack() })
            }
        }
    }
}
