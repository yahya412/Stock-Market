package com.sm.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
public class PrimaryPresenter {

    @FXML
    private View primary;
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label statuslbl;

public void setStatus(String x){
statuslbl.setText(x);
}

    //private final static String DB_NAME = "sample1.db";
    //private Connection connection = null;
    //private Statement stmt;
    //private ResultSet rs;
    public static int status=1;
    @FXML
    void login(ActionEvent event) throws IOException {
        
        statuslbl.setText(usernameField.getText()+passwordField.getText());
        System.out.println(DBConnection.CheckLogin(usernameField.getText(), passwordField.getText()));
        try{
        status=DBConnection.CheckLogin(usernameField.getText(), passwordField.getText());
        if (status==0){
            MobileApplication.getInstance().switchView("Secondary View");
          
        }
        }catch (Exception e){
        statuslbl.setText(e.getMessage());
        }
       
        
    /*    
        MobileApplication.getInstance().switchView("../fxml/purchase.fxml");  
        File dir;
        String dbUrl = "jdbc:sqlite:";
        
      
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
            File db = new File (dir, DB_NAME);
            dbUrl = dbUrl + db.getAbsolutePath();
            System.out.println("dbUrl***********"+dbUrl);
        } catch (IOException ex) {
            System.out.println("Error1" +ex);
            return;
        }

        try {
            connection = DriverManager.getConnection(dbUrl);
           System.out.println("Connection established: " + dbUrl);
        } catch (SQLException ex) {
            System.out.println("Error establishing connection " + ex.getSQLState());
            return;
        }

        try {
            if (connection != null) {
                stmt = connection.createStatement();
                stmt.setQueryTimeout(30);

                System.out.println("Creating table 'users'...");
                //stmt.executeUpdate("drop table if exists users");
                //create table users (id integer, username string, password string,name string)");
                //create table tras(id intger,trs_type char,trs_num integer,stk_price double,trs_price double,trs_date date);

                //stmt.executeUpdate("insert into users values(1, 'admin','1234','ADMIN')");
                //stmt.executeUpdate("insert into users values(2, 'user','0000','user')");
                //stmt.executeUpdate("insert into users values(3, 'user','1111','Guest')");
                
               // stmt.executeUpdate("create table companies (id integer, company_name string, notes string)");
               // stmt.executeUpdate("insert into companies values(5, 'زجاج',null)");
               // stmt.executeUpdate("insert into companies values(6, 'اللجين',null)");
               // stmt.executeUpdate("insert into companies values(7, 'سابك',null)");
                //stmt.executeUpdate("insert into companies values(8, 'ينساب',null)");
               
               
                
                
                System.out.println("Retrieving records from table 'users'...");
                rs = stmt.executeQuery("select * from companies");
                while (rs.next()) {
                    int name =rs.getInt("id");
                    String firstname = rs.getString("company_name");
                    String lastname = rs.getString("notes");
                    
                    System.out.print(firstname+"\t");
                    System.out.print(lastname+"\t");
                    System.out.println(name);
                }
                System.out.println("End creating table and retrieving records");
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

*/
    }
         


    public void initialize() {
        primary.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("اسهم محلية");
         
                appBar.setVisible(false);
//       appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
           //             System.out.println("Search")));
            }
        });
    }
    
   
}
