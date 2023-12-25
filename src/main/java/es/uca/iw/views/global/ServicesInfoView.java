package es.uca.iw.views.global;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.views.templates.MainLayout;


@PageTitle("Nuestros servicios")
@Route(value = "servicesInfo", layout = MainLayout.class)
@RouteAlias(value = "servicesInfo", layout = MainLayout.class)
@AnonymousAllowed
public class ServicesInfoView extends HorizontalLayout{
    
    public ServicesInfoView() {
        Anchor backToHome = new Anchor("home", "Inicio");
        add(backToHome);
    }
}
