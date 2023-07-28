package com.example.issproject.repository;

import com.example.issproject.models.AdminsEntity;
import com.example.issproject.models.SpectatorsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SpectatorRepo {
    public SpectatorRepo() {
    }

    public SpectatorsEntity findSpectator(String name, int age) {
        try {
//            testDriver();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                SpectatorsEntity spectator = em.createNamedQuery("SpectatorsEntity.findSpectator", SpectatorsEntity.class)
                        .setParameter("name", name)
                        .setParameter("age", age)
                        .getSingleResult();
                et.commit();
                return spectator;
            } catch (Exception e) {
                et.rollback();
//                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }

        } catch (Exception e) {
//            no admin found if here
//            e.printStackTrace();
        }
        return null;
    }

    public SpectatorsEntity addSpectator(String name, int age) {
        try {
//            testDriver();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                SpectatorsEntity spectator = new SpectatorsEntity();
                spectator.setName(name);
                spectator.setAge(age);
                em.persist(spectator);
                et.commit();
                return spectator;
            } catch (Exception e) {
                et.rollback();
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception e) {
            // could not save
            e.printStackTrace();
        }
        return null;
    }
}
