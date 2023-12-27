package es.uca.iw.aplication.tables.usuarios;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Token {
    public final int expiracion = 60 * 24;

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "token")
    private String token;
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    private Date fecha;
    public Date getDate() { return fecha; }
    public void setDate(Date fecha) { this.fecha = fecha; }

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}