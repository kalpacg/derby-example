import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;

public class Test{

  public static void main(String[] args){

  try{
    String framework = "embedded";
    String protocol = "jdbc:derby:";
    String dbName = "derbyDB";
    ArrayList<Statement> statements = new ArrayList<Statement>();
    PreparedStatement psInsert;
    PreparedStatement psUpdate;
    Statement s;
    ResultSet rs = null;

	// View classpath
    String classpathStr = System.getProperty("java.class.path");
    System.out.println(classpathStr);

    Properties props = new Properties(); // connection properties
    props.put("user", "user1");
    props.put("password", "user1");

    Connection conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
    System.out.println("Connected to and created database " + dbName);
    conn.setAutoCommit(false);

    s = conn.createStatement();
    statements.add(s);
   
    // Create a table
    s.execute("create table location(num int, addr varchar(40))");
    System.out.println("Created table location");

    // Add some data
    psInsert = conn.prepareStatement("insert into location values (?, ?)");
    statements.add(psInsert);
    psInsert.setInt(1, 1956);
    psInsert.setString(2, "Webster St.");
    psInsert.executeUpdate();
    System.out.println("Inserted 1956 Webster");
    psInsert.setInt(1, 1910);
    psInsert.setString(2, "Union St.");
    psInsert.executeUpdate();
    System.out.println("Inserted 1910 Union");

    // Let's update some rows as well...
    // parameter 1 and 3 are num (int), parameter 2 is addr (varchar)
    psUpdate = conn.prepareStatement("update location set num=?, addr=? where num=?");
    statements.add(psUpdate);
    psUpdate.setInt(1, 180);
    psUpdate.setString(2, "Grand Ave.");
    psUpdate.setInt(3, 1956);
    psUpdate.executeUpdate();
    System.out.println("Updated 1956 Webster to 180 Grand");
    
    psUpdate.setInt(1, 300);
    psUpdate.setString(2, "Lakeshore Ave.");
    psUpdate.setInt(3, 180);
    psUpdate.executeUpdate();
    System.out.println("Updated 180 Grand to 300 Lakeshore");

    // Select data
    rs = s.executeQuery( "SELECT num, addr FROM location ORDER BY num");
    int number; // street number retrieved from the database
    boolean failure = false;

    if (!rs.next()){
      failure = true;
      System.out.println("No rows in ResultSet");
    }
    if ((number = rs.getInt(1)) != 300){
      failure = true;
      System.out.println( "Wrong row returned, expected num=300, got " + number);
    }
    if (!rs.next()) {
      failure = true;
      System.out.println("Too few rows");
    }
    if ((number = rs.getInt(1)) != 1910) {
      failure = true;
      System.out.println( "Wrong row returned, expected num=1910, got " + number);
    }
    if (rs.next()) {
      failure = true;
      System.out.println("Too many rows");
    }
    if (!failure) {
      System.out.println("Verified the rows");
    }

    while (rs.next()){
      System.out.println(rs.getString(1));
	}
 
   	conn.commit();
    System.out.println("Committed the transaction");

  } catch (SQLException e){
    System.out.println("Error");
    while (e != null) {
      System.err.println("\n----- SQLException -----");
      System.err.println("  SQL State:  " + e.getSQLState());
      System.err.println("  Error Code: " + e.getErrorCode());
      System.err.println("  Message:    " + e.getMessage());
      // for stack traces, refer to derby.log or uncomment this:
      //e.printStackTrace(System.err);
      e = e.getNextException();
      }
}
  }
}
