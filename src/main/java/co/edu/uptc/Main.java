package co.edu.uptc;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main extends Application {
    private final Graph<Vertex, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    private final Map<String, Vertex> vertexMap = new HashMap<>();
    private final TextArea outputArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rutas Urbanas - Calculadora de Rutas Cortas");

        // Layout principal
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Entrada de estaciones
        TextField stationInput = new TextField();
        stationInput.setPromptText("Ingrese nombres de estaciones separadas por espacios (A B C)");

        Button addStationsButton = new Button("Añadir Estaciones");
        addStationsButton.setOnAction(e -> handleAddStations(stationInput.getText()));

        HBox stationsBox = new HBox(10, stationInput, addStationsButton);

        // Entrada de rutas
        TextField routeSourceInput = new TextField();
        routeSourceInput.setPromptText("Ruta origen (ej. A)");

        TextField routeTargetInput = new TextField();
        routeTargetInput.setPromptText("Ruta destino (ej. B)");

        TextField routeTimeInput = new TextField();
        routeTimeInput.setPromptText("Tiempo (ej. 10)");

        Button addRouteButton = new Button("Añadir Ruta");
        addRouteButton.setOnAction(e -> handleAddRoute(routeSourceInput.getText(), routeTargetInput.getText(), routeTimeInput.getText()));

        HBox routesBox = new HBox(10, routeSourceInput, routeTargetInput, routeTimeInput, addRouteButton);

        // Entrada de estaciones de inicio y destino
        TextField startStationInput = new TextField();
        startStationInput.setPromptText("Estación de inicio");

        TextField endStationInput = new TextField();
        endStationInput.setPromptText("Estación de destino");

        Button calculateButton = new Button("Calcular Ruta Más Corta");
        calculateButton.setOnAction(e -> handleCalculateShortestPath(startStationInput.getText(), endStationInput.getText()));

        HBox calculationBox = new HBox(10, startStationInput, endStationInput, calculateButton);

        // Área de salida
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Agregar componentes al layout principal
        root.getChildren().addAll(new Label("Estaciones"), stationsBox, new Label("Rutas"), routesBox,
                new Label("Cálculo de Rutas"), calculationBox, new Label("Resultados"), outputArea);

        // Configurar escena
        Scene scene = new Scene(root, 700, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddStations(String input) {
        if (!input.matches("([A-Z](\\s[A-Z])*)")) {
            showError("Entrada inválida. Use solo letras mayúsculas separadas por espacios.");
            return;
        }

        for (String vertexName : input.split("\\s+")) {
            if (vertexMap.containsKey(vertexName)) {
                showError("Estación duplicada: " + vertexName);
                return; // Detenemos la ejecución si hay una estación duplicada
            }
        }

        StringBuilder addedStations = new StringBuilder();
        for (String vertexName : input.split("\\s+")) {
            Vertex vertex = new Vertex(vertexName);
            graph.addVertex(vertex);
            vertexMap.put(vertexName, vertex);
            addedStations.append(vertexName).append(" ");
        }

        if (addedStations.length() > 0) {
            outputArea.appendText("Estaciones añadidas: " + addedStations + "\n");
        }
    }

    private void handleAddRoute(String source, String target, String time) {
        if (!source.matches("[A-Z]") || !target.matches("[A-Z]")) {
            showError("Ruta origen y destino deben ser letras mayúsculas únicas.");
            return;
        }

        if (!vertexMap.containsKey(source) || !vertexMap.containsKey(target)) {
            showError("Ambas estaciones deben existir antes de añadir una ruta.");
            return;
        }

        double weight;
        try {
            weight = Double.parseDouble(time);
            if (weight <= 0) {
                showError("El tiempo debe ser un número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("El tiempo debe ser un número válido.");
            return;
        }

        Vertex sourceVertex = vertexMap.get(source);
        Vertex targetVertex = vertexMap.get(target);
        DefaultWeightedEdge edge = graph.addEdge(sourceVertex, targetVertex);
        if (edge != null) {
            graph.setEdgeWeight(edge, weight);
            outputArea.appendText("Ruta añadida: " + source + " -> " + target + " [" + weight + "]\n");
        } else {
            showError("La ruta ya existe o es inválida.");
        }
    }

    private void handleCalculateShortestPath(String startName, String endName) {
        Vertex startVertex = vertexMap.get(startName);
        Vertex endVertex = vertexMap.get(endName);

        if (startVertex == null || endVertex == null) {
            showError("Las estaciones deben existir en el grafo.");
            return;
        }

        DijkstraShortestPath<Vertex, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        var path = dijkstra.getPath(startVertex, endVertex);

        if (path != null) {
            List<Vertex> shortestPath = path.getVertexList();
            double shortestDistance = path.getWeight();

            outputArea.appendText("La ruta más corta de " + startName + " a " + endName + " es: " + shortestPath + "\n");
            outputArea.appendText("Distancia más corta: " + shortestDistance + "\n");
        } else {
            outputArea.appendText("No existe una ruta entre " + startName + " y " + endName + "\n");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}