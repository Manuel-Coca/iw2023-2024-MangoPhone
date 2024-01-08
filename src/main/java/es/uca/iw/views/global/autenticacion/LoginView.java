package es.uca.iw.views.global.autenticacion;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import es.uca.iw.aplication.service.EmailService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;

import es.uca.iw.aplication.tables.usuarios.Usuario;

@PageTitle("Inicio Sesión")
@Route(value = "login")
@RouteAlias(value = "login")
@AnonymousAllowed
public class LoginView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    //private final AuthenticatedUser authUser;

    public LoginView(EmailService emailService, UsuarioService usuarioService) {
        this.emailService = emailService;
        this.usuarioService = usuarioService;
        VaadinSession session = VaadinSession.getCurrent();
        
        if(session.getAttribute("loggedUserId") == null ) {
            add(LoginLayout());
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            
            ConfirmDialog error = new ConfirmDialog("Error", "Ya has iniciado sesión", "Entrar", event -> {
                if (loggedUser.getRol().equals(Rol.CLIENTE))        UI.getCurrent().navigate("home");  
                else if (loggedUser.getRol().equals(Rol.SAC))       UI.getCurrent().navigate("sachome"); 
                else if (loggedUser.getRol().equals(Rol.MARKETING)) UI.getCurrent().navigate("marketinghome"); 
                else if (loggedUser.getRol().equals(Rol.FINANZAS))  UI.getCurrent().navigate("/home"); 
            });
            error.open();
        }
    }

    private VerticalLayout LoginLayout() {

        // Layout del bloque de login
        VerticalLayout loginLayout = new VerticalLayout();
        
        // Componentes del bloque

        // Titulo
        H1 titulo = new H1("Iniciar Sesión | MangoPhone");
        
        // Inputs
        EmailField emailField = new EmailField("Correo electónico");
        PasswordField passwordField = new PasswordField("Contraseña");
        
        //Binding
        Binder<Usuario> binderLogin = new Binder<>(Usuario.class);
        
        binderLogin.forField(emailField)
            .asRequired("El correo electrónico es obligatorio")
            .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);

        binderLogin.forField(passwordField)
            .asRequired("La contraseña es obligatoria")
            .bind(Usuario::getContrasena, Usuario::setContrasena);
        
        // Boton login
        Button loginButton = new Button("Iniciar sesión");
        loginButton.addClassName("boton-naranja-primary");
        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addClickListener(event -> {
            if (binderLogin.validate().isOk()) {
                Usuario usuario = usuarioService.buscarEmail(emailField.getValue());
                VerifyLogin(usuario, passwordField.getValue());
            }       
        });
        
        // Enlace "Olvidar contraseña"
        Paragraph forgotPwText = new Paragraph("¿Olvidaste tu contraseña?");
        forgotPwText.addClassName(LumoUtility.Margin.Bottom.LARGE);
        forgotPwText.addClassName("enlace");
        forgotPwText.addClickListener(event -> {
            Dialog newPassDialog = crearModalNuevaContraseña();
            newPassDialog.open();
        });
        
        // Texto "Nuevo cliente"
        Text question = new Text("¿Eres un nuevo cliente?");

        // Boton Ir a registro
        Button registerButton = new Button("Crear una cuenta");
        registerButton.addClassName("boton-naranja-secondary");
        registerButton.addClickListener(event -> {
            // Cambiar a ir a registro
            UI.getCurrent().getPage().setLocation("register");        
        });

        // Enlace "Ir a inicio"
        Anchor backToHome = new Anchor("home", "Inicio");
        backToHome.addClassName(LumoUtility.Margin.Top.LARGE);

        // Agregar componentes al contenedor del formulario
        loginLayout.add(titulo, emailField, passwordField, loginButton, forgotPwText, question, registerButton, backToHome);

        // Centrar el contenedor del formulario en la pantalla
        loginLayout.setSizeFull();
        loginLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        loginLayout.setAlignItems(Alignment.CENTER);

        return loginLayout;
    }

    private void VerifyLogin(Usuario usuario, String password) {
        try {
            if(usuario != null && passwordEncoder.matches(password, usuario.getContrasena())) {
                // Abrir la sesion
                VaadinSession session = VaadinSession.getCurrent();

                if(usuario.getRol().equals(Rol.CLIENTE)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un cliente.", "Entrar", event -> {
                        UI.getCurrent().getPage().setLocation("profile"); //Perfil de cliente
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.SAC)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de SAC.", "Entrar", event -> {
                        UI.getCurrent().getPage().setLocation("sachome"); //Perfil de SAC
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.MARKETING)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de Marketing.", "Entrar", event -> {
                        UI.getCurrent().getPage().setLocation("marketinghome"); //Perfil de Marketing
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.FINANZAS)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de Finanzas.", "Entrar", event -> {
                        UI.getCurrent().getPage().setLocation("finanzashome"); //Perfil de Finanzas
                    });
                    dialogBienvenida.open();
                }
            }
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "El correo o la contraseña son incorrectos", "Reintentar", event -> {
                        UI.getCurrent().getPage().setLocation("login");
                    });
                errorDialog.open();
            }
        } 
        catch(Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "No se ha conseguido realizar la peticion", "Reintentar", event -> {
                UI.getCurrent().getPage().setLocation("/login");
            });
            errorDialog.open();
        }
    }

    private Dialog crearModalNuevaContraseña() {
        Dialog newPassDialog = new Dialog();
        // Binding
        Binder<Usuario> binder = new Binder<>(Usuario.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        EmailField correoField = new EmailField("Correo electrónico");
        correoField.setWidthFull();
        correoField.setPlaceholder("Introduce tu correo electrónico");
        binder.forField(correoField)
                .asRequired("El correo es obligatorio")
                .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);

        Paragraph infoParagraph = new Paragraph("Se le enviará a su correo una nueva contraseña. Úsela para iniciar sesión. No olvide cambiarla en su perfil cuando entre.");

        dialogLayout.add(infoParagraph, correoField);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            newPassDialog.close();
        });

        Button confirmar = new Button("Enviar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(event2 -> {
            if(binder.validate().isOk()) {
                try {
                    Usuario user = usuarioService.buscarEmail(correoField.getValue());
                    String newPass = UUID.randomUUID().toString().substring(0, 5);
    
                    user.setContrasena(newPass);
                    usuarioService.updateUsuarioOnlyPass(user);
                    emailService.sendNewPassEmail(user, newPass);
    
                    UI.getCurrent().getPage().setLocation("login");
                }
                catch(Exception e) { 
                    ConfirmDialog errorDialog = new ConfirmDialog("Error", "El usuario no existe", "Reintentar", event3 -> {
                        UI.getCurrent().getPage().setLocation("login");
                    });
                    errorDialog.open();
                }
            }
        });
        newPassDialog.getFooter().add(cerrarModal, confirmar);

        newPassDialog.setHeaderTitle("Olvido de contraseña");
        newPassDialog.setWidth("600px");
        newPassDialog.add(dialogLayout);
        return newPassDialog;
    }

    /*
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(authUser.get().isPresent()) {
            //setOpened(false);
            event.forwardTo("");
        }
    }
     */
}