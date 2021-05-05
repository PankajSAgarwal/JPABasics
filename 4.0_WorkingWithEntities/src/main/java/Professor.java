import javax.persistence.*;

@Entity
public class Professor {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "professor_generator")
    @SequenceGenerator(name="professor_generator",sequenceName = "professor_seq")
    private Long Id;
    private String firstName;
    private String lastName;

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

    public Long getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
