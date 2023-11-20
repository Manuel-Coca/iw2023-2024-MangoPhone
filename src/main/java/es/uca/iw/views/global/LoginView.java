package es.uca.iw.views.global;

import java.util.Objects;
import java.util.Optional;

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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
import com.vaadin.flow.theme.lumo.LumoUtility;

import es.uca.iw.aplication.service.ClienteService;
import es.uca.iw.aplication.tables.Cliente;

@PageTitle("Inicio Sesión")
@Route(value = "login")
@RouteAlias(value = "login")
public class LoginView extends Div {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginView() {
        add(LoginLayout());
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
        Binder<Cliente> binderLogin = new Binder<>(Cliente.class);
        
        binderLogin.forField(emailField)
            .asRequired("El correo electrónico es obligatorio")
            .bind(Cliente::getCorreoElectronico, Cliente::setCorreoElectronico);

        binderLogin.forField(passwordField)
            .asRequired("La contraseña es obligatoria")
            .bind(Cliente::getContrasena, Cliente::setContrasena);
        
        // Boton login
        Button loginButton = new Button("Iniciar sesión");
        loginButton.addClassName("boton-naranja-primary");
        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addClickListener(event -> {
            if (binderLogin.validate().isOk()) {
                Optional<Cliente> cliente = clienteService.findByCorreoElectronico(emailField.getValue());
                //VerifyLogin(cliente, passwordField.getValue());
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

    private void VerifyLogin(Cliente cliente, String password) {
        /*
        try {
            if (cliente != null && passwordEncoder.matches(passwordForm, user.getPassword())) {
                // Declaramos el rol del usuario
                String role = usuarioService.getRole(user.getUUID());
                user.setRol(Role.valueOf(role));
                // Coger el usuario logueado
                VaadinSession session = VaadinSession.getCurrent();

                if (Objects.equals(user.getRol(), Role.CLIENTE.toString())) {
                    session.setAttribute(Cliente.class, (Cliente) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-cliente");

                } else if (Objects.equals(user.getRol(), Role.GESTOR.toString())) {
                    session.setAttribute(Gestor.class, (Gestor) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-gestor");

                } else if (Objects.equals(user.getRol(), Role.ENCARGADO_COMUNICACIONES.toString())) {
                    session.setAttribute(EncargadoComunicaciones.class, (EncargadoComunicaciones) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-encargado");

                } else if (Objects.equals(user.getRol(), Role.ADMINISTRADOR.toString())) {
                    session.setAttribute(Administrador.class, (Administrador) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-admin");

                }
            } else {
                Notification errorEmailPassword = Notification.show("El correo electrónico o la contraseña son incorrectos");
                errorEmailPassword.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
    */
    }
}
