package com.sm.views;
import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class CompanyView {

    public View getView() {
        try {
            View view = FXMLLoader.load(PrimaryView.class.getResource("insertCompany.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
