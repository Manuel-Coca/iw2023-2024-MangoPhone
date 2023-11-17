package es.uca.iw.views.global;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Inicio Sesión")
@Route(value = "login")
@RouteAlias(value = "login")
public class LoginView extends Div {

    public LoginView() {

        // Layout del bloque de login
        VerticalLayout loginLayout = new VerticalLayout();
        
        // Componentes del bloque

        // Titulo
        H1 titulo = new H1("Iniciar Sesión | MangoPhone");
        
        // Inputs
        EmailField emailField = new EmailField("Correo electónico");
        PasswordField passwordField = new PasswordField("Contraseña");
        
        // Boton login
        Button loginButton = new Button("Iniciar sesión");
        loginButton.addClassName("boton-naranja-primary");
        loginButton.addClickListener(event -> {
            // String sMail = emailField.getValue();
            // String sPassword = passwordField.getValue();
            UI.getCurrent().navigate("home");        
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

        // Agregar el contenedor del formulario al componente Div
        add(loginLayout);
    }
}
