package es.uca.iw.views.global.autenticacion;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.MyEmailService;
import es.uca.iw.aplication.service.TokenService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.usuarios.Token;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@PageTitle("Activar Usuario")
@Route(value = "activar")
@RouteAlias(value = "activar")
@AnonymousAllowed
public class ActivarView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService; 

    @Autowired
    private MyEmailService emailService;

    private VaadinSession session = VaadinSession.getCurrent();

    public ActivarView(UsuarioService usuarioService, TokenService tokenService, MyEmailService emailService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        this.emailService = emailService;
        
        if(session.getAttribute("justRegisteredUser") != null && (boolean)session.getAttribute("justRegisteredUser") == true) {
            add(crearContenido(null));
            session.setAttribute("justRegisteredUser", false);
        }
        else {
            if(session.getAttribute("loggedUserId") == null) {
                ConfirmDialog dialog = new ConfirmDialog("Error", "Debes iniciar sesión", "Iniciar sesión", evento -> {
                    UI.getCurrent().getPage().setLocation("login");
                });
                dialog.open();
            }
            else {
                Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
                if(loggedUser.getActivo() == false) {
                    add(crearContenido(loggedUser));
                }
                else {
                    ConfirmDialog dialog = new ConfirmDialog("Aviso", "El usuario ya está activo", "Inicio", evento -> {
                        UI.getCurrent().getPage().setLocation("home");
                    });
                    dialog.open();
                }
            }
        }
    }

    private Div crearContenido(Usuario loggedUser) {
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
            System.out.println(emailField.getValue() + tokenField.getValue());
            confirmarPeticion(emailField.getValue(), tokenField.getValue());
        });
        globalVerticalLayout.add(confirmButton);

        SimpleTimer timer = new SimpleTimer(30);
        timer.setFractions(false);

        Button reenviarButton = new Button("Enviar código");
        reenviarButton.addClassName("boton-verde-secondary");
        reenviarButton.addClickListener(event -> {
            reenviarButton.setEnabled(false);
            timer.start();
            timer.addTimerEndEvent(e -> { 
                reenviarButton.setEnabled(true); 
                timer.reset(); 
            });       
            
            Token newToken = tokenService.buscarToken(loggedUser);
            newToken.setToken(UUID.randomUUID().toString().substring(0, 5));
            newToken.setDate(newToken.calculateExpiryDate(newToken.expiracion));
            tokenService.createToken(newToken);

            emailService.sendNewToken(loggedUser, newToken.getToken());
        });
        if(session.getAttribute("justRegisteredUser") != null && (boolean)session.getAttribute("justRegisteredUser") == true) {
            timer.start();
            reenviarButton.setEnabled(false);
            timer.addTimerEndEvent(e -> { 
                reenviarButton.setEnabled(true); 
                timer.reset(); 
            });   
        }
    
        HorizontalLayout reenvioLayout = new HorizontalLayout();
        reenvioLayout.setAlignItems(Alignment.CENTER);
        reenvioLayout.add(reenviarButton, timer, new Paragraph("segundos antes de poder reenviar."));
        globalVerticalLayout.add(reenvioLayout);
        
        globalLayout.add(globalVerticalLayout);
        return globalLayout;
    }

    private void confirmarPeticion(String email, String token) {    
        if(usuarioService.activarUsuario(email, token)) {
            if(session.getAttribute("loggedUserId") == null) {
                ConfirmDialog okDialog = new ConfirmDialog("Éxito", "El usuario ha sido activado con éxito. Ahora, inicie sesión", "Iniciar sesión", event -> {
                    UI.getCurrent().getPage().setLocation("login");
                });
                okDialog.open();
            }
            else {
                ConfirmDialog okDialog = new ConfirmDialog("Éxito", "El usuario ha sido activado con éxito.", "Inicio", event -> {
                    UI.getCurrent().getPage().setLocation("profile");
                });
                okDialog.open();

            }
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