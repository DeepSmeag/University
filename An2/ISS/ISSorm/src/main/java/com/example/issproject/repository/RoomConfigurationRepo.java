package com.example.issproject.repository;

import com.example.issproject.models.RoomconfigurationsEntity;

import javax.persistence.EntityManagerFactory;

public class RoomConfigurationRepo {
    public RoomConfigurationRepo() {
    }

    public RoomconfigurationsEntity retrieveConfig(int roomid) {
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
            javax.persistence.EntityManager em = emf.createEntityManager();
            javax.persistence.EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                RoomconfigurationsEntity roomconfig = em.createNamedQuery("RoomconfigurationsEntity.retrieveConfig", RoomconfigurationsEntity.class)
                        .setParameter("roomid", roomid)
                        .getSingleResult();
                et.commit();
                return roomconfig;
            } catch (Exception e) {
                et.rollback();
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
