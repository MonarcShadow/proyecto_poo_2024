/**
 * Controlador para la interfaz de la tienda.
 * Gestiona la lógica de la aplicación relacionada con la tienda, jugadores e inventario.
 */
package proyecto.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controlador para la interfaz de la tienda.
 * Gestiona las interacciones entre la interfaz gráfica y la lógica de la aplicación.
 */
public class ShopController {

    // Declaraciones de los controles de la interfaz gráfica
    @FXML
    private Button actualizar_precio_custom;
    @FXML
    private Button buton_actualizarconsumo_inventario;
    @FXML
    private Button buton_agregar_dinero;
    @FXML
    private Button buton_agregar_nuevo_jugador;
    @FXML
    private Button buton_revisar_inventario;
    @FXML
    private Button comprar_button;
    @FXML
    private Button vender_button;
    @FXML
    private ComboBox<?> elejir_jugador_compra_venta;
    @FXML
    private ComboBox<?> filtro_fe;
    @FXML
    private ComboBox<?> filtro_stocklimite;
    @FXML
    private ComboBox<?> filtro_sub_tipo_item;
    @FXML
    private ComboBox<?> filtro_tienda;
    @FXML
    private ComboBox<?> filtro_tipo_item;
    @FXML
    private ComboBox<?> filtro_lugar;
    @FXML
    private TextField input_cantidad_item_consumido;
    @FXML
    private TextField id_item_compra;
    @FXML
    private TextField cantidad_item_compra;
    @FXML
    private TextField input_dinero_nuevo_jugador;
    @FXML
    private TextField input_id_item_precio_custom;
    @FXML
    private TextField input_item_consumido;
    @FXML
    private ComboBox<?> input_jugador_dinero;
    @FXML
    private TextField input_monto_dinero;
    @FXML
    private TextField input_monto_precio_custom1;
    @FXML
    private ComboBox<?> input_nombre_jugador_inventario;
    @FXML
    private TextField input_nombre_nuevo_jugador;
    @FXML
    private ComboBox<?> input_trabajo_nuevo_jugador;
    @FXML
    private TableColumn<Inventario, Integer> tabla_cantidad_item_inventario;
    @FXML
    private TableColumn<Inventario, Integer> tabla_id_item_inventario;
    @FXML
    private TableColumn<Inventario, String> tabla_nombre_item_inventario;
    @FXML
    private TableView<Inventario> tabla_inventario;
    @FXML
    private TableView<Jugador> tabla_jugadores;
    @FXML
    private TableColumn<Jugador, Integer> tabla_id_jugador;
    @FXML
    private TableColumn<Jugador, String> tabla_nombre_jugador;
    @FXML
    private TableColumn<Jugador, String> tabla_trabajo_jugador;
    @FXML
    private TableColumn<Jugador, Integer> tabla_dinero_jugador;
    @FXML
    private TableView<Item> tabla_tienda;
    @FXML
    private TableColumn<Item, Integer> tabla_tienda_id_item;
    @FXML
    private TableColumn<Item, String> tabla_tienda_nombre_item;
    @FXML
    private TableColumn<Item, Integer> tabla_tienda_costo_bajo;
    @FXML
    private TableColumn<Item, Integer> tabla_tienda_costo_normal;
    @FXML
    private TableColumn<Item, Integer> tabla_tienda_costo_alto;
    @FXML
    private TableColumn<Item, Integer> tabla_tienda_custom;
    @FXML
    private ComboBox<?> tipo_moneda;

    // Listas observables para almacenar datos de la tienda
    private ObservableList<Item> lista_items_tienda_tabla;
    private ObservableList<Jugador> lista_jugadores_partida;
    private ObservableList<Inventario> lista_items_inventario;
    /**
     * Actualiza la cantidad consumida de un ítem en el inventario de un jugador.
     * Actualiza la tabla de inventario con los cambios.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void actualizar_consumo_inventario(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.actualizar_inventario(cn_usuario, input_nombre_jugador_inventario.getValue().toString(),
                    Integer.parseInt(input_item_consumido.getText()),
                    Integer.parseInt(input_cantidad_item_consumido.getText()));
            Shop.lista_items_inventario_jugador_actual.clear();
            lista_items_inventario.clear();
            usuario.listar_inventario_jugador(cn_usuario, input_nombre_jugador_inventario.getValue().toString());
            lista_items_inventario.addAll(Shop.lista_items_inventario_jugador_actual);
            tabla_inventario.setItems(lista_items_inventario);
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e + "\nerror al actualizar consumo inventario");
        }
    }

    /**
     * Reinicia todos los filtros de la tienda a sus valores predeterminados y lista todos los ítems.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al listar los ítems
     */
    @FXML
    void resetear_filtros(ActionEvent event) throws SQLException {
        filtro_fe.setValue(null);
        filtro_stocklimite.setValue(null);
        filtro_sub_tipo_item.setValue(null);
        filtro_tienda.setValue(null);
        filtro_tipo_item.setValue(null);
        filtro_lugar.setValue(null);
        Shop.items.listar_items(Shop.cn_items);
        definir_tabla_tienda();
        lista_items_tienda_tabla.addAll(Shop.lista_items_tienda);
        this.tabla_tienda.setItems(lista_items_tienda_tabla);
    }

