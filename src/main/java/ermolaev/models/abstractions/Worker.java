package ermolaev.models.abstractions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.DataScientist;
import ermolaev.models.impl.FrontendDeveloper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "workers")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "position")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BackendDeveloper.class, name = "backend"),
        @JsonSubTypes.Type(value = FrontendDeveloper.class, name = "frontend"),
        @JsonSubTypes.Type(value = DataScientist.class, name = "datascientist")
})
public abstract class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "workername", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public abstract void doJob();

    public Worker(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime + ((name == null) ? 0 : name.hashCode());
        hash = hash * prime + (email == null ? 0 : email.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        Worker workerToCompare = (Worker) obj;

        return (Objects.equals(workerToCompare.name, this.name) &&
                Objects.equals(workerToCompare.email, this.email));
    }

    @Override
    public String toString() {
        var className = "Worker";

        if (getClass().equals(BackendDeveloper.class)) {
            className = "Backend developer";
        } else if (getClass().equals(FrontendDeveloper.class)) {
            className = "Frontend developer";
        } else if (getClass().equals(DataScientist.class)) {
            className = "Data scientist";
        }
        return className + " {name: " + name + ", email: " + email + "}";
    }
}
