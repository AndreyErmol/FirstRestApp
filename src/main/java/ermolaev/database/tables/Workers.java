package ermolaev.database.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Workers")
public class Workers {
    private Integer id;
    private String workerName;
    private String email;
    private String position;
}
