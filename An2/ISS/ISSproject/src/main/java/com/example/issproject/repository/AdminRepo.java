package com.example.issproject.repository;





import com.example.issproject.model.AdminsEntity;
import java.sql.*;
import javax.persistence.*;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;


public class AdminRepo {

    public AdminRepo() {
    }

    public AdminsEntity findAdmin(String username, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ISSproject.main");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            AdminsEntity admin = em.createNamedQuery("AdminsEntity.findAdmin", AdminsEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            et.commit();
            return admin;
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
            emf.close();
        }
        return null;
    }
}
