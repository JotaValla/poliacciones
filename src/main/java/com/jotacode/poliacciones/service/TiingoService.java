package com.jotacode.poliacciones.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TiingoService {

    private final RestTemplate clienteHttp;

    @Value("${tiingo.api.token}")
    private String apiToken;

    private static final String BASE_URL = "https://api.tiingo.com/tiingo";

    public TiingoService(RestTemplate clienteHttp) {
        this.clienteHttp = clienteHttp;
    }

    public Double obtenerPrecioActual(String simbolo) {
        String url = BASE_URL + "/daily/" + simbolo + "/prices?token=" + apiToken;

        try {
            // Imprime la URL para depuración
            System.out.println("URL utilizada para obtener precio actual: " + url);

            ResponseEntity<List<Map<String, Object>>> response =
                    clienteHttp.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

            List<Map<String, Object>> precios = response.getBody();

            // Verificar si la respuesta tiene datos
            if (precios != null && !precios.isEmpty()) {
                Map<String, Object> ultimoPrecio = precios.get(precios.size() - 1); // Último precio
                System.out.println("Último precio obtenido: " + ultimoPrecio);
                return Double.parseDouble(ultimoPrecio.get("close").toString()); // Precio de cierre
            } else {
                System.out.println("No se encontraron precios para el símbolo: " + simbolo);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el precio actual de Tiingo: " + e.getMessage());
        }

        return null;
    }


    public boolean verificarSimbolo(String simbolo) {
        String url = BASE_URL + "/daily/" + simbolo + "?token=" + apiToken;
        try {
            Map<String, Object> respuesta = clienteHttp.getForObject(url, Map.class);
            return respuesta != null && respuesta.containsKey("ticker") && respuesta.get("ticker").equals(simbolo);
        } catch (Exception e) {
            System.out.println("Error al verificar el símbolo: " + e.getMessage());
            return false;
        }
    }

    public Double obtenerPrecioAccion(String simbolo) {
        String url = BASE_URL + "/daily/" + simbolo + "/prices?token=" + apiToken;

        try {
            Map<String, Object>[] respuesta = clienteHttp.getForObject(url, Map[].class);
            if (respuesta != null && respuesta.length > 0) {
                return (Double) respuesta[0].get("close");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al obtener el precio actual: " + e.getMessage());
            return null;
        }
    }

    public Double obtenerPrecioAccionPorFecha(String simbolo, LocalDate fecha) {
        String url = BASE_URL + "/daily/" + simbolo + "/prices?startDate=" + fecha + "&endDate=" + fecha + "&token=" + apiToken;

        try {
            Map<String, Object>[] respuesta = clienteHttp.getForObject(url, Map[].class);

            if (respuesta != null && respuesta.length > 0) {
                return Double.parseDouble(respuesta[0].get("close").toString());
            }

            System.out.println("No se encontraron datos para la fecha: " + fecha);
            return null; // Devuelve null si no hay datos disponibles
        } catch (Exception e) {
            System.out.println("Error al obtener precio por fecha: " + e.getMessage());
            throw new RuntimeException("Error al consultar Tiingo API: " + e.getMessage());
        }
    }

}
