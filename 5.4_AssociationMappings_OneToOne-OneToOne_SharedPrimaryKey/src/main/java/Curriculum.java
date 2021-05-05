import javax.persistence.*;

@Entity
public class Curriculum {
    @javax.persistence.Id
    private Long Id;

    private String description;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Course course;

    @Version
    private int version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return Id;
    }

    public int getVersion() {
        return version;
    }
}
