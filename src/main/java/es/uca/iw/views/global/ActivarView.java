package es.uca.iw.views.global;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.aplication.service.UsuarioService;

@PageTitle("Activar Usuario")
@Route(value = "useractivation")
@RouteAlias(value = "useractivation")
@AnonymousAllowed
public class ActivarView extends Div {

    @Autowired
    private UsuarioService usuarioService;
    private final H1 title;
    private final TextField email;
    private final TextField secretCode;
    private final Button activate;
    private final H4 status;

    public ActivarView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        title = new H1("Activate User");
        email = new TextField("Your email");
        email.setId("email");

        secretCode = new TextField("Your secret code");
        secretCode.setId("secretCode");

        status = new H4();
        status.setId("status");

        activate = new Button("Activate");
        activate.setId("activate");

        status.setVisible(false);

        activate.addClickListener(e -> onActivateButtonClick());
        add(title, new HorizontalLayout(email, secretCode), activate, status);
    }

    public void onActivateButtonClick() {
        status.setVisible(true);
    
        if (usuarioService.activarUsuario(email.getValue(), secretCode.getValue())) {
            status.setText("El usuario ha sido activado");
            Button loginButton = new Button("Iniciar sesión");
            loginButton.addClassName("boton-naranja-secondary");
            loginButton.addClickListener(event -> {
                UI.getCurrent().navigate("login");        
            });
        }else{
            status.setText("Ups. The user could not be activated");
        }
    }
}