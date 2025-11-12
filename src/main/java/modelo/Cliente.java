package modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Cliente {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id_cliente;

    @Column(name = "nombre_cliente", length = 50, nullable = false)
    private String nombre;


    @Column(name = "apellido_cliente", length = 50, nullable = false)
    private  String apellido;

    @Column(name = "cedula_cliente", length = 18, nullable = false, unique = true)
    private String cedula;

    @Column(name = "telefono_cliente", length = 12, nullable = false)
    private String Telefono;

    @Column(name = "direccion_cliente", length = 100, nullable = false)
    private String direccion;
}