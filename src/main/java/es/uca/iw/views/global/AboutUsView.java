package es.uca.iw.views.global;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import es.uca.iw.views.templates.MainLayout;


@PageTitle("Sobre nosotros")
@Route(value = "aboutUs", layout = MainLayout.class)
@RouteAlias(value = "aboutUs", layout = MainLayout.class)
public class AboutUsView extends HorizontalLayout {
    public AboutUsView() {
        Anchor backToHome = new Anchor("home", "Inicio");
        add(backToHome);
    }
}
