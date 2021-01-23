package com.sm.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GeneralReportPresenter {

    public  ObservableList<Transaction> trasnactions=FXCollections.observableArrayList();

//public ObservableList<Transaction> trasnactions1=FXCollections.observableArrayList();

    @FXML
    private View generalReport;
    @FXML
    private TableView<Transaction> transTable;
    @FXML
    private TableColumn<Transaction, String> name;

    @FXML
    private TableColumn<Transaction, Integer> num;

    @FXML
    private TableColumn<Transaction,Double> pprice;

    @FXML
    private TableColumn<Transaction,Double> sprice;

    @FXML
    private TableColumn<Transaction,Double> total;

@FXML
    void cancel(ActionEvent event){
    
     MobileApplication.getInstance().switchView("Secondary View");
    }

public void initialize() throws IOException{
    generalReport.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
    trasnactions=  DBConnection.GetPprice();
    
    
    
    name.setCellValueFactory(new PropertyValueFactory<Transaction,String>("name"));
    num.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("num"));
    pprice.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("pprice"));
    sprice.setCellValueFactory( new PropertyValueFactory<Transaction,Double>("sprice"));
    total.setCellValueFactory(new PropertyValueFactory<Transaction,Double>("totla"));
    transTable.setItems(trasnactions);

}


}
    

