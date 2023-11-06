package es.uca.iw.views.global;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Crear Cuenta")
@Route(value = "register")
@RouteAlias(value = "register")
public class RegisterView extends Div {

    public RegisterView() {

        // Layouts del bloque de registro
        VerticalLayout registerLayout = new VerticalLayout();
        VerticalLayout column1Layout = new VerticalLayout();
        VerticalLayout column2Layout = new VerticalLayout();
        HorizontalLayout fieldsLayout = new HorizontalLayout();

        // Componentes del bloque

        // Titulo
        H1 titulo = new H1("Crear cuenta | MangoPhone");
        
        // Inputs
        TextField nameField = new TextField("Nombre");
        TextField surnameField = new TextField("Apellidos");
        TextField phoneNumberPhield = new TextField("Teléfono");
        EmailField emailField = new EmailField("Correo electónico");
        PasswordField passwordField = new PasswordField("Contraseña");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");
        
        // Boton register
        Button registerButton = new Button("Crear cuenta");
        registerButton.addClassName("boton-naranja-primary");
        registerButton.addClassName(LumoUtility.Margin.Bottom.LARGE);
        registerButton.addClickListener(event -> {
            // String sName = nameField.getValue();
            // String sSurname = surnameField.getValue();
            // String sPhoneNumber = phoneNumberPhield.getValue();
            // String sMail = emailField.getValue();
            String sPassword = passwordField.getValue();
            String sConfirmPassword = confirmPasswordField.getValue();
            if (sPassword.equals(sConfirmPassword)) {
                UI.getCurrent().navigate("welcome");        
            }
        });
        
        // Texto "Nuevo cliente"
        Text question = new Text("¿Ya eres nuestro cliente?");

        // Boton Ir a registro
        Button loginButton = new Button("Iniciar sesión");
        loginButton.addClassName("boton-naranja-secondary");
        loginButton.addClickListener(event -> {
            // Cambiar a ir a registro
            UI.getCurrent().navigate("login");        
        });

        // Enlace "Ir a inicio"
        Anchor backToHome = new Anchor("welcome", "Inicio");
        backToHome.addClassName(LumoUtility.Margin.Top.LARGE);

        // Agregar componentes al contenedor del formulario
        column1Layout.add(nameField, surnameField, phoneNumberPhield);
        column2Layout.add(emailField, passwordField, confirmPasswordField);
        fieldsLayout.add(column1Layout, column2Layout);
        registerLayout.add(titulo, fieldsLayout, registerButton, question, loginButton, backToHome);
        
        // Centrar el contenedor del formulario en la pantalla
        registerLayout.setSizeFull();
        registerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        registerLayout.setAlignItems(Alignment.CENTER);

        // Agregar el contenedor del formulario al componente Div
        add(registerLayout);
    }
}
