package es.uca.iw.views.global;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import es.uca.iw.views.templates.MainLayout;

@PageTitle("Bienvenido")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class WelcomeView extends Div {

    public WelcomeView() {
        
        // Layout
        VerticalLayout globalViewLayout = new VerticalLayout();
        
        // Bloque titulo
        VerticalLayout titleLayout = new VerticalLayout();
        
        H1 globalTitle = new H1("Mango");
        globalTitle.addClassName("titulo-custom");
        
        titleLayout.add(globalTitle, new Paragraph("Tu compañia de confianza con precios tan frescos como un mango"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);
        
        // Bloque servicios
        HorizontalLayout servicesCardsLayout = new HorizontalLayout();
        VerticalLayout fibraCard = new VerticalLayout();
        VerticalLayout movilCard = new VerticalLayout();
        VerticalLayout fijoCard = new VerticalLayout();
        
        H1 servicesTitle = new H1("Nuestros servicios");
        
        // Fibra
        H2 fibraTitle = new H2("Fibra");
        Paragraph fibraDescription = new Paragraph("La fibra más rápida del mercado");
        Paragraph fibraPrice = new Paragraph("Desde 15€ / mes"); // Cambiar por llamada a base datos
        
        fibraCard.add(fibraTitle, fibraDescription, fibraPrice);
        fibraCard.addClassName("card-prueba");
        fibraCard.setJustifyContentMode(JustifyContentMode.CENTER);
        fibraCard.setAlignItems(Alignment.CENTER);

        // Movil
        H2 movilTitle = new H2("Movil");
        Paragraph movilDescription = new Paragraph("Las llamadas con mejor audio del planeta");
        Paragraph movilPrice = new Paragraph("Desde 7.99€ / mes"); // Cambiar por llamada a base datos
        
        movilCard.add(movilTitle, movilDescription, movilPrice);
        movilCard.addClassName("card-prueba");
        movilCard.setJustifyContentMode(JustifyContentMode.CENTER);
        movilCard.setAlignItems(Alignment.CENTER);

        // Fijo
        H2 fijoTitle = new H2("Fijo");
        Paragraph fijoDescription = new Paragraph("El télefono fijo sigue siendo una realidad");
        Paragraph fijoPrice = new Paragraph("Desde 2.50€ / mes"); // Cambiar por llamada a base datos
        
        fijoCard.add(fijoTitle, fijoDescription, fijoPrice);
        fijoCard.addClassNames("card-prueba");
        fijoCard.setJustifyContentMode(JustifyContentMode.CENTER);
        fijoCard.setAlignItems(Alignment.CENTER);
        
        servicesCardsLayout.add(fibraCard, movilCard, fijoCard);
        servicesCardsLayout.addClassName("card-flex-box");

        globalViewLayout.add(titleLayout, servicesTitle, servicesCardsLayout);

        add(globalViewLayout);

    }
}
