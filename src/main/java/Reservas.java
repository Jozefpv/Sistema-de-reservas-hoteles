import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservas {

	private StringProperty name = new SimpleStringProperty();
	private StringProperty dateArrival = new SimpleStringProperty();
	private StringProperty dateDeparture = new SimpleStringProperty();
	private StringProperty habitacion = new SimpleStringProperty();
	private StringProperty id = new SimpleStringProperty();

	public Reservas(String id, String name, String dateArrival, String dateDeparture, String habitacion) {
		setName(name);
		setDateArrival(dateArrival);
		setDateDeparture(dateDeparture);
		setHabitacion(habitacion);
		setId(id);
	}
	public Reservas(String name, String dateArrival, String dateDeparture, String habitacion) {
		setName(name);
		setDateArrival(dateArrival);
		setDateDeparture(dateDeparture);
		setHabitacion(habitacion);
		
	}
	

	public final String toString() {
		return name.get() + " " + dateArrival.get() + " " + dateDeparture.get() + "\n";
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty dateArrivalProperty() {
		return this.dateArrival;
	}

	public final String getDateArrival() {
		return this.dateArrivalProperty().get();
	}

	public final void setDateArrival(final String dateArrival) {
		this.dateArrivalProperty().set(dateArrival);
	}

	public final StringProperty dateDepartureProperty() {
		return this.dateDeparture;
	}

	public final String getDateDeparture() {
		return this.dateDepartureProperty().get();
	}

	public final void setDateDeparture(final String dateDeparture) {
		this.dateDepartureProperty().set(dateDeparture);
	}


	public final StringProperty habitacionProperty() {
		return this.habitacion;
	}
	


	public final String getHabitacion() {
		return this.habitacionProperty().get();
	}
	


	public final void setHabitacion(final String habitacion) {
		this.habitacionProperty().set(habitacion);
	}


	public final StringProperty idProperty() {
		return this.id;
	}
	


	public final String getId() {
		return this.idProperty().get();
	}
	


	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	
	

}
