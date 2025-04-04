package com.azul.azulVentas.core.utils

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.ui.theme.DarkTextColor
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

class Utility {

    companion object {

        // Función para calcular la diferencia en días entre dos fechas
        fun calculateDaysBetween(startDate: LocalDate, endDate: LocalDate): Long {
            return ChronoUnit.DAYS.between(startDate, endDate)
        }

        // Otras funciones que podrías agregar a esta clase
        fun isLeapYear(year: Int): Boolean {
            return LocalDate.of(year, 1, 1).isLeapYear
        }

        fun getCurrentDate(): LocalDate {
            return LocalDate.now()
        }

        //Calcula dias a fecha de hoy
        fun calculateDaysToTargetDate(targetDate: LocalDateTime): Long {
            val now = LocalDateTime.now()
            return ChronoUnit.DAYS.between(targetDate, now)
        }

        fun getCurrentDateTime(): String {
            //return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            return SimpleDateFormat( "EEE, MMM d, ''yyyy, hh:mm:ss a"  , Locale.getDefault()).format(Date())
        }

        @Composable
        fun ShowRealTimeClock() {
            var currentDateTime by remember { mutableStateOf(getCurrentDateTime()) }

            LaunchedEffect(Unit) {
                while (true) {
                    currentDateTime = getCurrentDateTime()
                    delay(10000) // Actualiza cada  10segundo
                }
            }

            Text(
                text = currentDateTime,
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkTextColor
            )
        }

        fun formatDate(dateString: String): String {
            // Parse the input date string to LocalDate
            val date = LocalDate.parse(dateString)

            // Define the desired output format
            val formatter = DateTimeFormatter.ofPattern("MMM, dd/yyyy", Locale.getDefault())

            // Format the date and return it as a string
            return date.format(formatter)
        }

        fun stringToLocalDateTime(dateString: String): LocalDateTime? {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            try {
                return LocalDateTime.parse(dateString, formatter)
            } catch (e: DateTimeParseException) {
                println("Error parsing date: ${e.message}")
                return null // Or you can throw the exception if needed
            }
        }

        fun formatCurrency(amount: Double): String {
            val format = NumberFormat.getCurrencyInstance(java.util.Locale("es", "CO")) // Cambia 'US' según tu país
            return format.format(amount)
        }

    }
}