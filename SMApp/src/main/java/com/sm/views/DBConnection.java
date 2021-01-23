package com.sm.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.sm.Stocks;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnection {

    private static final String DB_NAME = "sample1.db";
    private static Connection connection = null;
    private static Statement stmt;
    private static ResultSet rs, rx;
    private static File dir;
    private static String dbUrl = "jdbc:sqlite:";
    //  Connections c;

    public static ObservableList<Transaction> GetPprice() {
//public  trasnactions1=        
//select id, trs_num,trs_price from tras where trs_type='P' AND id=?
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        try {
            connection = Connections.getConnection();
        } catch (IOException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (connection != null) {
                String s = "select t.id idi, c.company_name name,sum(t.trs_num) n, sum(t.trs_num*t.trs_price) p from companies c LEFT JOIN tras t ON c.id=t.id where t.trs_type='S' group by t.id;";
                stmt = connection.createStatement();
                rx = stmt.executeQuery(s);
                String sql = "select id, trs_num,trs_price from tras where trs_type='P' AND id=?;";

                while (rx.next()) {
                    Integer ci = rx.getInt("idi");
                    String cname = rx.getString("name");
                    Integer n = rx.getInt("n");
                    Integer n1 = n;
                    Double p = rx.getDouble("p");
                    Double stotal = 0.0, z = 0.0;
                    PreparedStatement prestatment = connection.prepareStatement(sql);
                    prestatment.setInt(1, ci);
                    rs = prestatment.executeQuery();
                    while (rs.next() && n1 > 0) {

                        Integer x = rs.getInt("id");
                        Integer y = rs.getInt("trs_num");

                        z = rs.getDouble("trs_price");
                        System.out.println(" x " + x + " trs num " + y + " trs price " + z);
                        if (n1 < y) {
                            stotal += n1*z;
                            System.out.println("1n1 " + n1 + "stotal" + stotal);
                        } else if (n1 >= y) {

                            stotal += (y * z);
                            System.out.println("2n1 " + n1 + "stotal " + stotal);
                            
                            // System.out.println("total is " + stotal + "trs_price is " + z + "remining " + n1);
                            // System.out.println("results are :" + x + " " + y + " " + n + " " + z);
                        }
                            n1 -= y;
                    }
                    
                    System.out.println("3n1 " + n1 + "stotal " + stotal);
                    list.add(new Transaction(ci, cname, n, p, stotal));
                    stotal = 0.0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        } finally {
            try {
                if (rx != null) {
                    rx.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
        }
        return list;

    }

    public static List<Stocks> Get_Stocks_list() throws SQLException, IOException {
        connection = Connections.getConnection();
        List<Stocks> list = new ArrayList<>();
        /*try {
        Class c = null;
        if (Platform.isAndroid()) {
        c = Class.forName("org.sqldroid.SQLDroidDriver");
        } else if (Platform.isIOS()) {
        c = Class.forName("SQLite.JDBCDriver");
        } else if (Platform.isDesktop()) {
        c = Class.forName("org.sqlite.JDBC");
        } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
        c = Class.forName("org.sqlite.JDBC");
        }
        } catch (ClassNotFoundException e) {
        System.out.println("Error class not found " + e);
        }
        
        //////////////////////
        try {
        dir = Services.get(StorageService.class)
        .map(s -> s.getPrivateStorage().get())
        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        dbUrl = dbUrl + db.getAbsolutePath();
        //    System.out.println("dbUrl"+dbUrl);
        } catch (IOException ex) {
        System.out.println("Error1" + ex);
        //return;
        }
        try {
            connection = DriverManager.getConnection(dbUrl);
            // System.out.println("Connection established: " + dbUrl);//"jdbc:sqlite:sample1.db"
        } catch (SQLException ex) {
            //   System.out.println("Error establishing connection " + ex.getSQLState());
            //return;
        }
         */
        try {
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30);

            System.out.println("Connecting...");
            //String sqlq="select * from companies ";
            //stmt.executeUpdate("select * from companies");
            //select c.company_name a,n.nsum b from companies c, (select p.id nid, p.sum_p -ifnull(s.sum_s,0) nsum from  (select id,count(id), ifnull(sum(trs_num),0) sum_p from tras where trs_type='P' group by id) p LEFT OUTER JOIN (select id,sum(trs_num) sum_s from tras where trs_type='S' group by id)s ON p.id=s.id) n where c.id=n.nid;
            //select companies.company_name  as a,tras.trs_num  as b from companies,tras where companies.id=tras.id; 
            rs = stmt.executeQuery("select c.company_name a,n.nsum b from companies c, (select p.id nid, p.sum_p -ifnull(s.sum_s,0) nsum from  (select id,count(id), ifnull(sum(trs_num),0) sum_p from tras where trs_type='P' group by id) p LEFT OUTER JOIN (select id,sum(trs_num) sum_s from tras where trs_type='S' group by id)s ON p.id=s.id) n where c.id=n.nid and b>0;");
            while (rs.next()) {

                String company_name = rs.getString("a");
                int id = rs.getInt("b");
                //System.out.print(company_name +"\t");
                //System.out.println(id);
                list.add(new Stocks(company_name, id));
            }

        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        }

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getSQLState());
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getSQLState());
        }
        return list;
    }

    public static List<Company> getCompanies() throws SQLException, IOException {
        Connection connection = Connections.getConnection();
        List<Company> clist = new ArrayList<>();
        /*
        try {
        Class c = null;
        if (Platform.isAndroid()) {
        c = Class.forName("org.sqldroid.SQLDroidDriver");
        } else if (Platform.isIOS()) {
        c = Class.forName("SQLite.JDBCDriver");
        } else if (Platform.isDesktop()) {
        c = Class.forName("org.sqlite.JDBC");
        } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
        c = Class.forName("org.sqlite.JDBC");
        }
        } catch (ClassNotFoundException e) {
        System.out.println("Error class not found " + e);
        }
        
        //////////////////////
        try {
        dir = Services.get(StorageService.class)
        .map(s -> s.getPrivateStorage().get())
        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        dbUrl = dbUrl + db.getAbsolutePath();
        } catch (IOException ex) {
        }
        
        try {
        connection = DriverManager.getConnection(dbUrl);
        System.out.println("Connection established: " + dbUrl);
        } catch (SQLException ex) {
        //   System.out.println("Error establishing connection " + ex.getSQLState());
        //return;
        }*/

        try {
            stmt = connection.createStatement();
            stmt.setQueryTimeout(30);

            System.out.println("Connecting...");
            //String sqlq="select * from companies ";
            //stmt.executeUpdate("select * from companies");

            rs = stmt.executeQuery("select * from companies");
            while (rs.next()) {

                int id = rs.getInt("id");
                String company_name = rs.getString("company_name");
                String notes = rs.getString("notes");
                clist.add(new Company(id, company_name));

            }

        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        }

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getSQLState());
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getSQLState());
        }
        return clist;
    }

    public static int CheckLogin(String u, String p) throws IOException {
        Connection connection = Connections.getConnection();
        /*try {
        Class c = null;
        if (Platform.isAndroid()) {
        c = Class.forName("org.sqldroid.SQLDroidDriver");
        } else if (Platform.isIOS()) {
        c = Class.forName("SQLite.JDBCDriver");
        } else if (Platform.isDesktop()) {
        c = Class.forName("org.sqlite.JDBC");
        } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
        c = Class.forName("org.sqlite.JDBC");
        }
        } catch (ClassNotFoundException e) {
        System.out.println("Error class not found " + e);
        }
        
        ///////////////
        if (Platform.isDesktop()) {
        dbUrl = dbUrl + ":resource:" + PrimaryPresenter.class.getResource("/database/" + DB_NAME).toExternalForm();
        } else {
        
        
        try {
        dir = Services.get(StorageService.class)
        .map(s -> s.getPrivateStorage().get())
        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        System.out.println("Copying database " + DB_NAME + " to private storage");
        DBUtils.copyDatabase("/database/", dir.getAbsolutePath(), DB_NAME);
        dbUrl = dbUrl + db.getAbsolutePath();
        } catch (IOException ex) {
        //("IO error " + ex.getMessage());
        
        }
        }*/
////////////////////// 
/*  try {
dir = Services.get(StorageService.class)
.map(s -> s.getPrivateStorage().get())
.orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
File db = new File(dir, DB_NAME);
dbUrl = dbUrl + db.getAbsolutePath();
System.out.println("absolutePath is " + db.getAbsolutePath());
} catch (IOException ex) {
}

        try {
            connection = DriverManager.getConnection(dbUrl);
            // System.out.println("Connection established: " + dbUrl);
        } catch (SQLException ex) {
                System.out.println("Error establishing connection " + ex.getSQLState());
            //return;
        }
         */
        try {
            if (connection != null) {
                stmt = connection.createStatement();
                stmt.setQueryTimeout(30);

                System.out.println("Connecting...");
                String sql = "select * from users WHERE username =? AND password=?";

                PreparedStatement pres = connection.prepareStatement(sql);
                pres.setString(1, u);
                pres.setString(2, p);

                rs = pres.executeQuery();
                while (rs.next()) {

                    String firstname = rs.getString("username");
                    String lastname = rs.getString("password");
                    String name = rs.getString("name");
                    return 0;
                }

            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
        }
        return 1;
    }

    public static void PurchaseIt(Integer x, String y, Integer z, Double ws, Double wt) {
        try {
            connection = Connections.getConnection();
        } catch (IOException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
 /* try {
        Class c = null;
        if (Platform.isAndroid()) {
        c = Class.forName("org.sqldroid.SQLDroidDriver");
        } else if (Platform.isIOS()) {
        c = Class.forName("SQLite.JDBCDriver");
        } else if (Platform.isDesktop()) {
        c = Class.forName("org.sqlite.JDBC");
        } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
        c = Class.forName("org.sqlite.JDBC");
        }
        } catch (ClassNotFoundException e) {
        System.out.println("Error class not found " + e);
        }
        
        if (Platform.isDesktop()) {
        dbUrl = dbUrl + ":resource:" + PrimaryPresenter.class.getResource("/database/" + DB_NAME).toExternalForm();
        } else {
        
        try {
        dir = Services.get(StorageService.class)
        .map(s -> s.getPrivateStorage().get())
        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        // System.out.println("Copying database " + DB_NAME + " to private storage");
        //  DBUtils.copyDatabase("/database/", dir.getAbsolutePath(), DB_NAME);
        dbUrl = dbUrl + db.getAbsolutePath();
        } catch (IOException ex) {
        //("IO error " + ex.getMessage());
        
        }
        }*/
 /*try {
  dir = Services.get(StorageService.class)
  .map(s -> s.getPrivateStorage().get())
  .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
  File db = new File(dir, DB_NAME);
  dbUrl = dbUrl + db.getAbsolutePath();
  } catch (IOException ex) {

        try {
            connection = DriverManager.getConnection(dbUrl);

        } catch (SQLException ex) {

        }
         */
        try {
            if (connection != null) {

                String sql1 = "insert into tras (id,trs_type,trs_num,stk_price,trs_price,trs_date) values(?,?,?,?,?,?);";

                PreparedStatement prestatment = connection.prepareStatement(sql1);
                prestatment.setInt(1, x);
                prestatment.setString(2, y);
                prestatment.setInt(3, z);
                prestatment.setDouble(4, ws);
                prestatment.setDouble(5, wt);
                prestatment.setDate(6, null);
                
                prestatment.executeUpdate();
                System.out.println("Inserted");
            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
        }

    }

    public static void insertCompany(String x, String y) {
        try {
            connection = Connections.getConnection();
        } catch (IOException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*        try {
        Class c = null;
        if (Platform.isAndroid()) {
        c = Class.forName("org.sqldroid.SQLDroidDriver");
        } else if (Platform.isIOS()) {
        c = Class.forName("SQLite.JDBCDriver");
        } else if (Platform.isDesktop()) {
        c = Class.forName("org.sqlite.JDBC");
        } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
        c = Class.forName("org.sqlite.JDBC");
        }
        } catch (ClassNotFoundException e) {
        System.out.println("Error class not found " + e);
        }
        
        try {
        dir = Services.get(StorageService.class)
        .map(s -> s.getPrivateStorage().get())
        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
        File db = new File(dir, DB_NAME);
        dbUrl = dbUrl + db.getAbsolutePath();
        } catch (IOException ex) {
        
        }
        try {
        connection = DriverManager.getConnection(dbUrl);
        
        } catch (SQLException ex) {
        
        }*/
        try {
            if (connection != null) {

                String sql2 = "insert into companies(company_name,notes) values(?,?);";

                PreparedStatement prestatment = connection.prepareStatement(sql2);
                prestatment.setString(1, x);
                prestatment.setString(2, y);

                prestatment.executeUpdate();

            }
        } catch (SQLException ex) {
            System.out.println("SQL error " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQL error " + ex.getSQLState());
            }
        }

    }

}
