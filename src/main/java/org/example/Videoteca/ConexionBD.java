package org.example.Videoteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos en PostgreSQL.
 *
 * @author Raquel Lahera
 */

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "RaquelLM";
    private static final String PASSWORD = "Pass12345";

    /**
     * Establece y devuelve la conexión con la base de datos PostgreSQL.
     *
     * @return el objeto Connection que representa la conexión a la base de datos.
     * @throws SQLException si ocurre un error al intentar conectar con la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
