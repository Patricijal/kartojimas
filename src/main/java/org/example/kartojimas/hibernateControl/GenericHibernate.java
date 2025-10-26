/**
 * Helper klase, skirta darbui su duomenu baze t.y. perduosiu objektus, atidarysiu sesija, sesijos metu kazka atliksiu, baigsiu darba
 */

package org.example.kartojimas.hibernateControl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.scene.control.Alert;
import org.example.kartojimas.model.CreatureType;
import org.example.kartojimas.model.MagicalCreature;
import org.example.kartojimas.utils.FxUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    // generic method
    public <T> void create(T entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity); // INSERT
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Oh no", "DB error", "Something went wrong on insert");
//            FxUtils.generateExceptionAlert(Alert.AlertType.ERROR, "During INSERT", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> void update(T entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity); // UPDATE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateExceptionAlert(Alert.AlertType.ERROR, "During UPDATE", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> void delete(Class<T> entityClass, int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entityFromDb = entityManager.find(entityClass, id);
            entityManager.remove(entityFromDb); // DELETE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            FxUtils.generateExceptionAlert(Alert.AlertType.ERROR, "During DELETE", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> T getEntityById(Class<T> entity, int id) {
        T entityFromDb = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityFromDb = entityManager.find(entity, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // Klaidos atveju as panaudosiu Alertus
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return entityFromDb;
    }

    public <T> List<T> getAllRecords(Class<T> entityClass) {
        List<T> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            FxUtils.generateExceptionAlert(Alert.AlertType.ERROR, "During Get All Records", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }

    public List<MagicalCreature> getByCriteria(String wizard, String creatureTitle, CreatureType creatureType, LocalDate date) {
        List<MagicalCreature> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MagicalCreature> query = cb.createQuery(MagicalCreature.class);
            Root<MagicalCreature> root = query.from(MagicalCreature.class);

            query.select(root).where(cb.and(
                    cb.like(root.get("wizard"), "%" + wizard + "%"),
                    cb.like(root.get("title"), "%" + creatureTitle + "%"),
                    cb.equal(root.get("creatureType"), creatureType),
                    cb.greaterThan(root.get("dateFound"), date)
            ));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            // Handle exception (e.g., user not found)
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Oh no", "DB error", "Something went wrong getting Wizard by criteria");
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;
    }
}
