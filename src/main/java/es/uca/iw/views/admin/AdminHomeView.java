package es.uca.iw.views.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;

@PageTitle("Admin")
@Route(value = "adminhome")
@RouteAlias(value = "adminhome")

public class AdminHomeView extends Div {

    @Value("${admin.user}")
    private String adminUser;
    
    @Value("${admin.pass}")
    private String adminPass;

    @Autowired
    private UsuarioService usuarioService;

    public AdminHomeView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        Dialog loginDialog = loginDialog();
        loginDialog.open();
    }
    
    private Dialog loginDialog() {
        Dialog loginDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        
        TextField adminUserField = new TextField("Usuario de admin");
        PasswordField adminPassField = new PasswordField("Contraseña de admin");
    
        dialogLayout.add(adminUserField, adminPassField);
        
        // Botones
        Button iniciarSesion = new Button("Iniciar sesión");
        iniciarSesion.addClassName("boton-naranja-primary");
        iniciarSesion.addClickListener(event -> {
            if(validarCredenciales(adminUserField.getValue(), adminPassField.getValue())) {
                loginDialog.close();
                crearContenido();
            }
            else {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                Div text = new Div(new Text("Credenciales erróneas"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event1 -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.open();
            }
        });

        Button cerrarModal = new Button("Salir");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            UI.getCurrent().getPage().setLocation("home");
        });
    
        loginDialog.getFooter().add(cerrarModal, iniciarSesion);
    
        loginDialog.setHeaderTitle("Inicio de sesión del administrador");
        loginDialog.setWidth("300px");
        loginDialog.setCloseOnEsc(false);
        loginDialog.setCloseOnOutsideClick(false);
        loginDialog.add(dialogLayout);
        return loginDialog;
    }

    private boolean validarCredenciales(String user, String pass) {
        if(user.equals(adminUser) && pass.equals(adminPass)) return true;
        else return false;
    }

    private void crearContenido() {
        VerticalLayout adminLayout = new VerticalLayout();

        Paragraph crearTrabajador = new Paragraph("Crear un trabajador");
        crearTrabajador.addClassName("enlace");
        crearTrabajador.addClickListener(event -> {
            Dialog modalCrearTrabajador = modalCrearTrabajador();
            modalCrearTrabajador.open();
        });
        adminLayout.add(crearTrabajador);
        add(adminLayout);
    }

    private Dialog modalCrearTrabajador() {
        Dialog crearTrabajadorDialog = new Dialog();
        HorizontalLayout dialogLayout = new HorizontalLayout();
        VerticalLayout column1Layout = new VerticalLayout();
        VerticalLayout column2Layout = new VerticalLayout();
    
        // Inputs
        TextField nameField = new TextField("Nombre");
        TextField surnameField = new TextField("Apellidos");
        TextField dniField = new TextField("DNI");
        TextField phoneNumberField = new TextField("Teléfono");
        EmailField emailField = new EmailField("Correo electónico");
        DatePicker birthDateField = new DatePicker("Fecha de nacimiento");
        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.setHelperText("La contraseña debe tener al menos 8 caracteres, un número y un caracter especial");
        Select<Rol> rolField = new Select<Rol>();
        rolField.setLabel("Rol");
        rolField.setItems(Rol.FINANZAS, Rol.MARKETING, Rol.SAC);
        
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
            .withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
            .withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
            .bind(Usuario::getDNI, Usuario::setDNI);
        
        binderRegister.forField(phoneNumberField)
            .asRequired("El teléfono es obligatorio")
            .bind(Usuario::getTelefono, Usuario::setTelefono);

        
        binderRegister.forField(birthDateField)
            .asRequired("La fecha de nacimiento es obligatoria")
            .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El Usuario ha de ser mayor de edad")
            .bind(Usuario::getFechaNacimiento, Usuario::setFechaNacimiento);
        
        
        binderRegister.forField(passwordField)
            .asRequired("La contraseña es obligatoria")
            .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
            .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
            .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
            .bind(Usuario::getContrasena, Usuario::setContrasena);

        binderRegister.forField(rolField)
            .asRequired("El rol es obligatorio");

        binderRegister.forField(emailField)
            .asRequired("El correo electrónico es obligatorio")
            .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
            .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);
        
    
        // Agregar componentes al contenedor del formulario
        column1Layout.add(nameField, surnameField, dniField, phoneNumberField);
        column2Layout.add(emailField, birthDateField, passwordField, rolField);
        dialogLayout.add(column1Layout, column2Layout);
        
        // Botones
        Button crearTrabajador = new Button("Crear");
        crearTrabajador.addClassName("boton-naranja-primary");
        crearTrabajador.addClickListener(event -> {
            if(binderRegister.validate().isOk()) {
                Usuario usuario = new Usuario();
                usuario.setId(UUID.randomUUID());
                usuario.setRol(rolField.getValue());
                usuario.setNombre(nameField.getValue());
                usuario.setApellidos(surnameField.getValue());
                usuario.setDNI(dniField.getValue());
                usuario.setTelefono(phoneNumberField.getValue());
                usuario.setFechaNacimiento(birthDateField.getValue());
                usuario.setCorreoElectronico(emailField.getValue());
                usuario.setContrasena(passwordField.getValue());
                usuario.setActivo(true);

                SaveRequest(usuario);
                crearTrabajadorDialog.close();
            }
        });

        Button cerrarModal = new Button("Salir");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            crearTrabajadorDialog.close();
        });
    
        crearTrabajadorDialog.getFooter().add(cerrarModal, crearTrabajador);
    
        crearTrabajadorDialog.setHeaderTitle("Crear un perfil de trabajador");
        crearTrabajadorDialog.setWidth("800px");
        crearTrabajadorDialog.add(dialogLayout);
        return crearTrabajadorDialog;
    }

    private void SaveRequest(Usuario usuario) {
        try {
            usuarioService.createUsuario(usuario);

            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            Div text = new Div(new Text("Trabajador creado correctamente"));
            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });
            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.open();
        }
        catch (Exception e) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            Div text = new Div(new Text("Error en la creación"));
            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });
            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);
            notification.add(layout);
            notification.open();
        }
    }
}