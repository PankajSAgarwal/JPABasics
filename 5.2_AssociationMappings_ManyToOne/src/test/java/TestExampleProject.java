import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class TestExampleProject {

    Logger log = Logger.getLogger(this.getClass().getName());
    private EntityManagerFactory emf;

    @Before
    public void init(){
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @After
    public void close(){
        emf.close();
    }

    @Test
    public void manageManyToOneAssociation(){
        log.info("...manageManyToOneAssociation...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // get the primary key of a course and professor
        Long courseId = createCourse();
        Long profId = createProfessor();

        // load Course and Professor entities by primary key

        Course course = em.find(Course.class, courseId);
        Professor professor = em.find(Professor.class, profId);

        // add the association
        course.setProfessor(professor);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void useManyToOneAssociation() {
        log.info("... useManyToOneAssociation ...");

        Long courseId = prepareTestData();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = em.find(Course.class, courseId);

        log.info("Professor:");
        log.info(course.getProfessor());

        em.getTransaction().commit();
        em.close();
    }

    private Long prepareTestData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId = createCourse();
        Long profId = createProfessor();

        // load Course and Professor entities by primary key
        Course course = em.find(Course.class, courseId);
        Professor prof = em.find(Professor.class, profId);

        // add the association
        course.setProfessor(prof);

        em.getTransaction().commit();
        em.close();

        return course.getId();
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

    private Long createProfessor() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Professor prof = new Professor();
        prof.setFirstName("Jane");
        prof.setLastName("Doe");
        em.persist(prof);

        em.getTransaction().commit();
        em.close();

        return prof.getId();
    }
}
