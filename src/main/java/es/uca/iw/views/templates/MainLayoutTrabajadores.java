package es.uca.iw.views.templates;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.global.autenticacion.LogoutView;
import es.uca.iw.views.marketing.CrearTarifaView;
import es.uca.iw.views.marketing.ListaTarifasView;
import es.uca.iw.views.marketing.MarkentingHomeView;
import es.uca.iw.views.sac.ContratosClientesView;
import es.uca.iw.views.sac.MensajesClientesView;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayoutTrabajadores extends AppLayout {
    
    @Autowired
    private UsuarioService usuarioService;

    public MainLayoutTrabajadores(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setPrimarySection(Section.NAVBAR);
        crearMenu();
        crearHeader();
        crearFooter();
    }
    
    private void crearHeader() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        
        Image mangoIcon = new Image("icons/mango-fruit-icon.svg", "logo");
        mangoIcon.setWidth("30px");
        mangoIcon.setHeight("30px");
        
        H1 mangoName = new H1("Mango");
        mangoName.addClassNames("titulo-custom");
        
        HorizontalLayout bloqueLogoNombre = new HorizontalLayout();
        bloqueLogoNombre.add(mangoIcon, mangoName);
        bloqueLogoNombre.setAlignItems(Alignment.CENTER);
        bloqueLogoNombre.addClickListener(event -> {
            UI.getCurrent().navigate("home");
        });
        
        addToNavbar(true, toggle, bloqueLogoNombre);
    }
    
    private void crearMenu() {
        H1 appName = new H1("Menú");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        
        Scroller scroller = new Scroller(crearNavegacion());
        
        addToDrawer(header, scroller, crearFooter());
    }
    
    private SideNav crearNavegacion() {
        VaadinSession session = VaadinSession.getCurrent();
        
        Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
        SideNav nav = new SideNav();
        if(loggedUser.getRol().equals(Rol.MARKETING)) {
            nav.addItem(new SideNavItem("Inicio", MarkentingHomeView.class, LineAwesomeIcon.HOME_SOLID.create()));
            nav.addItem(new SideNavItem("Crear tarifa", CrearTarifaView.class, LineAwesomeIcon.PLUS_CIRCLE_SOLID.create()));
            nav.addItem(new SideNavItem("Ver tarifas", ListaTarifasView.class, LineAwesomeIcon.LIST_SOLID.create()));
            nav.addItem(new SideNavItem("Cerrar sesión", LogoutView.class, LineAwesomeIcon.USER.create()));  
        }
        else if(loggedUser.getRol().equals(Rol.FINANZAS)) {
            //---
            nav.addItem(new SideNavItem("Cerrar sesión", LogoutView.class, LineAwesomeIcon.USER.create()));  
        }
        else if(loggedUser.getRol().equals(Rol.SAC)) {
            nav.addItem(new SideNavItem("Gestionar contratos", ContratosClientesView.class, LineAwesomeIcon.SCROLL_SOLID.create()));  
            nav.addItem(new SideNavItem("Ver mensajes", MensajesClientesView.class, LineAwesomeIcon.ENVELOPE.create()));  
            nav.addItem(new SideNavItem("Cerrar sesión", LogoutView.class, LineAwesomeIcon.USER.create()));  
        }

        return nav;
    }

    private Footer crearFooter() {
        Footer layout = new Footer();
        return layout;
    }

    /* 
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
    */
}
