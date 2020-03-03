import Entities.BasicLabel;
import Entities.Ingredients.AmmoniumChloride;
import Entities.Ingredients.BasicIngredient;
import Entities.Ingredients.Mint;
import Entities.Ingredients.Nettle;
import Entities.Shampoos.BasicShampoo;
import Entities.Shampoos.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Engine implements Runnable{
    private EntityManager em;
    private BufferedReader br;

    public Engine(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        this.em = emf.createEntityManager();
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        this.em.getTransaction().begin();
        this.execute();
        this.em.getTransaction().commit();
        this.em.close();
    }

    private void execute(){
        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Fresh Nuke Shampoo", "Contains mint and nettle");

        BasicShampoo shampoo = new FreshNuke(label);

        shampoo.getIngredients().add(am);
        shampoo.getIngredients().add(mint);
        shampoo.getIngredients().add(nettle);

        this.em.persist(shampoo);
    }
}
