package com.example.issproject.repository;


import com.example.issproject.models.AdminsEntity;
import java.sql.*;
import javax.persistence.*;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;


public class AdminRepo {

    public AdminRepo() {
    }
    public void testDriver() {
        String url = "jdbc:postgresql://localhost:5432/ISS";
        String user = "postgres";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to PostgreSQL database!");
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Driver name: " + metaData.getDriverName());
            System.out.println("Driver version: " + metaData.getDriverVersion());
            System.out.println("Database product name: " + metaData.getDatabaseProductName());
            System.out.println("Database product version: " + metaData.getDatabaseProductVersion());
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
    public AdminsEntity findAdmin(String username, String password) {
        try {
//            testDriver();

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
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
//                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }

        }
        catch (Exception e) {
//            no admin found if here
            e.printStackTrace();
        }
        return null;
    }
}
