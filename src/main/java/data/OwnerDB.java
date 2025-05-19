package data;

import business.Customer;
import business.Owner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class OwnerDB {
    public static Owner getOwnerByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT o FROM Owner o where o.email = :email and o.password = :password";
        TypedQuery<Owner> q = em.createQuery(qString, Owner.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Owner owner = q.getSingleResult();
            return owner;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}


