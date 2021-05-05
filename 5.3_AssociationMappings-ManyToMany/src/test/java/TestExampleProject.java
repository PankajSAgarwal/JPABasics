import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

/*
Unidirectional many-to-many association
 */
public class TestExampleProject {
    Logger log = Logger.getLogger(this.getClass().getName());
    private EntityManagerFactory emf;

    @Before
    public void init() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @After
    public void close() {
        emf.close();
    }

    @Test
    public void manageManyToManyAssociation() {
        log.info("... manageManyToManyAssociation ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId1 = createCourse();
        Long courseId2 = createCourse();
        Long studentId1 = createStudent();
        Long studentId2 = createStudent();

        // load Course entities by primary key
        Course course1 = em.find(Course.class, courseId1);
        Course course2 = em.find(Course.class, courseId2);

        // load Student entities by primary key
        Student student1 = em.find(Student.class, studentId1);
        Student student2 = em.find(Student.class, studentId2);

        // add the associations
        student1.getCourses().add(course1);

        student2.getCourses().add(course1);
        student2.getCourses().add(course2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void readAndUpdateAssociation() {
        log.info("... readAndUpdateAssociation ...");

        Long studentId = prepareTestData();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = em.find(Student.class, studentId);

        for (Course course : student.getCourses()) {
            log.info(course);
        }


        // unenroll student from course
        Course course = (Course) student.getCourses().toArray()[0];
        student.getCourses().remove(course);

        em.getTransaction().commit();
        em.close();
    }

    private Long createCourse() {
        return createCourse("Software Development 1");
    }

    private Long createCourse(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = new Course();
        course.setName(name);
        course.setStartDate(LocalDate.of(2018, 8, 15));
        course.setEndDate(LocalDate.of(2019, 5, 31));
        em.persist(course);

        em.getTransaction().commit();
        em.close();

        return course.getId();
    }

    private Long createStudent() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student s = new Student();
        s.setFirstName("Peter");
        s.setLastName("Doe");
        em.persist(s);

        em.getTransaction().commit();
        em.close();

        return s.getId();
    }

    private Long prepareTestData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId1 = createCourse();
        Long courseId2 = createCourse();
        Long studentId1 = createStudent();
        Long studentId2 = createStudent();

        // load Course entities by primary key
        Course course1 = em.find(Course.class, courseId1);
        Course course2 = em.find(Course.class, courseId2);

        // load Student entities by primary key
        Student student1 = em.find(Student.class, studentId1);
        Student student2 = em.find(Student.class, studentId2);

        // add the associations
        student1.getCourses().add(course1);

        student2.getCourses().add(course1);
        student2.getCourses().add(course2);

        em.getTransaction().commit();
        em.close();

        return course1.getId();
    }

}
