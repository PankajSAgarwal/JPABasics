import javax.persistence.*;

@Entity
public class Professor {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Sequence is preferred strategy for JPA
    @SequenceGenerator(name="professor_generator", sequenceName = "professor_seq")
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "professor_generator")
    //@TableGenerator(name = "professor_generator", table = "sequence_table")
    private Long Id;
    private String firstName;
    private String lastName;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
