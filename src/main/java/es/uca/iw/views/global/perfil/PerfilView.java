package es.uca.iw.views.global.perfil;

import java.util.Random;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tu perfil")
@Route(value = "profile", layout = MainLayout.class)
@RouteAlias(value = "profile", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilView extends Div {

    private final VaadinSession session = VaadinSession.getCurrent();
    private Object loggedUser = VaadinSession.getCurrent().getAttribute("loggedUser");
    private Object profilePic = VaadinSession.getCurrent().getAttribute("profilePic");

    public PerfilView() {
        add(crearContenido());
    }

    private Div crearContenido() {
        Div globalDiv = new Div();
        VerticalLayout globalVerticalLayout = new VerticalLayout();

        // Foto + nombre
        HorizontalLayout nameBlock = new HorizontalLayout();
        if(profilePic == null) {
            Random rand = new Random();
            int randomNum = rand.nextInt((13 - 1) + 1) + 1;
            session.setAttribute("profilePic", randomNum);
        }
        
        if(loggedUser != null) {
            Avatar profilePic = new Avatar(((Usuario)session.getAttribute("loggedUser")).getNombre());
            profilePic.setImage("/icons/profilepics/image" + session.getAttribute("profilePic") + ".svg");
            profilePic.setHeight("150px");
            profilePic.setWidth("150px");            
            nameBlock.add(profilePic);
            
            Paragraph userName = new Paragraph(((Usuario)session.getAttribute("loggedUser")).getNombre() + " " + (((Usuario)session.getAttribute("loggedUser")).getApellidos()));
            userName.addClassName("profile-name");
            nameBlock.setAlignItems(FlexComponent.Alignment.CENTER);
            nameBlock.add(userName);
            
            globalVerticalLayout.add(nameBlock);
            
            // Opciones
            Paragraph datosPersonalesLink = new Paragraph("Cambiar datos personales");
            datosPersonalesLink.addClassName("enlace");
            datosPersonalesLink.addClickListener(event -> {
                Dialog editDialog = crearModalEditarPerfil();
                editDialog.open();
            });


            Anchor listaLlamadasLink = new Anchor("/profile/llamadas", "Ver desglose de llamadas");
            Anchor listaFacturasLink = new Anchor("/profile/facturas", "Ver tus facturas");
            Anchor cerrarSesionLink = new Anchor("logout", "Cerrar sesión");
    
            globalVerticalLayout.add(datosPersonalesLink, listaLlamadasLink, listaFacturasLink, cerrarSesionLink);
    
            globalDiv.add(globalVerticalLayout);
            return globalDiv;
        }
        else {
            Anchor iniciarSesion = new Anchor("login", "Iniciar sesión");
            globalDiv.add(iniciarSesion);
            return globalDiv;
        }
    }

    private Dialog crearModalEditarPerfil() {
        Dialog editDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();

        // Nombre
        TextField nombreField = new TextField("Nombre");
        nombreField.setValue(((Usuario)session.getAttribute("loggedUser")).getNombre());
        dialogLayout.add(nombreField);
        
        // Apellidos
        TextField apellidosField = new TextField("Apellidos");
        nombreField.setValue(((Usuario)session.getAttribute("loggedUser")).getApellidos());
        dialogLayout.add(apellidosField);

        // Email
        TextField mailField = new TextField("Correo electrónico");
        mailField.setValue(((Usuario)session.getAttribute("loggedUser")).getCorreoElectronico());
        dialogLayout.add(mailField);

        // Pass
        PasswordField passField = new PasswordField("Contraseña");
        dialogLayout.add(passField);

        // Nacimiento
        DatePicker dateField = new DatePicker("Fecha de nacimiento");
        dateField.setValue(((Usuario)session.getAttribute("loggedUser")).getFechaNacimiento());
        dialogLayout.add(dateField);

        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event -> {
            editDialog.close();
        });
        Button confirmar = new Button("Guardar");
        confirmar.addClassName("boton-naranja-primary");
        cerrarModal.addClickListener(event -> {
            editDialog.close();
        });
        editDialog.getFooter().add(cerrarModal, confirmar);

        editDialog.setHeaderTitle("Editar información");
        editDialog.add(dialogLayout);

        // Binding
        Binder<Usuario> binderRegister = new Binder<>(Usuario.class);
        
        binderRegister.forField(nombreField)
            .asRequired("El nombre es obligatiorio")
            .bind(Usuario::getNombre, Usuario::setNombre);
        
        binderRegister.forField(apellidosField)
            .asRequired("El apellido es obligatorio")
            .bind(Usuario::getApellidos, Usuario::setApellidos);

        
        binderRegister.forField(dateField)
            .asRequired("La fecha de nacimiento es obligatoria")
            //.withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El Usuario ha de ser mayor de edad")
            .bind(Usuario::getFechaNacimiento, Usuario::setFechaNacimiento);
        
        binderRegister.forField(passField)
            .asRequired("La contraseña es obligatoria")
            //.withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
            //.withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
            //.withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
            //.withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
            //.withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
            //.withValidator(password1 -> password1.equals(confirmPasswordField.getValue()), "Las contraseñas no coinciden")
            .bind(Usuario::getContrasena, Usuario::setContrasena);

        binderRegister.forField(mailField)
            .asRequired("El correo electrónico es obligatorio")
            //.withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
            .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);

        return editDialog;
    }
}
