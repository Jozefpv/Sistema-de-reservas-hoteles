import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class Controller implements Initializable {

	// model
	private StringProperty codHotel = new SimpleStringProperty();
	private ListProperty<Reservas> reservasList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> codHoteles = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> listaHabitaciones = new SimpleListProperty<>(FXCollections.observableArrayList());



	public final StringProperty codHotelProperty() {
		return this.codHotel;
	}

	public final String getCodHotel() {
		return this.codHotelProperty().get();
	}

	public final void setCodHotel(final String codHotel) {
		this.codHotelProperty().set(codHotel);
	}

	public final ListProperty<Reservas> reservasListProperty() {
		return this.reservasList;
	}

	public final ObservableList<Reservas> getReservasList() {
		return this.reservasListProperty().get();
	}

	public final void setReservasList(final ObservableList<Reservas> reservasList) {
		this.reservasListProperty().set(reservasList);
	}

	private HandlerSQL handler = new HandlerSQL();

	// view
	@FXML
	private TableView<Reservas> reservasTable;

    @FXML
    private TableColumn<Reservas, String> arrivalTable;

	@FXML
	private TableColumn<Reservas, String> departureTable;

	@FXML
	private TableColumn<Reservas, String> nameTable;

	@FXML
	private ComboBox<String> comboHoteles;
	
	@FXML
    private ComboBox<String> comboHabitaciones;

	@FXML
	private Button deleteButton;

	@FXML
	private Button insertButton;

	@FXML
	private Button updateButton;

	@FXML
	private BorderPane view;

	public Controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		reservasTable.itemsProperty().bind(reservasList);
		codHotel.bind(comboHoteles.getSelectionModel().selectedItemProperty());
		

		codHotel.addListener((o, ov, nv) -> {
			reservasList.clear();
			reservasList.addAll(handler.getListaReservas(getCodHotel()));
			listaHabitaciones.setAll(handler.getListaHabtiaciones(getCodHotel()));
			comboHabitaciones.setItems(listaHabitaciones);
		});

		// load data

		handler.conection();
		codHoteles.setAll(handler.getListaHoteles());
		comboHoteles.setItems(codHoteles);
	

		nameTable.setCellValueFactory(v -> v.getValue().nameProperty());
		arrivalTable.setCellValueFactory(v -> v.getValue().dateArrivalProperty());
		departureTable.setCellValueFactory(v -> v.getValue().dateDepartureProperty());
		
		nameTable.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	public BorderPane getView() {
		return view;

	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		String id = reservasList.get(reservasTable.getSelectionModel().selectedIndexProperty().get()).getId();
		handler.deleteReserva(id, getCodHotel());
		reservasList.remove(reservasTable.getSelectionModel().selectedIndexProperty().get());
		
	}

	@FXML
	void onInsertAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(HotelesApp.primaryStage);
		dialog.setTitle("Usuario");
		dialog.setHeaderText("Añadir el nombre del usuario");
		dialog.setContentText("Nombre: ");
		Optional<String> name = dialog.showAndWait();
		
		TextInputDialog dialog1 = new TextInputDialog();
		dialog1.initOwner(HotelesApp.primaryStage);
		dialog1.setTitle("Fecha"); 
		dialog1.setHeaderText("Añadir fecha de inicio");
		dialog1.setContentText("Fecha inicio: ");
		Optional<String> fechaInicio = dialog1.showAndWait();
		
		TextInputDialog dialog2 = new TextInputDialog();
		dialog2.initOwner(HotelesApp.primaryStage);
		dialog2.setTitle("Fecha");
		dialog2.setHeaderText("Añadir fecha de salida");
		dialog2.setContentText("Fecha de salida: ");
		Optional<String> fechaFin = dialog2.showAndWait();
		
		
		ChoiceDialog<String> dialog4 = new ChoiceDialog<String>(null, listaHabitaciones);
		dialog4.initOwner(HotelesApp.primaryStage);
		dialog4.setTitle("Eleccion");
		dialog4.setHeaderText("Elige una habitacion");
		dialog4.setContentText("Habitacion: ");
		String hab = dialog4.showAndWait().orElse(null);
		
		reservasList.add(new Reservas(name.get(), fechaInicio.get(), fechaFin.get(), hab));
		handler.insertReserva(reservasList.get(reservasList.size()-1), getCodHotel(), hab);
		
	}

	@FXML
	void onUpdateAction(ActionEvent event) {

	}


}
