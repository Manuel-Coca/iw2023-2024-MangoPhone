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
import es.uca.iw.aplication.service.EmailService;
import es.uca.iw.aplication.service.TokenService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Token;
import es.uca.iw.aplication.tables.usuarios.Usuario;


@PageTitle("Crear Cuenta")
@Route(value = "register")
@RouteAlias(value = "register")
@AnonymousAllowed
public class RegisterView extends Div {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;

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
        Binder<Usuario> binderRegister = new Binder<>(Usuario.class);
        
        binderRegister.forField(nameField)
            .asRequired("El nombre es obligatiorio")
            .bind(Usuario::getNombre, Usuario::setNombre);
        
        binderRegister.forField(surnameField)
            .asRequired("El apellido es obligatorio")
            .bind(Usuario::getApellidos, Usuario::setApellidos);
                
        binderRegister.forField(dniField)
            .asRequired("El DNI es obligatorio")
            //.withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
            //.withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
            .bind(Usuario::getDNI, Usuario::setDNI);
        
        binderRegister.forField(phoneNumberField)
            .asRequired("El teléfono es obligatorio")
            .bind(Usuario::getTelefono, Usuario::setTelefono);

        
        binderRegister.forField(birthDateField)
            .asRequired("La fecha de nacimiento es obligatoria")
            //.withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El Usuario ha de ser mayor de edad")
            .bind(Usuario::getFechaNacimiento, Usuario::setFechaNacimiento);
        
        
        binderRegister.forField(passwordField)
            .asRequired("La contraseña es obligatoria")
            //.withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
            //.withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
            //.withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
            //.withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
            //.withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
            //.withValidator(password1 -> password1.equals(confirmPasswordField.getValue()), "Las contraseñas no coinciden")
            .bind(Usuario::getContrasena, Usuario::setContrasena);

        binderRegister.forField(confirmPasswordField)
            .asRequired("La confirmación de la contraseña es obligatoria")
            //.withValidator(password1 -> password1.equals(confirmPasswordField.getValue()), "Las contraseñas no coinciden")
            .bind(Usuario::getContrasena, Usuario::setContrasena);

        binderRegister.forField(emailField)
            .asRequired("El correo electrónico es obligatorio")
            //.withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
            .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);
        
        // Boton register
        Button registerButton = new Button("Crear cuenta");
        registerButton.addClassName("boton-naranja-primary");
        registerButton.addClassName(LumoUtility.Margin.Bottom.LARGE);
        registerButton.addClickShortcut(Key.ENTER);
        registerButton.addClickListener(event -> {
            if(binderRegister.validate().isOk()) {
                Usuario usuario = new Usuario();
                usuario.setId(UUID.randomUUID());
                usuario.setRol(Rol.CLIENTE);
                usuario.setNombre(nameField.getValue());
                usuario.setApellidos(surnameField.getValue());
                usuario.setDNI(dniField.getValue());
                usuario.setTelefono(phoneNumberField.getValue());
                usuario.setFechaNacimiento(birthDateField.getValue());
                usuario.setCorreoElectronico(emailField.getValue());
                usuario.setContrasena(passwordField.getValue());
                usuario.setActivo(false);

                SaveRequest(usuario);
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

    private void SaveRequest(Usuario usuario) {
        try {
            String code = UUID.randomUUID().toString().substring(0, 5);
            
            usuarioService.createUsuario(usuario);

            Token token = new Token();
            token.setId(UUID.randomUUID());
            token.setToken(code);
            token.setUsuario(usuario);
            token.setDate(token.calculateExpiryDate(token.expiracion));
            tokenService.createToken(token);
            
            emailService.sendRegistartionEmail(usuario,code);
            ConfirmDialog confirmDialog = new ConfirmDialog("Registro Correcto", "Activar cuenta", "Activar", event1 -> {
                UI.getCurrent().navigate("/useractivation");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al registrar el usuario", "Aceptar", event -> { 
                UI.getCurrent().navigate("/register");
            });
            errorDialog.open();
        }
    }
}
