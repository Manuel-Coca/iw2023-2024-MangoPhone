package es.uca.iw.views.global.autenticacion;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@PageTitle("Activar Usuario")
@Route(value = "activar")
@RouteAlias(value = "activar")
@AnonymousAllowed
public class ActivarView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();

    public ActivarView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        if(((Usuario)session.getAttribute("loggedUser")) == null || !((Usuario)session.getAttribute("loggedUser")).getActivo()) {
            add(crearContenido());
        }else{
            ConfirmDialog dialog = new ConfirmDialog("Aviso", "El usuario ya está activo", "Inicio", evento -> {
                UI.getCurrent().navigate("/home");
            });
            dialog.open();
        }
    }

    private Div crearContenido() {
        VerticalLayout globalVerticalLayout = new VerticalLayout();
        Div globalLayout = new Div();
        
        H1 title = new H1("Activar usuario");
        globalVerticalLayout.add(title);

        EmailField emailField = new EmailField("Correo electrónico");
        globalVerticalLayout.add(emailField);

        TextField tokenField = new TextField("Código de activación");
        globalVerticalLayout.add(tokenField);

        Button confirmButton = new Button("Activar cuenta");
        confirmButton.addClassName("boton-naranja-primary");
        confirmButton.addClickListener(event -> {
            confirmarPeticion(emailField.getValue(), tokenField.getValue());
        });
        globalVerticalLayout.add(confirmButton);
        
        globalLayout.add(globalVerticalLayout);
        return globalLayout;
    }

    private void confirmarPeticion(String email, String token) {    
        if(usuarioService.activarUsuario(email, token)) {
            ConfirmDialog okDialog = new ConfirmDialog("Éxito", "El usuario ha sido activado con éxito. Ahora, inicie sesión", "Iniciar sesión", event -> {
                UI.getCurrent().navigate("/login");
            });
            okDialog.open();
        }
        else if(email == null || email.equals("") || token == null || token.equals("")){
            ConfirmDialog errorDialog = new ConfirmDialog();
            errorDialog.setHeader("Error");
            errorDialog.setText("Debe rellenar los campos solicitados");
            errorDialog.setConfirmText("Cerrar");
            errorDialog.addConfirmListener(event -> { errorDialog.close(); });
            errorDialog.open();
        }
        else {
            ConfirmDialog errorDialog = new ConfirmDialog();
            errorDialog.setHeader("Error");
            errorDialog.setText("No se ha podido verificar el usuario");
            errorDialog.setConfirmText("Cerrar");
            errorDialog.addConfirmListener(event -> { errorDialog.close(); });
            errorDialog.open();
        }
    }
}