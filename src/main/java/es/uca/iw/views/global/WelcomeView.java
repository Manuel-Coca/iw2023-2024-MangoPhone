package es.uca.iw.views.global;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import es.uca.iw.views.templates.MainLayout;

@PageTitle("Bienvenido")
@Route(value = "welcome", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class WelcomeView extends HorizontalLayout {

    public WelcomeView() {
        String mensaje = new String("Soy la vista welcome");
        add(mensaje);
    }
}
