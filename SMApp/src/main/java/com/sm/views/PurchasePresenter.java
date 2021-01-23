
package com.sm.views;



import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.gluonhq.charm.glisten.mvc.View;
import static com.sm.SM.SECONDARY_VIEW;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import com.gluonhq.charm.glisten.application.MobileApplication;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;


    

public class PurchasePresenter {
    
    @FXML
    private View purchaes;

    @FXML
    private ComboBox<Company> compnaiesList;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField stockNumTextField;

    @FXML
    private RadioButton pbutton;

    @FXML
    private RadioButton sbutton;

    private String p_type;

    ObservableList<Company> olist=FXCollections.observableArrayList(); 

    
 @FXML
    void process(ActionEvent event) throws IOException {
       
        Integer inputX=compnaiesList.getSelectionModel().getSelectedItem().getId();
        Integer inputY=Integer.parseInt(stockNumTextField.getText());
        Double inputWS=Double.parseDouble(priceTextField.getText());
        Double inputWT=inputWS;
        //Double inputWT=Double.parseDouble(priceTextField.getText());
        if (pbutton.isSelected())
           p_type="P";
         
         else  if ( sbutton.isSelected())
             p_type="S";
                  
       ///////////////////////////////
       
        DBConnection.PurchaseIt(inputX, p_type,inputY , inputWS,inputWT);
       
       
       /////////////////////////////////
       /*
       ApplicationContext appContext =  ... ;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("path/to/fxml"));
        loader.setControllerFactory(appContext::getBean);
        Parent root = loader.load();
       
       
       */   
       
       
       
       
       
                MobileApplication.getInstance().removeViewFactory(SECONDARY_VIEW);
                MobileApplication.getInstance().addViewFactory(SECONDARY_VIEW, () -> new SecondaryView().getView());

       
           MobileApplication.getInstance().switchView(SECONDARY_VIEW);
           
        /////////////////////////////////////
        
    }    
    
    @FXML
    void cancel(ActionEvent event){
    
     MobileApplication.getInstance().switchView("Secondary View");
    }
    
public void initialize() throws SQLException, IOException {
    purchaes.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
    olist.addAll(DBConnection.getCompanies());
   compnaiesList.setItems(olist);
}


}