package com.jotacode.poliacciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;


@Service
public class AlphaVantageService {

    private final RestTemplate clienteHttp;

    private static final String API_URL ="https://www.alphavantage.co/query";
    private static final String API_KEY = "EHWEKFR9ODOFEKIV";

    public AlphaVantageService(RestTemplate restTemplate) {
        this.clienteHttp = restTemplate;
    }

    public boolean verificarSimbolo(String simbolo) {
        String simboloEnMayusculas = simbolo.toUpperCase();
        String url = API_URL + "?function=OVERVIEW&symbol=" + simboloEnMayusculas + "&apikey=" + API_KEY;

        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);

            // Depuración: imprime la respuesta completa
            System.out.println("Respuesta de verificación de símbolo: " + respuesta);

            // Verificar si la respuesta contiene el campo "Symbol"
            return respuesta != null
                   && respuesta.containsKey("Symbol")
                   && respuesta.get("Symbol").equals(simboloEnMayusculas);
        } catch (Exception e) {
            System.out.println("Error al verificar el símbolo: " + e.getMessage());
            return false;
        }
    }



    public Double obtenerPrecioAccion(String simbolo) {
        String simboloEnMayusculas = simbolo.toUpperCase();
        String url = API_URL + "?function=TIME_SERIES_DAILY&symbol=" + simboloEnMayusculas + "&apikey=" + API_KEY;

        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);
            if (respuesta == null || !respuesta.containsKey("Time Series (Daily)")) {
                return null;
            }
            Map<String, Object> serieTemporal = (Map<String, Object>) respuesta.get("Time Series (Daily)");
            String ultimaActualizacion = ((Map<String, Object>) respuesta.get("Meta Data")).get("3. Last Refreshed").toString();
            Map<String, String> datosHoy = (Map<String, String>) serieTemporal.get(ultimaActualizacion);

            return Double.parseDouble(datosHoy.get("4. close")); // Precio de cierre
        } catch (Exception e) {
            return null; // Si hay un error, retornar null
        }
    }

    public Double obtenerPrecioAccionPorFecha(String simbolo, LocalDate fecha) {
        String simboloEnMayusculas = simbolo.toUpperCase();
        String url = API_URL + "?function=TIME_SERIES_DAILY&symbol=" + simboloEnMayusculas + "&apikey=" + API_KEY;

        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);

            // Depurar la respuesta completa
            System.out.println("Respuesta completa de la API: " + respuesta);

            if (respuesta == null || !respuesta.containsKey("Time Series (Daily)")) {
                System.out.println("La API no devolvió datos válidos para 'Time Series (Daily)'.");
                return null;
            }

            Map<String, Object> serieTemporal = (Map<String, Object>) respuesta.get("Time Series (Daily)");
            System.out.println("Fechas disponibles: " + serieTemporal.keySet());

            String fechaString = fecha.toString();

            if (serieTemporal.containsKey(fechaString)) {
                Map<String, String> datosFecha = (Map<String, String>) serieTemporal.get(fechaString);
                System.out.println("Datos para la fecha " + fechaString + ": " + datosFecha);
                return Double.parseDouble(datosFecha.get("4. close"));
            } else {
                System.out.println("No hay datos para la fecha " + fechaString);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API: " + e.getMessage());
        }
    }




    public Double obtenerPrecioActual(String simbolo) {
        String simboloEnMayusculas = simbolo.toUpperCase();
        String url = API_URL + "?function=TIME_SERIES_INTRADAY&symbol=" + simboloEnMayusculas + "&interval=1min&apikey=" + API_KEY;

        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);

            if (respuesta == null || !respuesta.containsKey("Time Series (1min)")) {
                return null; // Sin datos disponibles
            }

            Map<String, Object> serieTemporal = (Map<String, Object>) respuesta.get("Time Series (1min)");

            // Obtener la última entrada (dato más reciente)
            String ultimaFecha = serieTemporal.keySet().stream().findFirst().orElse(null);
            if (ultimaFecha == null) {
                return null;
            }

            Map<String, String> datosUltimoMinuto = (Map<String, String>) serieTemporal.get(ultimaFecha);
            return Double.parseDouble(datosUltimoMinuto.get("4. close")); // Precio actual
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el precio actual de la acción: " + simbolo, e);
        }
    }


}
