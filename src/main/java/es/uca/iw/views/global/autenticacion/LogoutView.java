package es.uca.iw.views.global.autenticacion;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;


@Route(value = "logout")
@RouteAlias(value = "logout")

public class LogoutView extends Div {
    
    public LogoutView() {
        if(VaadinSession.getCurrent() != null) { 
            VaadinSession.getCurrent().getSession().invalidate();
            VaadinSession.getCurrent().close();
        }
        UI.getCurrent().getPage().setLocation("home");
    }
}