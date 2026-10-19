# Cappucare — RN-02: Registro de salida por venta

Aplicación Android académica para demostrar la regla RN-02 del caso Cappucare. Usa únicamente datos ficticios y no se conecta a un backend.

## Alcance
Esta entrega se limita a RN-02: al registrar una salida por venta, el sistema guarda el motivo `venta`. Incluye Inicio, formulario de venta e historial local para comprobar el registro.

No incluye control ni descuento de stock, gestión completa de productos, login ni conexión con servicios externos; esas funciones quedan fuera del alcance de esta entrega.

## Tecnologías
- Kotlin, Jetpack Compose y Material 3.
- Navigation Compose.
- MVVM con `VentaViewModel`, `StateFlow` y `RepositorioVentas`.
- Room para persistir movimientos localmente.
- `AnimatedVisibility` y `animateContentSize` para animaciones.
- `LocalHapticFeedback` para respuesta háptica al guardar.

## Dónde se implementa RN-02
En `app/src/main/java/cl/cappucare/rn02/data/RepositorioVentas.kt`, dentro de `registrarVenta()`. El repositorio crea un movimiento con `tipo = "SALIDA"` y `motivo = "venta"`. La interfaz muestra el motivo como campo de solo lectura.

## Archivos principales
- `data/MovimientoVenta.kt`: entidad Room.
- `data/MovimientoVentaDao.kt`: inserción y consulta del historial.
- `data/CappucareDatabase.kt`: configuración de Room.
- `data/RepositorioVentas.kt`: validaciones y regla RN-02.
- `ui/VentaViewModel.kt`: estado con StateFlow y coordinación del registro.
- `ui/screens/`: pantallas Inicio, Venta e Historial.
- `ui/navigation/Rutas.kt`: rutas.
- `ui/theme/`: tema visual.
- `MainActivity.kt`: navegación principal.

## Cómo ejecutar
1. Abre la carpeta raíz en Android Studio.
2. Configura JDK 17 y Android SDK 35.
3. Sincroniza Gradle y descarga las dependencias.
4. Ejecuta en un emulador o dispositivo Android compatible.

El paquete no incluye `gradle-wrapper.jar`. Si Android Studio requiere el wrapper, con Gradle instalado abre una terminal en la raíz y ejecuta `gradle wrapper --gradle-version 8.9`.

## Pruebas manuales recomendadas
1. Guardar sin producto: debe aparecer un error bajo Producto.
2. Guardar cantidad vacía o cero: debe aparecer un error bajo Cantidad.
3. Guardar sin responsable: debe aparecer un error bajo Responsable.
4. Registrar una venta válida: debe mostrar confirmación y respuesta háptica si el dispositivo la admite.
5. Abrir Historial y comprobar tipo `SALIDA` y motivo `venta`.
6. Cerrar y abrir la aplicación y comprobar que Room conserva el movimiento.

