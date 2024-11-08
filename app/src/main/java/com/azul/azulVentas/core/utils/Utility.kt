package com.azul.azulVentas.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
    }
}