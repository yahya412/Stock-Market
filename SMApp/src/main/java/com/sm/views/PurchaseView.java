
package com.sm.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;



public class PurchaseView {

    public View getView() {
        
        
        try {
            View view = FXMLLoader.load(SecondaryView.class.getResource("purchase.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
    
}
