package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class databaseConnector {
    private final SQLServerDataSource dataSource;

    public databaseConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("");
        dataSource.setDatabaseName("");
        dataSource.setPortNumber(1433);
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setTrustServerCertificate(true);
    }
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

}
