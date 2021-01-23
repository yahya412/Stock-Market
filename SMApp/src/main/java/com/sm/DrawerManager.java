package com.sm;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.sm.SM.PRIMARY_VIEW;
import static com.sm.SM.SECONDARY_VIEW;
import static com.sm.SM.COMPANY_VIEW;
import static com.sm.SM.GENERALREPORT_VIEW;
import com.sm.views.PrimaryPresenter;
import javafx.scene.image.Image;

public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("ادارة الاسهم المحليه",
                "تداول",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
        
        final Item primaryItem = new ViewItem("تسجيل الدخول", MaterialDesignIcon.HOME.graphic(), PRIMARY_VIEW, ViewStackPolicy.SKIP);
        final Item secondaryItem = new ViewItem("تداول", MaterialDesignIcon.DASHBOARD.graphic(), SECONDARY_VIEW);
        final Item companyItem =new ViewItem("شركه جديدة",MaterialDesignIcon.DASHBOARD.graphic(),COMPANY_VIEW);
        final Item genralReportItem =new ViewItem("تقرير عام",MaterialDesignIcon.DASHBOARD.graphic(),GENERALREPORT_VIEW);
        
        drawer.getItems().addAll( secondaryItem,companyItem,genralReportItem);
            
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}