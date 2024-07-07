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

public class Shop extends Application {
    public static String Path_save_usurious;
    public static ArrayList<Jugador> lista_jugadores_partida = new ArrayList<>();
    public static ArrayList<Item> lista_items_tienda = new ArrayList<>();
    public static ArrayList<Inventario> lista_items_inventario_jugador_actual = new ArrayList<>();
    public static Connection cn_items = null;
    public static Conexion items = null;
    public static String usuario_actual;
    public static String nombre_partida_actual;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inicio-view.fxml")));
        stage.initStyle(StageStyle.UNDECORATED);
        //descomentar siguientes 2 lineas si se compilara en un jar
        //Image icon = new Image("proyecto/tienda/imagenes/icono.jpg");
        //stage.getIcons().add(icon);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        try {
            items = new Conexion("items.db");
            cn_items = items.conectar();
        } catch (Exception e){
            System.out.println(e);
        }
        launch();
    }
}