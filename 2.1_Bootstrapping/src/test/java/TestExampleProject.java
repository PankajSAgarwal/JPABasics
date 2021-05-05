import org.apache.log4j.Logger;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestExampleProject {
    Logger log = Logger.getLogger(this.getClass().getName());

    @Test
    public void bootstrap() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        // start the database transaction
        em.getTransaction().begin();

        // implement persistence logic

        // commit the database transaction
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
