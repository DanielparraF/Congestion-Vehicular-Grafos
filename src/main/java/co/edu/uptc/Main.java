package co.edu.uptc;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph<Vertex, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Map<String, Vertex> vertexMap = new HashMap<>();

        // Validar nombres de estaciones
        System.out.println("Ingrese los nombres de las estaciones de transporte urbano separadas por espacios (ejemplo: A B C D)");
        String input = sc.nextLine().trim();

// Validar que las estaciones cumplan con el formato (solo letras mayúsculas, separadas por espacios)
        while (!input.matches("([A-Z](\\s[A-Z])*)")) {
            System.out.println("Entrada inválida. Ingrese las estaciones en formato correcto: solo letras mayúsculas separadas por espacios (ejemplo: A B C D)");
            input = sc.nextLine().trim();
        }

// Crear los vértices a partir de la entrada validada
        for (String vertexName : input.split("\\s+")) {
            Vertex vertex = new Vertex(vertexName);
            graph.addVertex(vertex);
            vertexMap.put(vertexName, vertex);
        }


        // Validar rutas
        System.out.println("Ingrese las rutas en el formato: estacion-inicio estacion-fin tiempo-de-viaje (por ejemplo A B 10)");
        System.out.println("Escriba 'fin' cuando haya terminado.");
        while (true) {
            input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Formato incorrecto. Intente nuevamente con el formato: estacion-inicio estacion-fin tiempo-de-viaje");
                continue;
            }

            String sourceName = parts[0];
            String targetName = parts[1];
            double weight;
            try {
                weight = Double.parseDouble(parts[2]);
                if (weight <= 0) {
                    System.out.println("El tiempo de viaje debe ser un número positivo. Intente nuevamente.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("El tiempo de viaje debe ser un número válido. Intente nuevamente.");
                continue;
            }

            Vertex source = vertexMap.get(sourceName);
            Vertex target = vertexMap.get(targetName);
            if (source == null || target == null) {
                System.out.println("Las estaciones deben existir. Intente nuevamente.");
                continue;
            }

            DefaultWeightedEdge edge = graph.addEdge(source, target);
            graph.setEdgeWeight(edge, weight);
        }

        // Validar estación de inicio
        System.out.println("Ingrese el nombre de la estación de inicio:");
        String startName = sc.nextLine().trim();
        while (!vertexMap.containsKey(startName)) {
            System.out.println("La estación de inicio no existe. Intente nuevamente:");
            startName = sc.nextLine().trim();
        }

        // Validar estación de destino
        System.out.println("Ingrese el nombre de la estación de destino:");
        String endName = sc.nextLine().trim();
        while (!vertexMap.containsKey(endName)) {
            System.out.println("La estación de destino no existe. Intente nuevamente:");
            endName = sc.nextLine().trim();
        }

        // Calcular la ruta más corta
        Vertex startVertex = vertexMap.get(startName);
        Vertex endVertex = vertexMap.get(endName);

        DijkstraShortestPath<Vertex, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        var path = dijkstra.getPath(startVertex, endVertex);

        if (path != null) {
            List<Vertex> shortestPath = path.getVertexList();
            double shortestDistance = path.getWeight();

            System.out.println("La ruta más corta de " + startName + " a " + endName + " es: " + shortestPath);
            System.out.println("La distancia más corta de " + startName + " a " + endName + " es: " + shortestDistance);
        } else {
            System.out.println("No existe una ruta entre " + startName + " y " + endName);
        }
    }
}