package DAO;

import business.*;
import business.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PersonDao {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("employeePU");

    // Method to save a new person
    public void savePerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error saving person: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    public Person getPersonByIdOrDefault(Long personID) {
        System.out.println("Fetching Person ID: " + personID);
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, personID);
            if (person == null) {
                // Trả về giá trị mặc định
                return new Person("Default Name", "default@example.com", "12345678");
            }
            return person;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Method to update an existing person
    public void updatePerson(Person person) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error updating person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Method to delete a person by ID
    public void deletePerson(Long personID) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personID);
            if (person != null) {
                em.remove(person);
                em.getTransaction().commit();
            } else {
                System.out.println("Person not found with ID: " + personID);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error deleting person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Method to retrieve a person by ID
    public Person getPersonById(Long personID) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Person.class, personID);
        } catch (Exception e) {
            System.out.println("Error retrieving person: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
}
