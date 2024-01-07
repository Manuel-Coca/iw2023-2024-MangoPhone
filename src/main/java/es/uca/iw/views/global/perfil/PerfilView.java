package es.uca.iw.views.global.perfil;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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

import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tu perfil")
@Route(value = "profile", layout = MainLayout.class)
@RouteAlias(value = "profile", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilView extends Div {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private VaadinSession session = VaadinSession.getCurrent();
    private Object profilePic = VaadinSession.getCurrent().getAttribute("profilePic");

    public PerfilView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para ver tu perfil", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getActivo() == false) add(crearContenidoNoActivo(loggedUser));
            else add(crearContenidoActivo(loggedUser));
        }
    }

    private Div crearContenidoNoActivo(Usuario loggedUser) {
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
            Avatar profilePic = new Avatar(loggedUser.getNombre());
            profilePic.setImage("/icons/profilepics/image" + session.getAttribute("profilePic") + ".svg");
            profilePic.setHeight("150px");
            profilePic.setWidth("150px");            
            nameBlock.add(profilePic);
            
            Paragraph userName = new Paragraph(loggedUser.getNombre() + " " + (loggedUser.getApellidos()));
            userName.addClassName("profile-name");
            nameBlock.setAlignItems(FlexComponent.Alignment.CENTER);
            nameBlock.add(userName);
            
            globalVerticalLayout.add(nameBlock);

            // Opciones
            Paragraph datosPersonalesLink = new Paragraph("Cambiar datos personales");
            datosPersonalesLink.addClassName("enlace");
            datosPersonalesLink.addClickListener(event -> {
                Dialog editDialog = crearModalEditarPerfil(false, loggedUser);
                editDialog.open();
            });

            Paragraph passwordLink = new Paragraph("Cambiar contraseña");
            passwordLink.addClassName("enlace");
            passwordLink.addClickListener(event -> {
                Dialog editDialog = crearModalEditarPerfil(true, loggedUser);
                editDialog.open();
            });

            Paragraph activarLink = new Paragraph("Activar perfil");
            activarLink.addClassName("enlace");
            activarLink.addClickListener(event -> {
                UI.getCurrent().getPage().setLocation("activar");
            });

            Paragraph cerrarSesionLink = new Paragraph("Cerrar sesión");
            cerrarSesionLink.addClassName("enlace");
            cerrarSesionLink.addClickListener(event -> {
                UI.getCurrent().getPage().setLocation("logout");
            });
    
            globalVerticalLayout.add(datosPersonalesLink, passwordLink, activarLink, cerrarSesionLink);
            globalDiv.add(globalVerticalLayout);
        }

        return globalDiv;
    }

    private Div crearContenidoActivo(Usuario loggedUser) {
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
            Avatar profilePic = new Avatar(loggedUser.getNombre());
            profilePic.setImage("/icons/profilepics/image" + session.getAttribute("profilePic") + ".svg");
            profilePic.setHeight("150px");
            profilePic.setWidth("150px");            
            nameBlock.add(profilePic);
            
            Paragraph userName = new Paragraph(loggedUser.getNombre() + " " + (loggedUser.getApellidos()));
            userName.addClassName("profile-name");
            nameBlock.setAlignItems(FlexComponent.Alignment.CENTER);
            nameBlock.add(userName);
            
            globalVerticalLayout.add(nameBlock);
            
            // Opciones
            Paragraph datosPersonalesLink = new Paragraph("Cambiar datos personales");
            datosPersonalesLink.addClassName("enlace");
            datosPersonalesLink.addClickListener(event -> {
                Dialog editDialog = crearModalEditarPerfil(false, loggedUser);
                editDialog.open();
            });

            Paragraph passwordLink = new Paragraph("Cambiar contraseña");
            passwordLink.addClassName("enlace");
            passwordLink.addClickListener(event -> {
                Dialog editDialog = crearModalEditarPerfil(true, loggedUser);
                editDialog.open();
            });

            Paragraph contratoLink = new Paragraph("Gestionar tu contrato");
            contratoLink.addClassName("enlace");
            contratoLink.addClickListener(event -> {
                UI.getCurrent().getPage().setLocation("profile/contrato");
            });

            Paragraph facturasLink = new Paragraph("Ver tus facturas");
            facturasLink.addClassName("enlace");
            facturasLink.addClickListener(event -> {
                UI.getCurrent().getPage().setLocation("profile/facturas");
            });

            Paragraph mensajesLink = new Paragraph("Ver o realizar consultas y reclamaciones");
            mensajesLink.addClassName("enlace");
            mensajesLink.addClickListener(event -> {
                UI.getCurrent().getPage().setLocation("profile/mensajes");
            });

            Anchor listaLlamadasLink = new Anchor("/profile/llamadas", "Ver desglose de llamadas");
            Anchor cerrarSesionLink = new Anchor("logout", "Cerrar sesión");
    
            globalVerticalLayout.add(datosPersonalesLink, passwordLink, contratoLink, facturasLink, listaLlamadasLink, mensajesLink, cerrarSesionLink);
    
            globalDiv.add(globalVerticalLayout);
            return globalDiv;
        }
        else {
            Anchor iniciarSesion = new Anchor("login", "Iniciar sesión");
            globalDiv.add(iniciarSesion);
            return globalDiv;
        }
    }
    
    private Dialog crearModalEditarPerfil(boolean password, Usuario loggedUser) {
        Dialog editDialog = new Dialog();
        // Binding
        Binder<Usuario> binderRegister = new Binder<>(Usuario.class);
        VerticalLayout dialogLayout = new VerticalLayout();
        
        if(password) {
            // Nombre
            PasswordField oldPassField = new PasswordField("Antigua contraseña");
            PasswordField newPassField = new PasswordField("Nueva contraseña");
            newPassField.setHelperText("La contraseña debe tener al menos 8 caracteres, un número y un caracter especial");
            dialogLayout.add(oldPassField, newPassField);

            binderRegister.forField(oldPassField)
                .asRequired("La antigua contraseña es obligatoria")
                .bind(Usuario::getContrasena, Usuario::setContrasena);

            binderRegister.forField(newPassField)
                .asRequired("La nueva contraseña es obligatoria")
                //.withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                //.withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                //.withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .bind(Usuario::getContrasena, Usuario::setContrasena);

            // Botones
            Button cerrarModal = new Button("Cerrar");
            cerrarModal.addClassName("boton-verde-secondary");
            cerrarModal.addClickListener(event -> {
                editDialog.close();
            });
            Button confirmar = new Button("Cambiar");
            confirmar.addClassName("boton-naranja-primary");
            confirmar.addClickListener(event -> {
                if(binderRegister.validate().isOk() && passwordEncoder.matches(oldPassField.getValue(), loggedUser.getContrasena())) {
                    loggedUser.setContrasena(newPassField.getValue());
                    SaveRequestUpdatePass(loggedUser);
                }
                else {
                    Dialog errorDialog = new Dialog();
                    errorDialog.setHeaderTitle("Error");
                    errorDialog.add("Las contraseñas antiguas no coinciden");
                    errorDialog.getFooter().add(new Button("Cerrar", event1 -> {errorDialog.close();}));
                    errorDialog.open();
                }
            });
            editDialog.getFooter().add(cerrarModal, confirmar);
    
            editDialog.setHeaderTitle("Editar información");
            editDialog.add(dialogLayout);
        }
        else {
            // Nombre
            TextField nombreField = new TextField("Nombre");
            nombreField.setValue(loggedUser.getNombre());
            nombreField.setWidthFull();
            dialogLayout.add(nombreField);
            
            // Apellidos
            TextField apellidosField = new TextField("Apellidos");
            apellidosField.setValue(loggedUser.getApellidos());
            apellidosField.setWidthFull();
            dialogLayout.add(apellidosField);
    
            // Email
            TextField mailField = new TextField("Correo electrónico");
            mailField.setValue(loggedUser.getCorreoElectronico());
            mailField.setWidthFull();
            dialogLayout.add(mailField);
    
            // Nacimiento
            DatePicker dateField = new DatePicker("Fecha de nacimiento");
            dateField.setValue(loggedUser.getFechaNacimiento());
            dialogLayout.add(dateField);
            
            
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
            
    
            binderRegister.forField(mailField)
                .asRequired("El correo electrónico es obligatorio")
                //.withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Usuario::getCorreoElectronico, Usuario::setCorreoElectronico);
            
            // Botones
            Button cerrarModal = new Button("Cerrar");
            cerrarModal.addClassName("boton-verde-secondary");
            cerrarModal.addClickListener(event -> {
                editDialog.close();
            });
            Button confirmar = new Button("Guardar");
            confirmar.addClassName("boton-naranja-primary");
            confirmar.addClickListener(event -> {
                if(binderRegister.validate().isOk()) {
                    loggedUser.setNombre(nombreField.getValue());
                    loggedUser.setApellidos(apellidosField.getValue());
                    loggedUser.setCorreoElectronico(mailField.getValue());
                    loggedUser.setFechaNacimiento(dateField.getValue());
    
                    SaveRequestUpdateData(loggedUser);
                }
            });
            editDialog.getFooter().add(cerrarModal, confirmar);
    
            editDialog.setHeaderTitle("Editar información");
            editDialog.setWidth("400px");
            dialogLayout.setWidthFull();
            editDialog.add(dialogLayout);
        }
        return editDialog;
    }

    private void SaveRequestUpdatePass(Usuario usuario) {
        try {
            usuarioService.updateUsuarioOnlyPass(usuario);
            ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Cambios realizados correctamente", "Confirmar", event1 -> {
                UI.getCurrent().getPage().setLocation("profile");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al cambiar los datos", "Volver", event -> { 
                UI.getCurrent().navigate("profile");
            });
            errorDialog.open();
        }
    }

    private void SaveRequestUpdateData(Usuario usuario) {
        try {
            usuarioService.updateUsuarioRegularData(usuario);
            ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Cambios realizados correctamente", "Confirmar", event1 -> {
                UI.getCurrent().getPage().setLocation("profile");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al cambiar los datos", "Volver", event -> { 
                UI.getCurrent().navigate("profile");
            });
            errorDialog.open();
        }
    }
}
