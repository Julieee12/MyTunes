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
        dataSource.setServerName("10.176.111.34");
        dataSource.setDatabaseName("MyTunes-groupE1");
        dataSource.setPortNumber(1433);
        dataSource.setUser("CSe2023b_e_18");
        dataSource.setPassword("CSe2023bE18#23");
        dataSource.setTrustServerCertificate(true);
    }
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

}
