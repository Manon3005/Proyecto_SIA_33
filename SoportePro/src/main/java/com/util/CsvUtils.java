package com.util;

import com.model.domain.Ticket;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class CsvUtils {

    public static boolean generarReporteCSV(List<Ticket> tickets, String filename) {
        int totalTickets = tickets.size();
        int pendientes = 0;
        int enProceso = 0;
        int enEvaluacion = 0;
        int tratados = 0;
        double sumaSatisfaccion = 0;
        double sumaDuracionHoras = 0;
        int cantidadTratadosConFecha = 0;

        for (Ticket ticket : tickets) {
            switch (ticket.getEstado()) {
                case PENDIENTE:
                    pendientes++;
                    break;
                case EN_PROCESO:
                    enProceso++;
                    break;
                case EN_EVALUACION:
                    enEvaluacion++;
                    break;
                case TRATADO:
                    tratados++;
                    if (ticket.getFechaFinalizacion() != null && ticket.getFechaCreacion() != null) {
                        Duration duracion = Duration.between(ticket.getFechaCreacion(), ticket.getFechaFinalizacion());
                        sumaDuracionHoras += duracion.toMinutes() / 60.0;
                        cantidadTratadosConFecha++;
                    }
                    sumaSatisfaccion += ticket.getSatisfaccion();
                    break;
            }

        }

        double satisfaccionPromedio = tratados > 0 ? sumaSatisfaccion / tratados : 0;
        double duracionPromedio = cantidadTratadosConFecha > 0 ? sumaDuracionHoras / cantidadTratadosConFecha : 0;

        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Total de tickets;").append(String.valueOf(totalTickets)).append("\n");
            writer.append("Tickets pendientes;").append(String.valueOf(pendientes)).append("\n");
            writer.append("Tickets en proceso;").append(String.valueOf(enProceso)).append("\n");
            writer.append("Tickets en evaluación;").append(String.valueOf(enEvaluacion)).append("\n");
            writer.append("Tickets tratados;").append(String.valueOf(tratados)).append("\n");
            writer.append("Satisfacción promedio;").append(String.format("%.2f", satisfaccionPromedio)).append("\n");
            writer.append("Duración promedio de resolución (en horas);").append(String.format("%.2f", duracionPromedio)).append("\n");
            writer.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error al generar el archivo CSV: " + e.getMessage());
            return false;
        }
    }
}
