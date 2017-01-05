import model.embeddabletwice.EmbeddableOne;
import model.embeddabletwice.EmbeddableTwo;
import model.embeddabletwice.Parent;
import model.enumembeddablemap.Competition;
import model.enumembeddablemap.Participant;
import model.hhh10741.Game;
import model.hhh10741.Result;
import model.hhh10741.Score;
import model.hhh10741.Team;
import model.onetomanytomany.Player;
import model.onetomanytomany.PlayerTourney;
import model.onetomanytomany.Tourney;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Tests {

    private static final EntityManagerFactory emf;

    static {
        Properties p = new Properties();
        p.setProperty("hibernate.connection.url", "jdbc:h2:mem:hibernatetests");
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

    @Test
    public static void testEnumEmbeddableMap() {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction etx = em.getTransaction();

        // create a competition with a red and blue
        Competition c = new Competition();
        c.setParticipants(new HashMap<>());
        c.getParticipants().put(Competition.Side.RED, new Participant("Me", 1));
        c.getParticipants().put(Competition.Side.BLUE, new Participant("You", 2));

        etx.begin();
        em.persist(c);
        em.flush();
        etx.commit();

        em.refresh(c);

        // participants are correctly persisted
        assert c.getParticipants().get(Competition.Side.RED).getName().equals("Me");
        assert c.getParticipants().get(Competition.Side.BLUE).getName().equals("You");


        // now put a participant with null values in the key RED
        etx.begin();
        c.getParticipants().put(Competition.Side.RED, new Participant(null, null));
        em.flush();
        etx.commit();

        em.refresh(c);

        // this is failing: https://hibernate.atlassian.net/browse/HHH-11353
        assert c.getParticipants().containsKey(Competition.Side.RED);
    }

    @Test(enabled = false)
    public static void testDoubleEmbeddable() {
        final EntityManager em = emf.createEntityManager();

        Parent p = new Parent();
        p.setId(1);
//        p.setOne(new HashSet<>());
        EmbeddableOne e1 = new EmbeddableOne();
//        p.getOne().add(e1);

        EmbeddableTwo e2 = new EmbeddableTwo();
        e1.setOneString("hello");
        e1.setTwo(e2);
        e2.setTwoString("world");

        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Parent> pQ = cb.createQuery(Parent.class);
        final List<Parent> query = em.createQuery(pQ.select(pQ.from(Parent.class))).getResultList();

        assert query.size() == 1;
        assert query.get(0).getId().equals(1);
    }

    @Test
    public static void testOneToManyToMany() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Tourney t = new Tourney();
        t.setName("My Tourney");
        em.persist(t);
        em.flush();

        Team team = new Team();
        team.setName("player team");
        em.persist(team);
        em.flush();

        Player p = new Player();
        p.setName("Moody");

        PlayerTourney pt = new PlayerTourney();
        pt.setTourney(t);
        pt.setTeams(Arrays.asList(team));

        p.setTourneys(Arrays.asList(pt));

        em.persist(p);
        em.flush();
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();

        p = em.find(Player.class, p.getId());

        em.close();

        assert p.getTourneys().size() == 1;
        assert p.getTourneys().get(0).getTeams().size() == 1;
        team = p.getTourneys().get(0).getTeams().get(0);
        assert team.getName().equals("player team");
    }

    @Test
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

        Result r = new Result();
        r.setGame(g);
        em.persist(r);

        Score homeScore = new Score(),
            awayScore = new Score();
        homeScore.setResult(r);
        homeScore.setTeam(home);

        awayScore.setResult(r);
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
