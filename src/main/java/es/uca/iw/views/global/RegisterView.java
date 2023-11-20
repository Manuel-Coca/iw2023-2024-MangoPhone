package es.uca.iw.views.global;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import es.uca.iw.aplication.service.ClienteService;
import es.uca.iw.aplication.tables.usuarios.Cliente;


@PageTitle("Crear Cuenta")
@Route(value = "register")
@RouteAlias(value = "register")
@AnonymousAllowed
public class RegisterView extends Div {

    @Autowired
    private ClienteService clienteService;

    public RegisterView() {
        add(RegisterLayout());        
    }

    private VerticalLayout RegisterLayout() {

        // Layout del bloque de registro
        VerticalLayout registerLayout = new VerticalLayout();
        HorizontalLayout fieldsLayout = new HorizontalLayout();
        VerticalLayout column1Layout = new VerticalLayout();
        VerticalLayout column2Layout = new VerticalLayout();
        
        // Componentes del bloque
        
        // Titulo
        H1 titulo = new H1("Crear cuenta | MangoPhone");
        
        // Inputs
        TextField nameField = new TextField("Nombre");
        TextField surnameField = new TextField("Apellidos");
        TextField dniField = new TextField("DNI");
        TextField phoneNumberField = new TextField("Teléfono");
        EmailField emailField = new EmailField("Correo electónico");
        DatePicker birthDateField = new DatePicker("Fecha de nacimiento");
        PasswordField passwordField = new PasswordField("Contraseña");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");
        
        // Binding
        Binder<Cliente> binderRegister = new Binder<>(Cliente.class);
        
        binderRegister.forField(nameField)
            .asRequired("El nombre es obligatiorio")
            .bind(Cliente::getNombre, Cliente::setNombre);
        
        binderRegister.forField(surnameField)
            .asRequired("El apellido es obligatorio")
            .bind(Cliente::getApellidos, Cliente::setApellidos);
                
        binderRegister.forField(dniField)
            .asRequired("El DNI es obligatorio")
            .withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
            .withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
            .bind(Cliente::getDNI, Cliente::setDNI);
        
        binderRegister.forField(phoneNumberField)
            .asRequired("El teléfono es obligatorio")
            .bind(Cliente::getTelefono, Cliente::setTelefono);

        
        binderRegister.forField(birthDateField)
            .asRequired("La fecha de nacimiento es obligatoria")
            .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El Cliente ha de ser mayor de edad")
            .bind(Cliente::getFechaNacimiento, Cliente::setFechaNacimiento);
        
        
        binderRegister.forField(passwordField)
            .asRequired("La contraseña es obligatoria")
            .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
            .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
            .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
            .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
            .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
            .withValidator(password1 -> password1.equals(confirmPasswordField.getValue()), "Las contraseñas no coinciden")
            .bind(Cliente::getContrasena, Cliente::setContrasena);

        binderRegister.forField(confirmPasswordField)
            .asRequired("La confirmación de la contraseña es obligatoria")
            .withValidator(password1 -> password1.equals(confirmPasswordField.getValue()), "Las contraseñas no coinciden")
            .bind(Cliente::getContrasena, Cliente::setContrasena);

        binderRegister.forField(emailField)
            .asRequired("El correo electrónico es obligatorio")
            .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
            .bind(Cliente::getCorreoElectronico, Cliente::setCorreoElectronico);
        
        // Boton register
        Button registerButton = new Button("Crear cuenta");
        registerButton.addClassName("boton-naranja-primary");
        registerButton.addClassName(LumoUtility.Margin.Bottom.LARGE);
        registerButton.addClickShortcut(Key.ENTER);
        registerButton.addClickListener(event -> {
            if(binderRegister.validate().isOk()) {
                Cliente cliente = new Cliente();
                cliente.setId(UUID.randomUUID());
                cliente.setNombre(nameField.getValue());
                cliente.setApellidos(surnameField.getValue());
                cliente.setDNI(dniField.getValue());
                cliente.setTelefono(phoneNumberField.getValue());
                cliente.setFechaNacimiento(birthDateField.getValue());
                cliente.setCorreoElectronico(emailField.getValue());
                cliente.setContrasena(passwordField.getValue());
                cliente.setActivo(false);

                SaveRequest(cliente);
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
        Anchor backToHome = new Anchor("home", "Inicio");
        backToHome.addClassName(LumoUtility.Margin.Top.LARGE);

        // Agregar componentes al contenedor del formulario
        column1Layout.add(nameField, surnameField, dniField, phoneNumberField);
        column2Layout.add(emailField, birthDateField, passwordField, confirmPasswordField);
        fieldsLayout.add(column1Layout, column2Layout);
        registerLayout.add(titulo, fieldsLayout, registerButton, question, loginButton, backToHome);
        
        // Centrar el contenedor del formulario en la pantalla
        registerLayout.setSizeFull();
        registerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        registerLayout.setAlignItems(Alignment.CENTER);

        return registerLayout;
    }

    private void SaveRequest(Cliente cliente) {
        try {
            clienteService.createCliente(cliente);
            ConfirmDialog confirmDialog = new ConfirmDialog("Registro Correcto", "Registro realizado correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/login");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al registrar cliente", "Aceptar", event -> { 
                UI.getCurrent().navigate("/register");
            });
            errorDialog.open();
        }
    }
}
