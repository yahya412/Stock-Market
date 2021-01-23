package com.sm;

import com.sm.views.PurchaseView;
import com.sm.views.PrimaryView;
import com.sm.views.SecondaryView;
import com.sm.views.CompanyView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.sm.views.GeneralReportView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SM extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    public static final String SECONDARY_VIEW = "Secondary View";
    public static final String PURCHASE_VIEW= "Purchase View";
    public static final String COMPANY_VIEW="Company View" ;       
    public static final String GENERALREPORT_VIEW="GeneralReport View";
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> new PrimaryView().getView());
        addViewFactory(SECONDARY_VIEW, () -> new SecondaryView().getView());
        addViewFactory(PURCHASE_VIEW,() -> new PurchaseView().getView());
        addViewFactory(COMPANY_VIEW,() -> new CompanyView().getView());
        addViewFactory(GENERALREPORT_VIEW,()-> new GeneralReportView().getView());
        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(SM.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(SM.class.getResourceAsStream("/icon.png")));
    }
}
