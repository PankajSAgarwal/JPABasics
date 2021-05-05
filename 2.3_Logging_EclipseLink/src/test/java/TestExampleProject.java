import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
    public void createProfessor(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Persist a new Professor
        Professor prof = new Professor();
        prof.setId(1L);
        prof.setFirstName("Jane");
        prof.setLastName("Doe");
        em.persist(prof);

        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();

        // Execute a query
        TypedQuery<Professor> q = em.createQuery(
                "SELECT p FROM Professor p where p.firstName = :fname", Professor.class);
        q.setParameter("fname","Jane");
        prof = q.getSingleResult();

        // update the entity
        prof.setFirstName("Marie");

        em.getTransaction().commit();
        em.close();

    }

}