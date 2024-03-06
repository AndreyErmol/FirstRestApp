package ermolaev.models.abstractions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.DataScientist;
import ermolaev.models.impl.FrontendDeveloper;
import jakarta.persistence.*;

@Entity
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
    private int id;
    @Column(name = "workername")
    private String name;
    private String email;

    public Worker() {
    }

    public Worker(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract void doJob();

    @Override
    public String toString() {
        return "Worker{name: " + name + ", email: " + email + "}";
    }
}
