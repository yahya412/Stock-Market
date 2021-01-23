package com.sm.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CompanyPresenter {


@FXML
    private View companies;

    @FXML
    private TextField company_name;

    @FXML
    private TextField notes;

    @FXML
    void insertCompany(ActionEvent event) {
        DBConnection.insertCompany(company_name.getText(), notes.getText());
    
    company_name.setText("");notes.setText("");
    }

    
}
