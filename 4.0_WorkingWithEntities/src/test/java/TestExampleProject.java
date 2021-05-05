import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    public void persistProfessor(){
        log.info("... persist professor...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // create a new Professor entity
        Professor prof = new Professor();
        prof.setFirstName("Jane");
        prof.setLastName("Doe");
        // persist the professor entity
        em.persist(prof);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findProfessorById(){
        log.info("... findProfessorById...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a professor
        Long profId = createProfessor();
        // load Professor entity by primary key
        Professor prof = em.find(Professor.class, profId);

        log.info("Prof-->" + prof);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void updateProfessor(){
        log.info("... updateProfessor...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // get the primary key of a professor
        Long profId= createProfessor();

        // load Professor entity by primary key
        Professor prof = em.find(Professor.class,profId);

        // update one or more attributes
        // this should trigger a sql update on flush
        prof.setFirstName("Marie");
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void detachProfessor(){
        log.info("...detach professor...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // get the primary key of a Professor
        Long profId = createProfessor();
        // load Professor entity by primary key
        Professor prof = em.find(Professor.class, profId);
        // detach the Professor entity
        em.detach(prof);
        // update one or more attributes
        // this will not trigger any sql update as persistence context is detached
        prof.setFirstName("Marie");
        em.getTransaction().commit();
        em.close();
    }
    @Test
    public void mergeProfessor(){
        log.info("...merge professor...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // get the primary key of a Professor
        Long profId = createProfessor();
        // load Professor entity by primary key
        Professor prof = em.find(Professor.class,profId);
        // detach the Professor entity
        em.detach(prof);
        // update one or more attributes
        prof.setFirstName("Marie");
        log.info("Merge Professor entity");
        // re-attach the Professor entity
        em.merge(prof);

        em.getTransaction().commit();
        em.close();
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
