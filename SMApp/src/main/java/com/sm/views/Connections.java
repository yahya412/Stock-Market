package com.sm.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Connections {

    private static final String DB_NAME = "sample1.db";
    private static Connection connection = null;
    private static Statement stmt;
    private static ResultSet rs, rx;
    private static File dir,db;
    private static  String dbUrl = "jdbc:sqlite:";
    
    public static Connection getConnection() throws IOException {
      



  
       // Connection connection;
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

///////////////
        if (Platform.isDesktop()) {
            //dbUrl = dbUrl + ":resource:" + PrimaryPresenter.class.getResource("/database/" + DB_NAME).toExternalForm();
            //System.out.println("here:"+dbUrl);
            
            dir = Services.get(StorageService.class)
          .map(s -> s.getPrivateStorage().get())
          .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
          File db = new File (dir, DB_NAME);
          dbUrl = dbUrl + db.getAbsolutePath();
        } else {
            try {
                dir = Services.get(StorageService.class)
                        .map(s -> s.getPrivateStorage().get())
                        .orElseThrow(() -> new IOException("Error: PrivateStorage not available"));
                db = new File(dir, DB_NAME);
               
                
               if (!db.exists()){
                DBUtils.copyDatabase("/database/", dir.getAbsolutePath(), DB_NAME);
               }
            } finally {dbUrl = dbUrl + db.getAbsolutePath();
                
            }
        }
       
        try {
       connection = (Connection) DriverManager.getConnection(dbUrl);
            // System.out.println("Connection established: " + dbUrl);
        } catch (SQLException ex) {
          //  System.out.println("Error establishing connection " + ex.getSQLState());
            //return;
        }
        return connection;

    }

}
