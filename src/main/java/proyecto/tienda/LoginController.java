package proyecto.tienda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;

public class LoginController extends Component {

    @FXML
    private Label buttonexit;

    @FXML
    private Button choose_file;

    @FXML
    private Button continue_sesion;

    @FXML
    private TextField input_new_user;

    @FXML
    private TextField input_user;

    @FXML
    private Button nuevo_usuario_se_une;

    @FXML
    private Label path_choosefile_user;

    @FXML
    private Label ver_usuarios;

    @FXML
    private Button start_sesion;

    @FXML
    private TextField input_name_partida;

    @FXML
    private Label ver_partidas;

    @FXML
    void agregar_usuario_a_db(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.guardar_nuevo_usuario(cn_usuario, input_new_user.getText());
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void change_filechooser(ActionEvent event) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.db", "db");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Elige otro archivo de datos de partida");
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fileChooser.getSelectedFile();
            Shop.Path_save_usurious = fichero.getAbsolutePath();
            path_choosefile_user.setText(Shop.Path_save_usurious);
        }
    }

    @FXML
    void continuar_una_sesion_anterior(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try{
            usuario= new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.continuar_partida(cn_usuario, input_user.getText(), input_name_partida.getText());
            cn_usuario.close();
            Shop.usuario_actual = input_user.getText();
            Shop.nombre_partida_actual = input_name_partida.getText();
        }catch (Exception e) {
            System.out.println(e);
        }
        nextwindow();
    }

    @FXML
    void iniciar_nueva_sesion(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try{
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.iniciar_nueva_partida(cn_usuario, input_name_partida.getText(), input_user.getText());
            cn_usuario.close();
            Shop.usuario_actual = input_user.getText();
            Shop.nombre_partida_actual = input_name_partida.getText();
        } catch (Exception e) {
            System.out.println(e);
        }
        nextwindow();
    }
    @FXML
    void Ver_usuarios_de_db(MouseEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.listar(cn_usuario, "nombre_usuario", "nombre_usuario");
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    void ver_partidas_del_usuario(MouseEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try{
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.listar_partidas_usuario(cn_usuario, input_user.getText());
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    void close_window(MouseEvent event) {
        System.exit(0);
    }
    void close_window1(){
        path_choosefile_user.setText(Shop.Path_save_usurious);
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void nextwindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shop-view.fxml"));
            Parent root = fxmlLoader.load();
            ShopController Controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            Controller.Shop_start();
            //descomentar siguientes 2 lineas si se compilara en un jar
            //javafx.scene.image.Image icon = new Image("proyecto/tienda/imagenes/icono.jpg");
            //stage.getIcons().add(icon);
            stage.show();
            Stage myStage = (Stage) this.path_choosefile_user.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
