package proyecto.tienda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase principal que inicia la aplicación de la tienda.
 */
public class Shop extends Application {

    /**
     * Ruta para guardar los archivos de usuarios.
     */
    public static String Path_save_usurious;

    /**
     * Lista de jugadores en la partida actual.
     */
    public static ArrayList<Jugador> lista_jugadores_partida = new ArrayList<>();

    /**
     * Lista de items disponibles en la tienda.
     */
    public static ArrayList<Item> lista_items_tienda = new ArrayList<>();

    /**
     * Lista de items en el inventario del jugador actual.
     */
    public static ArrayList<Inventario> lista_items_inventario_jugador_actual = new ArrayList<>();

    /**
     * Conexión para la gestión de items.
     */
    public static Connection cn_items = null;

    /**
     * Instancia de la conexión de items.
     */
    public static Conexion items = null;

    /**
     * Usuario actualmente logueado.
     */
    public static String usuario_actual;

    /**
     * Nombre de la partida actual.
     */
    public static String nombre_partida_actual;

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param stage el stage principal de la aplicación
     * @throws IOException si ocurre un error al cargar la interfaz inicial
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inicio-view.fxml")));
        stage.initStyle(StageStyle.UNDECORATED);
        // descomentar siguientes 2 líneas si se compilara en un JAR
        // Image icon = new Image("proyecto/tienda/imagenes/icono.jpg");
        // stage.getIcons().add(icon);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal de la aplicación que inicializa la conexión a la base de datos y lanza la aplicación JavaFX.
     *
     * @param args argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            items = new Conexion("items.db");
            cn_items = items.conectar();
        } catch (Exception e) {
            System.out.println(e);
        }
        launch();
    }
}