    /**
     * Vende la cantidad especificada de un ítem seleccionado por un jugador.
     * Actualiza la tabla de jugadores con los cambios.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void vender_item_seleccionado(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            int id_item = Integer.parseInt(id_item_compra.getText());
            int cantidad = Integer.parseInt(cantidad_item_compra.getText());
            String precio_a_usar = tipo_moneda.getValue().toString();
            usuario.comprar_item(cn_usuario, elejir_jugador_compra_venta.getValue().toString(), id_item, -cantidad, precio_a_usar);
            Shop.lista_jugadores_partida.clear();
            usuario.continuar_partida(cn_usuario, Shop.usuario_actual, Shop.nombre_partida_actual);
            cn_usuario.close();
            lista_jugadores_partida.clear();
            lista_jugadores_partida.addAll(Shop.lista_jugadores_partida);
            tabla_jugadores.setItems(lista_jugadores_partida);
        } catch (Exception e) {
            System.out.println(e + "\nerror al comprar item seleccionado");
        }
    }

    /**
     * Actualiza el inventario de un jugador en la tabla de la interfaz gráfica.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void actualizar_inventario_en_tabla(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            Shop.lista_items_inventario_jugador_actual.clear();
            lista_items_inventario.clear();
            usuario.listar_inventario_jugador(cn_usuario, input_nombre_jugador_inventario.getValue().toString());
            lista_items_inventario.addAll(Shop.lista_items_inventario_jugador_actual);
            tabla_inventario.setItems(lista_items_inventario);
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e + "\nerror al actualizar inventario en tabla");
        }
    }


    /**
     * Actualiza el precio personalizado de un ítem en la tienda.
     * Actualiza la tabla de la tienda con los cambios.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al actualizar el precio personalizado del ítem
     */
    @FXML
    void actualizar_precio_Custom(ActionEvent event) throws SQLException {
        try {
            int id_item = Integer.parseInt(input_id_item_precio_custom.getText());
            int nuevo_precio = Integer.parseInt(input_monto_precio_custom1.getText());
            Shop.items.actualizar_precio_custom(Shop.cn_items, id_item, nuevo_precio);
            Shop.lista_items_tienda.clear();
            lista_items_tienda_tabla.clear();
            Shop.items.listar_items(Shop.cn_items);
            lista_items_tienda_tabla.addAll(Shop.lista_items_tienda);
            this.tabla_tienda.setItems(lista_items_tienda_tabla);
        } catch (Exception e) {
            System.out.println(e + "\nerror al actualizar precio custom");
        }
    }

