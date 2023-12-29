package es.uca.iw.aplication.tables.usuarios;

import java.util.UUID;
import java.util.List;

import es.uca.iw.aplication.tables.Contrato;
import jakarta.persistence.*;

@Entity
@Table(name="CuentaUsuario")
public class CuentaUsuario {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     @Column(name = "id")
     private UUID id = null;
     public UUID getId() { return id; }

    private boolean roaming;
    public boolean getRoaming() { return roaming; }
    public void setRoaming(boolean roaming) { this.roaming = roaming; }

    private List<String> numerosBloqueados;
    public List<String> getNumerosBloqueados() { return numerosBloqueados; }
    public void setNumerosBloqueados(List<String> numerosBloqueados) { this.numerosBloqueados = numerosBloqueados; }

    @OneToOne
    private Usuario duennoCuenta;
    public Usuario getDuennoCuenta() { return duennoCuenta; }
    public void setDuennoCuenta(Usuario duennoCuenta) { this.duennoCuenta = duennoCuenta; }

    @OneToOne
    private Contrato contrato = null;
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
}
