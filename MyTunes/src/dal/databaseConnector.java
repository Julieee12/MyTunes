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
            dataSource.setDatabaseName("MyTunes-groupE1");
            dataSource.setUser("CSe2023b_e_18");
            dataSource.setPassword("CSe2023bE18#23");
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
