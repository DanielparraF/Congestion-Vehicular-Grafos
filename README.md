# **Rutas Urbanas - Calculadora de Rutas Cortas**

## **Descripción del Proyecto**
Este proyecto consiste en una aplicación de escritorio desarrollada en Java utilizando JavaFX para la interfaz gráfica y JGraphT para la gestión de grafos. La aplicación permite:
1. **Registrar estaciones de transporte urbano:** Nombres únicos representados por letras mayúsculas.
2. **Definir rutas entre estaciones:** Incluye la distancia (tiempo) entre estaciones.
3. **Calcular la ruta más corta:** Utilizando el algoritmo de Dijkstra, se encuentra la ruta más eficiente entre dos estaciones.

La aplicación incluye validaciones para evitar errores comunes, como duplicados, formatos incorrectos o rutas inexistentes. La interfaz gráfica es amigable y permite visualizar los resultados de manera clara.

---

## **Integrantes**
- **Daniel Esteban Parra Flechas**
- **Maria Fernanda Angarita**

---

## **Requisitos**
Para ejecutar este proyecto, necesitas tener instalados los siguientes elementos en tu sistema:
1. **Java Development Kit (JDK)**: Versión 17 o superior.
2. **JavaFX SDK**: Para la interfaz gráfica.
3. **JGraphT Library**: Para el manejo de grafos.

---

## **Descarga de Librerías**

### **JavaFX SDK**
1. Descarga la versión correspondiente a tu sistema operativo desde la página oficial de JavaFX:
   - [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Descomprime el archivo descargado.
3. Configura la variable `VM options` en tu IDE:
   ```
   --module-path /ruta/a/javafx/lib --add-modules javafx.controls,javafx.fxml
   ```

### **JGraphT**
1. Descarga la librería desde Maven Central:
   - Página oficial: [https://jgrapht.org/](https://jgrapht.org/)
2. Si usas **Maven**, añade esta dependencia a tu archivo `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.jgrapht</groupId>
       <artifactId>jgrapht-core</artifactId>
       <version>1.5.1</version>
   </dependency>
   ```
3. Si no usas Maven, descarga el archivo `.jar` desde [Maven Central](https://search.maven.org/) y añádelo manualmente al proyecto.

---

## **Ejecución del Proyecto**
1. Clona o descarga este repositorio en tu máquina.
2. Asegúrate de configurar correctamente las librerías mencionadas.
3. Ejecuta la aplicación desde tu IDE preferido configurando las opciones de VM necesarias para JavaFX.
4. ¡Disfruta explorando las rutas urbanas!
