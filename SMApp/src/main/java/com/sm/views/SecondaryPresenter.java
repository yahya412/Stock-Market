package com.sm.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.sm.Stocks;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.net.ssl.HttpsURLConnection;
public class SecondaryPresenter {
     static String u1="https://www.tadawul.com.sa/wps/portal/tadawul/markets/equities/market-watch?locale=ar";
     
      public static  List <Stocks> db_stocks=new ArrayList<>();

    @FXML
    private View secondary;
    
    @FXML
    private    TableView<Stocks> StockTable;
 
    @FXML
     private  TableColumn<Stocks, String> stockName;

    @FXML
    private  TableColumn<Stocks, Integer> stockNum;

    @FXML
    private  TableColumn<Stocks, Double> cprice;

    @FXML
    private   TableColumn<Stocks, Double> total;
    
    @FXML
     private void purchace(ActionEvent event) {
         MobileApplication.getInstance().switchView("Purchase View");
     }

    @FXML
    private void exit(ActionEvent event) {
             MobileApplication.getInstance().switchView(HOME_VIEW);
    }

    public  ObservableList<Stocks> temp=FXCollections.observableArrayList();;
    
    
    
    public void initialize() throws IOException {
         
                try{
                db_stocks=DBConnection.Get_Stocks_list();
                }catch (SQLException e){
                    }
              
        
        secondary.setShowTransitionFactory(BounceInRightTransition::new);
        secondary.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
        
        
        stockName.setCellValueFactory(new PropertyValueFactory<Stocks,String>("stockName"));
        stockNum.setCellValueFactory(new PropertyValueFactory<Stocks,Integer>("stockNum"));
        cprice.setCellValueFactory(new PropertyValueFactory<Stocks,Double>("cprice"));
        //pdate.setCellValueFactory(new PropertyValueFactory<Stocks,LocalDate>("pdate"));
        total.setCellValueFactory(new PropertyValueFactory<Stocks,Double>("total"));
        temp=getStock(db_stocks);
        StockTable.setItems( temp);
        
        
        
        
        /*FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
        e -> System.out.println("Info"));
        fab.showOn(secondary);*/
        
        secondary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Secondary");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    }
    public static ObservableList<Stocks> getStocks(List<Stocks> array){
        
        ObservableList<Stocks> stocks=FXCollections.observableArrayList();
        String inputLine="",value="",fprice="";
        Double price=0d;        


//  List<Stocks> stocks=new ArrayList<>();
//Stocks s;
        /*System.out.println(array.get(3).getcPrice());
        System.out.println(array.get(3).getPdate());
        System.out.println(array.get(3).getStockName());
        System.out.println(array.get(3).getstockNum());*/
        
         
        try{
                URL myUrl = new URL(u1);
                HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                while ( inputLine!= null) {
                inputLine=br.readLine();   
                        for (int n=0 ; n < array.size();n++){
                                if (inputLine.contains(array.get(n).getStockName()) ){
                                for (int i=0;i<6;i++){
                                    value=br.readLine();
                
                                 }
                                fprice = value.charAt(28)+""+value.charAt(29)+""+value.charAt(30)+""+value.charAt(31)+""+value.charAt(32);
                                //price=Float.parseFloat(fprice);
                                price=Double.parseDouble(fprice);
                                //System.out.println(value);
                               //System.out.println(price);
                                //stocks.add(new Stocks(array.get(n).getStockName(),10,price));
                                db_stocks.get(n).setCprice(price);
                                //db_stocks.get(n).setstockNum(10);
                                //System.out.println(db_stocks.get(n).getcPrice());
                                
                                //stocks.add(db_stocks.get(n));
                                //System.out.println(db_stocks.get(n).getcPrice());
                                //System.out.println("  "+stocks.get(n).getCprice());
                                //System.out.println(db_stocks.get(n).getPdate());
                                //System.out.println("  "+stocks.get(n).getPdate());
                                //System.out.println(db_stocks.get(n).getStockName());
                                //System.out.println("  "+stocks.get(n).getStockName());
                                //System.out.println(db_stocks.get(n).getstockNum());
                                //System.out.println("  "+stocks.get(n).getStockNum());
                                //System.out.println(db_stocks.get(n).getTotal());
                                //System.out.println(stocks.get(n).getTotal());
                                }
                                
                                stocks.clear();
                                stocks.addAll(db_stocks);
                                /*for (Stocks s:stocks){
                                System.out.println(s.getcPrice());
                                System.out.println(s.getPdate());
                                System.out.println(s.getStockName());
                                System.out.println(s.getstockNum());
                                System.out.println("total::"+s.getTotal());
                                }*/
                    
                        }
            
                }
                
                br.close();   
                
            }   catch(Exception e){
              //  System.out.println("Error "+e);
            } 
        
        return stocks;
      
    }
public static ObservableList<Stocks> getStock(List<Stocks> array){
        
        ObservableList<Stocks> stocks=FXCollections.observableArrayList(array);
       

                    
                                for (int n=0 ; n < array.size();n++){
                                //db_stocks.get(n).setCprice(1.0);
                                stocks.get(n).setCprice(1.0);
                                }
                                
                               // stocks.clear();
                                //stocks.addAll(db_stocks);
        
        return stocks;
      
    }

public void refresh(){
    StockTable.refresh();
}
}
