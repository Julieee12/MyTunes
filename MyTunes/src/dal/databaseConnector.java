package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class databaseConnector {



    public class databaseConncetor {
        private final SQLServerDataSource dataSource;

        public databaseConncetor() {
            dataSource = new SQLServerDataSource();
            dataSource.setServerName("10.176.111.31");
            dataSource.setDatabaseName("");
            dataSource.setUser("");
            dataSource.setPassword("");
            dataSource.setPortNumber(1433);
            dataSource.setTrustServerCertificate(true);

        }

        public Connection getConnection() throws SQLServerException {
            return dataSource.getConnection();
        }

        public static void main(String[] args) throws SQLException {/*
        databaseConnector databaseConnection = new databaseConnector();
            Connection connection = databaseConnection.;
            System.out.println("Yaaay!");
            connection.close();*/


        }
    }
}
