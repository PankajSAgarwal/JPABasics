import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

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
    public void findToMany() {
        Long profId = prepareToMany();

        log.info("... findToMany ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Professor prof = em.find(Professor.class, profId);
        log.info("Professor: "+prof);
        log.info("Use relationship");
        for (Course course : prof.getCourses()) {
            log.info(course);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void queryToMany() {
        Long profId = prepareToMany();

        log.info("... findToMany ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Professor> q = em.createQuery("SELECT p FROM Professor p WHERE p.id = :id", Professor.class);
        q.setParameter("id", profId);
        Professor prof = q.getSingleResult();
        log.info("Professor: "+prof);
        log.info("Use relationship");
        for (Course course : prof.getCourses()) {
            log.info(course);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findToOne() {
        Long courseId = prepareToOne();

        log.info("... findToOne ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = em.find(Course.class, courseId);
        log.info("Course: "+course);
        log.info("Use relationship");
        log.info("Professor: "+course.getProfessor());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void queryToOne() {
        Long courseId = prepareToOne();

        log.info("... findToOne ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c WHERE c.id = :id", Course.class);
        q.setParameter("id", courseId);
        Course course = q.getSingleResult();
        log.info("Course: "+course);
        log.info("Use relationship");
        Professor prof = course.getProfessor();
        log.info("Professor: "+prof);

        em.getTransaction().commit();
        em.close();
    }

    private Long prepareToMany() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId1 = createCourse();
        Long courseId2 = createCourse();
        Long profId = createProfessor();

        // load Course and Professor entities by primary key
        Course course1 = em.find(Course.class, courseId1);
        Course course2 = em.find(Course.class, courseId2);
        Professor prof = em.find(Professor.class, profId);

        // add the associations
        prof.getCourses().add(course1);
        course1.setProfessor(prof);
        prof.getCourses().add(course2);
        course2.setProfessor(prof);

        em.getTransaction().commit();
        em.close();

        return prof.getId();
    }

    private Long prepareToOne() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId = createCourse();
        Long profId = createProfessor();

        // load Course and Professor entities by primary key
        Course course = em.find(Course.class, courseId);
        Professor prof = em.find(Professor.class, profId);

        // add the associations
        prof.getCourses().add(course);
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
