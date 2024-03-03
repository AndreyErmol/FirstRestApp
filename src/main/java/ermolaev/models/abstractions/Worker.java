package ermolaev.models.abstractions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ermolaev.models.impl.BackendDeveloper;
import ermolaev.models.impl.DataScientist;
import ermolaev.models.impl.FrontendDeveloper;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "position")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BackendDeveloper.class, name = "backend"),
        @JsonSubTypes.Type(value = FrontendDeveloper.class, name = "frontend"),
        @JsonSubTypes.Type(value = DataScientist.class, name = "datascience")
})
public abstract class Worker {
    private String name;
    private String email;

    public Worker(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Worker() {
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

    @Override
    public String toString() {
        return "Worker{name: " + name + ", email: " + email + "}";
    }

    public abstract void doJob();
}
