package es.uca.iw.views.global;

import java.util.Random;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    // cocaalba.manuel@gmail.com
    public PerfilView() {
        add(crearContenido());
    }

    private Div crearContenido() {
        Div globalDiv = new Div();
        VerticalLayout globalVerticalLayout = new VerticalLayout();

        // Foto + nombre
        HorizontalLayout nameBlock = new HorizontalLayout();
        if(session != null && session.getAttribute("profilePic") == null ) {
            Random rand = new Random();
            int randomNum = rand.nextInt((13 - 1) + 1) + 1;
            session.setAttribute("profilePic", randomNum);
            
            Avatar profilePic = new Avatar(((Usuario)session.getAttribute("loggedUser")).getNombre());
            profilePic.setImage("/icons/profilepics/image" + session.getAttribute("profilePic") + ".svg");
            profilePic.setHeight("150px");
            profilePic.setWidth("150px");            
            
            nameBlock.add(profilePic);
        }
        else {
            Avatar profilePic = new Avatar(((Usuario)session.getAttribute("loggedUser")).getNombre());
            profilePic.setImage("/icons/profilepics/image" + session.getAttribute("profilePic") + ".svg");
            profilePic.setHeight("150px");
            profilePic.setWidth("150px");
            
            nameBlock.add(profilePic);
        }
        
        Paragraph userName = new Paragraph(((Usuario)session.getAttribute("loggedUser")).getNombre() + " " + (((Usuario)session.getAttribute("loggedUser")).getApellidos()));
        userName.addClassName("profile-name");
        nameBlock.setAlignItems(FlexComponent.Alignment.CENTER);
        nameBlock.add(userName);
        globalVerticalLayout.add(nameBlock);

        // Opciones
        Anchor datosPersonalesLink = new Anchor("/profile/datos", "Ver datos personales");
        Anchor listaLlamadasLink = new Anchor("/profile/llamadas", "Ver desglose de llamadas");
        Anchor listaFacturasLink = new Anchor("/profile/facturas", "Ver tus facturas");
        Anchor cerrarSesionLink = new Anchor("logout", "Cerrar sesión");

        globalVerticalLayout.add(datosPersonalesLink, listaLlamadasLink, listaFacturasLink, cerrarSesionLink);

        globalDiv.add(globalVerticalLayout);
        return globalDiv;
    }
}
