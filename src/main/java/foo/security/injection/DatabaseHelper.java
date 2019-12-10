package foo.security.injection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DatabaseHelper {

  private static Connection conn;

  private DatabaseHelper() {
  }

  public static java.sql.Connection getJDBCConnection() {
    if (conn == null) {
      try {
        InitialContext ctx = new InitialContext();
        DataSource datasource = (DataSource) ctx.lookup("java:comp/env/jdbc/BenchmarkDB");
        conn = datasource.getConnection();
        conn.setAutoCommit(false);
      } catch (SQLException | NamingException e) {
        System.out.println("Problem with getConnection().");
      }
    }
    return conn;
  }

  public static PersistenceManager getJDOConnection() {
    Properties properties = new Properties();
    properties.put("javax.jdo.PersistenceManagerFactoryClass", "XXX");
    properties.put("javax.jdo.option.ConnectionURL", "XXX");
    properties.put("javax.jdo.option.ConnectionUserName", "XXX");
    properties.put("javax.jdo.option.ConnectionPassword", "XXX");

    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(properties);
    return pmf.getPersistenceManager();
  }
}
