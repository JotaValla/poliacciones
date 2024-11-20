package com.jotacode.poliacciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class AlphaVantageService {

    private final RestTemplate clienteHttp;

    private static final String API_URL ="https://www.alphavantage.co/query";
    private static final String API_KEY = "WPFU0QG1LQ21DU39";

    public AlphaVantageService(RestTemplate restTemplate) {
        this.clienteHttp = restTemplate;
    }

    public boolean verificarSimbolo(String simbolo) {
        // Convertir el símbolo a mayúsculas para garantizar la consistencia
        String simboloEnMayusculas = simbolo.toUpperCase();
        String url = API_URL + "?function=OVERVIEW&symbol=" + simboloEnMayusculas + "&apikey=" + API_KEY;

        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);
            // Verificar si la respuesta contiene el campo "Symbol" y coincide con el símbolo en mayúsculas
            return respuesta != null
                   && respuesta.containsKey("Symbol")
                   && respuesta.get("Symbol").equals(simboloEnMayusculas);
        } catch (Exception e) {
            // Si la API falla o no encuentra el símbolo, retornamos falso
            return false;
        }
    }


    public Double obtenerPrecioAccion(String nombreAccion) {
        String url = API_URL + "?function=TIME_SERIES_DAILY&symbol=" + nombreAccion + "&apikey=" + API_KEY;
        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);

            // Extraer la serie temporal y los datos más recientes
            Map<String, Object> serieTemporal = (Map<String, Object>) respuesta.get("Time Series (Daily)");
            String ultimaActualizacion = ((Map<String, Object>) respuesta.get("Meta Data")).get("3. Last Refreshed").toString();
            Map<String, String> datosHoy = (Map<String, String>) serieTemporal.get(ultimaActualizacion);

            // Retornar el precio de cierre
            return Double.parseDouble(datosHoy.get("4. close"));
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API de Alpha Vantage: " + e.getMessage());
        }
    }
}
