package es.uca.iw.views.about;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import es.uca.iw.views.MainLayout;

@PageTitle("Sobre Nosotros")
@Route(value = "SobreNosotros", layout = MainLayout.class)
public class AbaoutUsView extends VerticalLayout{
    public AbaoutUsView() {
        setSpacing(false);

        H1 header = new H1("Sobre MangoPhone");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);

        add(new Paragraph("MangoPhone es una destacada empresa en el sector de las telecomunicaciones, reconocida por su excelencia en servicios y su compromiso con la innovación. Como líder en el mercado, MangoPhone se posiciona como una compañía integral de telecomunicaciones, ofreciendo soluciones avanzadas en redes fijas, móviles y de banda ancha."));
        add(new Paragraph("Con una presencia global en diversos mercados, MangoPhone se destaca por su enfoque hacia la transformación digital, consolidándose como una verdadera 'Telco Digital'. Esta estrategia la sitúa de manera óptima para satisfacer las cambiantes necesidades de sus clientes y capitalizar el crecimiento en nuevos segmentos del mercado."));
        add(new Paragraph("Operando en múltiples países, MangoPhone atiende a una amplia base de clientes, superando los límites geográficos para ofrecer servicios de calidad. La compañía se enorgullece de su presencia sólida en regiones estratégicas, consolidando su posición en mercados clave de Europa, Latinoamérica y otros lugares, alineando su estrategia de crecimiento con las demandas específicas de cada región."));
        
        Image img = new Image("images/mango.png", "MangoPhoneIcon");
        img.setWidth("400px");
        add(img);

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}