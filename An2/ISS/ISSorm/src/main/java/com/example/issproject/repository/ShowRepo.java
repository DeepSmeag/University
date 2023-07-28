package com.example.issproject.repository;

import com.example.issproject.models.ShowsEntity;

import javax.persistence.EntityManagerFactory;

public class ShowRepo {
    public ShowRepo() {
    }
    public ShowsEntity getCurrentShow() {
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
            javax.persistence.EntityManager em = emf.createEntityManager();
            javax.persistence.EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                ShowsEntity show = em.createNamedQuery("ShowsEntity.getCurrentShow", ShowsEntity.class)
                        .getSingleResult();
                et.commit();
                return show;
            } catch (Exception e) {
                et.rollback();
            } finally {
                em.close();
                emf.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
