//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/recipe_db";
//    private static final String USER = "root"; // your MySQL username
//    private static final String PASS = "99952"; // your MySQL password
//
//    public static Connection getConnection(String i, String dbPassword) {
//        Connection conn = null;
//        try {
//            // Load MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Connect to DB
//            conn = DriverManager.getConnection(URL, USER, PASS);
//            System.out.println("✅ Connected to MySQL successfully!");
//        } catch (ClassNotFoundException e) {
//            System.out.println("❌ JDBC Driver not found!");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("❌ Database connection failed!");
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//    public static void main(String[] args) {
//                getConnection("root", "99952");
//            
//        
//    }
//}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/recipe_db";
    private static final String USER = "root";
    private static final String PASS = "99952";

    @SuppressWarnings("unused")
    public static Connection getConnection(String i, String dbPassword) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to MySQL successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection("root", "99952");
    }
}