    /**
     * Agrega una cantidad específica de dinero al jugador seleccionado.
     * Actualiza la tabla de jugadores con los cambios.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void agregar_dinero_al_jugador_seleccionado(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.agregar_dinero_a_jugador(cn_usuario, input_jugador_dinero.getValue().toString(), Integer.parseInt(input_monto_dinero.getText()));
            Shop.lista_jugadores_partida.clear();
            usuario.continuar_partida(cn_usuario, Shop.usuario_actual, Shop.nombre_partida_actual);
            cn_usuario.close();
            lista_jugadores_partida.clear();
            lista_jugadores_partida.addAll(Shop.lista_jugadores_partida);
            tabla_jugadores.setItems(lista_jugadores_partida);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Agrega un nuevo jugador a la partida con el nombre, trabajo y dinero especificados.
     * Actualiza las listas desplegables relacionadas con jugadores.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void agregar_nuevo_jugador_a_partida(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.guardar_nuevo_jugador(cn_usuario, input_nombre_nuevo_jugador.getText(), input_trabajo_nuevo_jugador.getValue().toString(), Integer.parseInt(input_dinero_nuevo_jugador.getText()));
            usuario.agregar_a_combobox_ultimo_jugador(cn_usuario, input_jugador_dinero);
            usuario.agregar_a_combobox_ultimo_jugador(cn_usuario, input_nombre_jugador_inventario);
            usuario.agregar_a_combobox_ultimo_jugador(cn_usuario, elejir_jugador_compra_venta);
            cn_usuario.close();
            lista_jugadores_partida.add(Shop.lista_jugadores_partida.get(Shop.lista_jugadores_partida.size() - 1));
            tabla_jugadores.setItems(lista_jugadores_partida);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Realiza la compra del ítem especificado por el jugador seleccionado.
     * Actualiza la tabla de jugadores con los cambios.
     *
     * @param event evento de acción que desencadena el método
     */
    @FXML
    void comprar_item_seleccionado(ActionEvent event) {
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            int id_item = Integer.parseInt(id_item_compra.getText());
            int cantidad = Integer.parseInt(cantidad_item_compra.getText());
            String precio_a_usar = tipo_moneda.getValue().toString();
            String valor = elejir_jugador_compra_venta.getValue() != null ? elejir_jugador_compra_venta.getValue().toString() : "";
            usuario.comprar_item(cn_usuario, valor, id_item, cantidad, precio_a_usar);
            Shop.lista_jugadores_partida.clear();
            usuario.continuar_partida(cn_usuario, Shop.usuario_actual, Shop.nombre_partida_actual);
            lista_jugadores_partida.clear();
            lista_jugadores_partida.addAll(Shop.lista_jugadores_partida);
            tabla_jugadores.setItems(lista_jugadores_partida);
            cn_usuario.close();
        } catch (Exception e) {
            System.out.println(e + "\nerror al comprar item seleccionado");
        }
    }
    /**
     * Filtra y muestra los items de solo un tipo específico en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por tipo
     */
    @FXML
    void mostrar_items_de_solo_un_tipo(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Filtra y muestra los items de un subtipo específico en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por subtipo
     */
    @FXML
    void mostrar_solo_items_de_un_subtipo(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Filtra y muestra los items de una tienda específica en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por tienda
     */
    @FXML
    void mostrar_solo_items_de_una_tienda(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Filtra y muestra solo los items disponibles en una ubicación específica en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por ubicación
     */
    @FXML
    void muestra_solo_items_disponibles_en_ubicacion_especifica(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Filtra y muestra solo los items que requieren fe en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por requisitos de fe
     */
    @FXML
    void muestra_solo_items_que_requieren_fe(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Filtra y muestra los items que están en stock límite en la tabla de la tienda.
     *
     * @param event evento de acción que desencadena el método
     * @throws SQLException si ocurre un error al filtrar los items por stock límite
     */
    @FXML
    void stock_limite(ActionEvent event) throws SQLException {
        filtrar_items();
    }

    /**
     * Inicia la tienda, listando items, configurando las tablas y llenando los ComboBoxes necesarios.
     *
     * @throws SQLException si ocurre un error durante la inicialización de la tienda desde la base de datos
     */
    public void Shop_start() throws SQLException {
        Shop.items.listar_items(Shop.cn_items);
        definir_tabla_tienda();
        lista_items_tienda_tabla.addAll(Shop.lista_items_tienda);
        this.tabla_tienda.setItems(lista_items_tienda_tabla);
        definir_tabla_jugadores();
        lista_jugadores_partida.addAll(Shop.lista_jugadores_partida);
        this.tabla_jugadores.setItems(lista_jugadores_partida);
        definir_tabla_inventario();
        Connection cn_usuario = null;
        Conexion usuario = null;
        try {
            usuario = new Conexion(Shop.Path_save_usurious);
            cn_usuario = usuario.conectar();
            usuario.llenar_combobox(cn_usuario, input_trabajo_nuevo_jugador, "trabajos", "nombre_trabajo");
            usuario.llenar_combobox(cn_usuario, input_jugador_dinero, "jugadores", "nombre_jugador");
            usuario.llenar_combobox(cn_usuario, input_nombre_jugador_inventario, "jugadores", "nombre_jugador");
            usuario.llenar_combobox(cn_usuario, elejir_jugador_compra_venta, "jugadores", "nombre_jugador");
            cn_usuario.close();
            Shop.items.llenar_combobox(Shop.cn_items, filtro_tienda, "clases_item", "nombre_clase");
            Shop.items.llenar_combobox(Shop.cn_items, filtro_tipo_item, "tipos_item", "nombre_tipo");
            Shop.items.llenar_combobox(Shop.cn_items, filtro_sub_tipo_item, "subtipo_item", "nombre_subtipo");
            String[] fe = {"Si", "No"};
            llenar_comboboxsindb(filtro_fe, fe);
            String[] lugar = {"Rural", "Urbano", "Premium"};
            llenar_comboboxsindb(filtro_lugar, lugar);
            String[] monedas = {"Costo Bajo", "Costo Normal", "Costo Alto", "Costo Custom"};
            llenar_comboboxsindb(tipo_moneda, monedas);
            llenar_comboboxsindb(filtro_stocklimite, fe);
            cn_usuario.close();
        } catch (SQLException e) {
            System.out.println(e + "\nError al iniciar la tienda.");
        }
    }

    /**
     * Define la estructura y las propiedades de la tabla de inventario.
     */
    private void definir_tabla_inventario() {
        lista_items_inventario = FXCollections.observableArrayList();
        this.tabla_id_item_inventario.setCellValueFactory(new PropertyValueFactory<>("Id_item"));
        this.tabla_nombre_item_inventario.setCellValueFactory(new PropertyValueFactory<>("Nombre_item"));
        this.tabla_cantidad_item_inventario.setCellValueFactory(new PropertyValueFactory<>("Cantidad_item"));
    }

    /**
     * Define la estructura y las propiedades de la tabla de jugadores.
     */
    private void definir_tabla_jugadores() {
        lista_jugadores_partida = FXCollections.observableArrayList();
        this.tabla_id_jugador.setCellValueFactory(new PropertyValueFactory<>("id_jugador"));
        this.tabla_nombre_jugador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tabla_trabajo_jugador.setCellValueFactory(new PropertyValueFactory<>("trabajo"));
        this.tabla_dinero_jugador.setCellValueFactory(new PropertyValueFactory<>("dinero_total"));
    }

    /**
     * Llena un ComboBox con los elementos de un arreglo proporcionado.
     *
     * @param comboBox el ComboBox a llenar
     * @param array    el arreglo de elementos a agregar al ComboBox
     */
    public void llenar_comboboxsindb(ComboBox comboBox, String[] array) {
        for (int i = 0; i < array.length; i++) {
            comboBox.getItems().add(array[i]);
        }
    }

    /**
     * Define la estructura y las propiedades de la tabla de la tienda.
     */
    public void definir_tabla_tienda() {
        lista_items_tienda_tabla = FXCollections.observableArrayList();
        this.tabla_tienda_id_item.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tabla_tienda_nombre_item.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tabla_tienda_custom.setCellValueFactory(new PropertyValueFactory<>("custom"));
        this.tabla_tienda_costo_bajo.setCellValueFactory(new PropertyValueFactory<>("PrecioBajo"));
        this.tabla_tienda_costo_normal.setCellValueFactory(new PropertyValueFactory<>("PrecioNormal"));
        this.tabla_tienda_costo_alto.setCellValueFactory(new PropertyValueFactory<>("PrecioAlto"));
    }

    /**
     * Filtra los items de la tienda según los criterios seleccionados en los ComboBoxes de filtro.
     *
     * @throws SQLException si ocurre un error al listar los items con el filtro desde la base de datos
     */
    public void filtrar_items() throws SQLException {
        Shop.lista_items_tienda.clear();
        lista_items_tienda_tabla.clear();
        String tienda = " ";
        String tipo_item = " ";
        String sub_tipo_item = " ";
        String lugar = " ";
        String fe = " ";
        String limite = " ";
        try {
            tienda = this.filtro_tienda.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            tipo_item = this.filtro_tipo_item.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            sub_tipo_item = this.filtro_sub_tipo_item.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            lugar = this.filtro_lugar.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            fe = this.filtro_fe.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            limite = this.filtro_stocklimite.getValue().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Shop.items.listar_items_con_filtro(Shop.cn_items, tienda, tipo_item, sub_tipo_item, lugar, fe, limite);
            lista_items_tienda_tabla.addAll(Shop.lista_items_tienda);
            this.tabla_tienda.setItems(lista_items_tienda_tabla);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
