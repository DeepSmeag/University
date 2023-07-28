package com.example.issproject.repository;

import com.example.issproject.models.SeatsEntity;

import javax.persistence.EntityManagerFactory;

public class SeatRepo {
    public SeatRepo() {}
    public SeatsEntity findSeat(int seatid) {
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
            javax.persistence.EntityManager em = emf.createEntityManager();
            javax.persistence.EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                SeatsEntity seat = em.createNamedQuery("SeatsEntity.findSeat", SeatsEntity.class)
                        .setParameter("seatid", seatid)
                        .getSingleResult();
                et.commit();
                return seat;
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
