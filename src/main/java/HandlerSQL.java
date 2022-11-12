import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


public class HandlerSQL {
	private Connection conn;
	private ResultSet resultado;

	public HandlerSQL() {

	}

	public void conection() {
		Properties prop = new Properties();

		try {
			prop.load(getClass().getResourceAsStream("/sql/configuracion.props"));
			String url = prop.getProperty("url");
			String username = prop.getProperty("user");
			String password = prop.getProperty("password");

			Class.forName(prop.getProperty("driver", "com.mysql.cj.jdbc.Driver"));

			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException | IOException e) {

		}
	}

	public ArrayList<String> getListaHoteles() {

		ArrayList<String> codigoHoteles = new ArrayList<>();
		try {

			Statement stmt = conn.createStatement();
			resultado = stmt.executeQuery("SELECT DISTINCT codHotel FROM habitaciones ORDER BY codHotel");
			while (resultado.next()) {
				String codHotel = resultado.getString("codHotel");
				codigoHoteles.add(codHotel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return codigoHoteles;

	}
	
	public ArrayList<String> getListaHabtiaciones(String codigoHotel) {

		ArrayList<String> habHoteles = new ArrayList<>();
		try {

			PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT numHabitacion FROM habitaciones WHERE codHotel = (?)");
			stmt.setString(1, codigoHotel);

			resultado = stmt.executeQuery();
			while(resultado.next()) {
				String numHabitacion = resultado.getString("numHabitacion");
				habHoteles.add(numHabitacion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return habHoteles;

	}

	public ArrayList<Reservas> getListaReservas(String codHotel) {
		ArrayList<Reservas> listaReservas = new ArrayList<>();

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT DISTINCT id, nombre, fechaInicio, fechaFin, numHabitacion FROM estancias WHERE codHotel = (?)");
			ps.setString(1, codHotel);
			resultado = ps.executeQuery();
			while (resultado.next()) {
				String nombre = resultado.getString("nombre");
				String fechaInicio = resultado.getString("fechaInicio");
				String fechaFin = resultado.getString("fechaFin");
				String numHabitacion = resultado.getString("numHabitacion");
				String id = resultado.getString("id");
			
				listaReservas.add(new Reservas(id, nombre, fechaInicio, fechaFin, numHabitacion));

			}
		} catch (SQLException e) {

		}
		return listaReservas;
	}
	
	public void insertReserva(Reservas reserva, String codHotel, String numHabitacion) {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO estancias (nombre, fechaInicio, fechaFin, numHabitacion, codHotel) VALUES (?,?,?,?,?)");
			ps.setString(1, reserva.getName());
			ps.setString(2, reserva.getDateArrival());
			ps.setString(3, reserva.getDateDeparture());
			ps.setString(4, numHabitacion);
			ps.setString(5, codHotel);

			ps.execute();


		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	public void deleteReserva(String id, String codHotel) {

		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM estancias WHERE id=(?) and codHotel=(?)");
			ps.setString(1, id);
			ps.setString(2, codHotel);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
