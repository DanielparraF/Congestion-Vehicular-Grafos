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

        System.out.println("Ingrese los nombres de las estaciones de transporte urbano separadas por espacios (ejemplo: A B C D)");
        String input = sc.nextLine().trim();
        for (String vertexName : input.split("\\s+")) {

            Vertex vertex = new Vertex(vertexName);
            graph.addVertex(vertex);
            vertexMap.put(vertexName, vertex);

        }

        System.out.println("Ingrese las rutas en el formato: estacion-inicio, estacion-fin , tiempo-de-viaje, (por ejemplo A B 10)");
        System.out.println("Escriba fin cuando haya terminado.");

        while (true) {

            input = sc.nextLine().trim();
            if(input.equalsIgnoreCase("fin")){
            break;
        }
            String[] parts = input.split("\\s+");
            String sourceName= parts[0];
            String targetName= parts[1];
            double weight = Double.parseDouble(parts[2]);
            Vertex source = vertexMap.get(sourceName);
            Vertex target = vertexMap.get(targetName);
            DefaultWeightedEdge edge=graph.addEdge(source,target);
            graph.setEdgeWeight(edge,weight);

        }

        System.out.println("ingrese el nombre de la estacion de inicio; ");
        String startName=sc.nextLine().trim();
        System.out.println("ingrese el nombre de la estacion de destino ; ");
        String endName=sc.nextLine().trim();

        Vertex startVertex= vertexMap.get(startName);
        Vertex endVertex= vertexMap.get(endName);

        DijkstraShortestPath<Vertex,DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        List<Vertex> shortestPath = dijkstra.getPath(startVertex,endVertex).getVertexList();
        double shortestDistance = dijkstra.getPathWeight(startVertex,endVertex);

        System.out.println("la ruta mas corta de....: "+ startName +" a "+endName +" es : "+shortestPath);
        System.out.println("la distancia mas corta de....: "+startName +" a "+endName+" es "+shortestDistance);

    }
}