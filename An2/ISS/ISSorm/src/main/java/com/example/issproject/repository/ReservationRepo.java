package com.example.issproject.repository;

import com.example.issproject.models.ReservationsEntity;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ReservationRepo {
    public ReservationRepo() {
    }

    public List<ReservationsEntity> getReservations() {
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
            javax.persistence.EntityManager em = emf.createEntityManager();
            javax.persistence.EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                List<ReservationsEntity> reservations = em.createNamedQuery("ReservationsEntity.getReservations", ReservationsEntity.class)
                        .getResultList();
                et.commit();
                return reservations;
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

    public ReservationsEntity addReservation(int spectatorid, int showid, String seatsid) {
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("default");
            javax.persistence.EntityManager em = emf.createEntityManager();
            javax.persistence.EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                ReservationsEntity reservation = new ReservationsEntity();
                reservation.setSpectatorid(spectatorid);
                reservation.setShowid(showid);
                reservation.setSeatsid(seatsid);
                em.persist(reservation);
                et.commit();
                return reservation;
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
