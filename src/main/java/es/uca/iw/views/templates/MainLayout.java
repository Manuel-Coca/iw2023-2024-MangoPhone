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
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.global.autenticacion.LoginView;
import es.uca.iw.views.global.autenticacion.LogoutView;
import es.uca.iw.views.global.main.AboutUsView;
import es.uca.iw.views.global.main.ContratoFormView;
import es.uca.iw.views.global.main.ServicesInfoView;
import es.uca.iw.views.global.main.WelcomeView;
import es.uca.iw.views.global.perfil.PerfilContratoView;
import es.uca.iw.views.global.perfil.PerfilView;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    @Autowired
    private UsuarioService usuarioService;

    public MainLayout(UsuarioService usuarioService) {
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
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Inicio", WelcomeView.class, LineAwesomeIcon.HOME_SOLID.create()));
        nav.addItem(new SideNavItem("Nuestros servicios", ServicesInfoView.class, LineAwesomeIcon.PHONE_SOLID.create()));
        nav.addItem(new SideNavItem("Sobre nosotros", AboutUsView.class, LineAwesomeIcon.INFO_SOLID.create()));
        
        if(session.getAttribute("loggedUserId") == null) {
            nav.addItem(new SideNavItem("Iniciar Sesión", LoginView.class, LineAwesomeIcon.USER.create()));
            nav.addItem(new SideNavItem("Contratar", ContratoFormView.class, LineAwesomeIcon.PLUS_CIRCLE_SOLID.create()));
        }
        else {
            nav.addItem(new SideNavItem("Tu perfil", PerfilView.class, LineAwesomeIcon.USER.create()));  
            nav.addItem(new SideNavItem("Cerrar sesión", LogoutView.class, LineAwesomeIcon.USER.create()));  
            
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getCuentaUsuario().getContrato() == null) nav.addItem(new SideNavItem("Contrata", ContratoFormView.class, LineAwesomeIcon.PLUS_CIRCLE_SOLID.create()));
            else nav.addItem(new SideNavItem("Tu contrato", PerfilContratoView.class, LineAwesomeIcon.SCROLL_SOLID.create()));
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
