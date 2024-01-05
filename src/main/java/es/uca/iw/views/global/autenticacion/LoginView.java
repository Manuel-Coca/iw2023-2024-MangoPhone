package es.uca.iw.views.global.autenticacion;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
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

    //private final AuthenticatedUser authUser;

    public LoginView() {
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
        Anchor forgotPwText = new Anchor("home", "¿Olvidaste tu contraseña?");
        forgotPwText.addClassName(LumoUtility.Margin.Bottom.LARGE);
        
        // Texto "Nuevo cliente"
        Text question = new Text("¿Eres un nuevo cliente?");

        // Boton Ir a registro
        Button registerButton = new Button("Crear una cuenta");
        registerButton.addClassName("boton-naranja-secondary");
        registerButton.addClickListener(event -> {
            // Cambiar a ir a registro
            UI.getCurrent().navigate("register");        
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
                        UI.getCurrent().navigate("profile"); //Perfil de cliente
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.SAC)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de SAC.", "Entrar", event -> {
                        UI.getCurrent().navigate("sachome"); //Perfil de SAC
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.MARKETING)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de Marketing.", "Entrar", event -> {
                        UI.getCurrent().navigate("marketinghome"); //Perfil de Marketing
                    });
                    dialogBienvenida.open();
                } 
                else if(usuario.getRol().equals(Rol.FINANZAS)) {
                    session.setAttribute("loggedUserId", usuario.getId().toString());
                    ConfirmDialog dialogBienvenida = new ConfirmDialog("Bienvenido", "Hola de vuelta, " + usuario.getNombre() + ". Eres un trabajador de Finanzas.", "Entrar", event -> {
                        UI.getCurrent().navigate("/home"); //Cambiar por perfil de Finanzas
                    });
                    dialogBienvenida.open();
                }
            }
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "El correo o la contraseña son incorrectos", "Reintentar", event -> {
                        UI.getCurrent().navigate("/login");
                    });
                errorDialog.open();
            }
        } 
        catch(Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "No se ha conseguido realizar la peticion", "Reintentar", event -> {
                UI.getCurrent().navigate("/login");
            });
            errorDialog.open();
        }
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