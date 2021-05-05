import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
    @SequenceGenerator(name="student_generator", sequenceName = "student_seq")
    private Long Id;
    private String firstName;
    private String lastName;
    //@Enumerated(EnumType.ORDINAL) // preferred and default
    @Enumerated(EnumType.STRING)
    private StudentStatus state;
    @Version
    private long version;

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

    public StudentStatus getState() {
        return state;
    }

    public void setState(StudentStatus state) {
        this.state = state;
    }

    public Long getId() {
        return Id;
    }

    public long getVersion() {
        return version;
    }
}
