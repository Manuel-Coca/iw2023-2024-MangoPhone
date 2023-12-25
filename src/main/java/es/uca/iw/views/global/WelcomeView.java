package es.uca.iw.views.global;

import com.flowingcode.vaadin.addons.carousel.Carousel;
import com.flowingcode.vaadin.addons.carousel.Slide;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Bienvenido")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class WelcomeView extends Div {

    private VaadinSession session = VaadinSession.getCurrent();

    public WelcomeView() {
        add(crearCarrusel(), crearContenido());
    }

    public Carousel crearCarrusel() {
        // Slide 1
        Div izquierdaS1 = new Div();
        izquierdaS1.addClassName("mitad-izquierda-slide1");

        H1 mainTextS1 = new H1("¡Hola Cádiz!");
        Paragraph secondTextS1 = new Paragraph("Os traemos la mejor conexión de toda la provincia. Conectamos a las personas desde Tarifa hasta la Sierra");
        
        Div derechaS1 = new Div();
        derechaS1.addClassName("mitad-derecha-slide1");
        derechaS1.add(mainTextS1, secondTextS1);
        
        Div slide1Layout = new Div();
        slide1Layout.addClassName("slide");
        slide1Layout.add(izquierdaS1, derechaS1);

        Slide s1 = new Slide(slide1Layout);

        // Slide 2
        Div izquierdaS2 = new Div();
        izquierdaS2.addClassName("mitad-izquierda-slide2");

        H1 mainTextS2 = new H1("¡Somos los más frescos!");
        Paragraph secondTextS2 = new Paragraph("Nos comprometemos a traer las ofertas más frescas del mercado. Somos como el mango del verano pero en las telecomunicaciones");
        
        Div derechaS2 = new Div();
        derechaS2.addClassName("mitad-derecha-slide2");
        derechaS2.add(mainTextS2, secondTextS2);

        Div slide2Layout = new Div();
        slide2Layout.addClassName("slide");
        slide2Layout.add(izquierdaS2, derechaS2);

        Slide s2 = new Slide(slide2Layout);

        // Slide 3
        H1 mainTextS3 = new H1("Conectamos contigo igual que tu conectas con los demás");
        Paragraph secondTextS3 = new Paragraph("No dudes en preguntar ante cualquier problema. Ofrecemos la mejor atención al cliente");
        Div izquierdaS3 = new Div();
        izquierdaS3.addClassName("mitad-izquierda-slide3");
        izquierdaS3.add(mainTextS3, secondTextS3);

        Div derechaS3 = new Div();
        derechaS3.addClassName("mitad-derecha-slide3");

        Div slide3Layout = new Div();
        slide3Layout.addClassName("slide");
        slide3Layout.add(izquierdaS3, derechaS3);

        Slide s3 = new Slide(slide3Layout);
        //s3.setClassName("fondo-verde");
    
        Carousel c = new Carousel(s1,s2,s3)
        .withAutoProgress()
        .withoutSwipe()
        .withSlideDuration(4)
        .withStartPosition(0);
        
        c.setWidthFull();
        c.setHeight("600px");

        return c;
    }

    public Div crearContenido() {
        // Layout final
        Div globalViewLayout = new Div();
        
        // Texto de introduccion
        H1 servicesTitle = new H1("Tarifas frescas como un mango");
        
        HorizontalLayout textoIntroLayout = new HorizontalLayout();
        textoIntroLayout.setWidthFull();
        textoIntroLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        textoIntroLayout.addClassName("margen-top-mid");
        textoIntroLayout.add(servicesTitle);
        
        globalViewLayout.add(textoIntroLayout);

        // Cards
        // Fibra
        H2 fibraTitle = new H2("Fibra");
        Paragraph fibraDescription = new Paragraph("La fibra más rápida del mercado");
        Paragraph fibraPrice = new Paragraph("Desde 15€ / mes"); // Cambiar por llamada a base datos
        Button contratarFibraButton = new Button("¡Contratar!");
        contratarFibraButton.addClassName("boton-contratar");
        contratarFibraButton.addClickListener(event -> {  
            if(session.getAttribute("Rol") == null) UI.getCurrent().navigate("login");
            else UI.getCurrent().navigate("aboutUs"); // Cambiar por vista de contratar
        });

        Div fibraCard = new Div();
        fibraCard.add(fibraTitle, fibraDescription, fibraPrice, contratarFibraButton);
        fibraCard.addClassName("card");
       
       
        // Movil
        H2 movilTitle = new H2("Movil");
        Paragraph movilDescription = new Paragraph("Las llamadas con mejor audio del planeta");
        Paragraph movilPrice = new Paragraph("Desde 7.99€ / mes"); // Cambiar por llamada a base datos
        Button contratarMovilButton = new Button("¡Contratar!");
        contratarMovilButton.addClassName("boton-contratar");
        contratarMovilButton.addClickListener(event -> {  
            if(session.getAttribute("Rol") == null) UI.getCurrent().navigate("login");
            else UI.getCurrent().navigate("aboutUs"); // Cambiar por vista de contratar
        });

        Div movilCard = new Div();
        movilCard.add(movilTitle, movilDescription, movilPrice, contratarMovilButton);
        movilCard.addClassName("card");
        
        // Fijo
        H2 fijoTitle = new H2("Fijo");
        Paragraph fijoDescription = new Paragraph("El télefono fijo sigue siendo una realidad");
        Paragraph fijoPrice = new Paragraph("Desde 2.50€ / mes"); // Cambiar por llamada a base datos
        Button contratarFijoButton = new Button("¡Contratar!");
        contratarFijoButton.addClassName("boton-contratar");
        contratarFijoButton.addClickListener(event -> {  
            if(session.getAttribute("Rol") == null) UI.getCurrent().navigate("login");
            else UI.getCurrent().navigate("aboutUs"); // Cambiar por vista de contratar
        });

        Div fijoCard = new Div();
        fijoCard.add(fijoTitle, fijoDescription, fijoPrice, contratarFijoButton);
        fijoCard.addClassNames("card");
        
        HorizontalLayout cardsLayout = new HorizontalLayout();
        cardsLayout.setWidthFull();
        cardsLayout.addClassName("margen-top-small");
        cardsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        cardsLayout.add(movilCard, fibraCard, fijoCard);
        
        globalViewLayout.add(cardsLayout);
        
        // Bloque de relleno
        Paragraph conocenosParagraph = new Paragraph("¿Quieres saber más sobre nuestro equipo?");
        conocenosParagraph.setClassName("text-conocenos");

        Button conocenosButton = new Button("Sobre nosotros");
        conocenosButton.setClassName("boton-conocenos");
        conocenosButton.addClickListener(event -> {
            UI.getCurrent().navigate("aboutUs");
        });
        
        Div bloqueConocenosLayout = new Div();
        bloqueConocenosLayout.add(conocenosParagraph, conocenosButton);

        Div conocenosDiv = new Div();
        conocenosDiv.setClassName("div-conocenos");

        conocenosDiv.add(bloqueConocenosLayout);

        globalViewLayout.add(conocenosDiv);
        
        return globalViewLayout;
    }
}