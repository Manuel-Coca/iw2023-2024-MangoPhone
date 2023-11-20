package es.uca.iw.aplication.tables.usuarios;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ROOT")
public class Root extends Usuario {

}
