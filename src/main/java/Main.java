import model.Game;
import model.Score;
import model.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class Main {

    private static final EntityManagerFactory emf;

    static {
        Properties p = new Properties();
        p.setProperty("hibernate.connection.url", "jdbc:h2:mem:wtngservlet");
        p.setProperty("hibernate.connection.username", "sa");
        p.setProperty("hibernate.connection.password", "sa");
        p.setProperty("hibernate.connection.useUnicode", "true");
        p.setProperty("hibernate.c3p0.min_size", "3");
        p.setProperty("hibernate.c3p0.max_size", "15");
        p.setProperty("hibernate.c3p0.timeout", "300");
        p.setProperty("hibernate.c3p0.max_statements", "50");
        p.setProperty("hibernate.c3p0.idle_test_period", "3000");
        p.setProperty("hibernate.default_batch_fetch_size", "32");
        p.setProperty("hibernate.show_sql", "true");
        p.setProperty("hibernate.format_sql", "true");
        p.setProperty("hibernate.hbm2ddl.auto", "create");

        emf = Persistence.createEntityManagerFactory("test", p);
    }

    public static void main(String[] args) {
        System.out.println("Started!");

        testHHH10741();

        emf.close();
    }

    public static void testHHH10741() {
        // this is the setup code
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Team home = new Team(),
            away = new Team();

        home.setName("home");
        away.setName("away");

        em.persist(home);
        em.persist(away);

        Game g = new Game();
        g.setHomeTeam(home);
        g.setAwayTeam(away);
        em.persist(g);

        Score homeScore = new Score(),
            awayScore = new Score();
        homeScore.setGame(g);
        homeScore.setTeam(home);

        awayScore.setGame(g);
        awayScore.setTeam(away);

        em.persist(homeScore);
        em.persist(awayScore);

        em.flush();
        em.getTransaction().commit();
        em.close();

        // this is the test code
        em = emf.createEntityManager();

        g = em.find(Game.class, g.getId());

        em.close();

        assert g.getHomeScore() != null && g.getHomeScore().getId() != null && g.getHomeScore() instanceof Score;
        assert g.getHomeTeam() != null && g.getHomeTeam().getId() != null && "home".equals(g.getHomeTeam().getName()) &&
            g.getHomeTeam() instanceof Team;
    }
}
