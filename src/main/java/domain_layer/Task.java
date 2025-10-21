package domain_layer;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    @XmlAttribute(name = "id")
    private String numero;

    @XmlElement(name = "description")
    private String descripcion;

    @XmlElement(name = "dueDate")
    private String fechaFinalizacionStr;

    @XmlElement(name = "priority")
    private Priority prioridad;

    @XmlElement(name = "status")
    private Status estado;

    @XmlElement(name = "responsible")
    private User responsable;

    @XmlTransient
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public Task() {}

    public Task(String numero, String descripcion, LocalDate fechaFinalizacion, Priority prioridad, Status estado, User responsable) {
        this.numero = numero;
        this.descripcion = descripcion;
        setFechaFinalizacion(fechaFinalizacion);
        this.prioridad = prioridad;
        this.estado = estado;
        this.responsable = responsable;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaFinalizacion() {
        return (fechaFinalizacionStr == null || fechaFinalizacionStr.isEmpty())
                ? null
                : LocalDate.parse(fechaFinalizacionStr, FORMATTER);
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacionStr = (fechaFinalizacion == null)
                ? null
                : fechaFinalizacion.format(FORMATTER);
    }

    public Priority getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Priority prioridad) {
        this.prioridad = prioridad;
    }

    public Status getEstado() {
        return estado;
    }

    public void setEstado(Status estado) {
        this.estado = estado;
    }

    public User getResponsable() {
        return responsable;
    }

    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }

    public enum Priority {
        ALTA, MEDIA, BAJA
    }

    // java
    public enum Status {
        ABIERTA("Abierta"),
        EN_PROGRESO("En-progreso"),
        EN_REVISION("En-revisión"),
        RESUELTA("Resuelta");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

}



