package com.naeng_biseo.naeng_biseo.repository;

import com.naeng_biseo.naeng_biseo.domain.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class).getResultList();
    }

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findByEmail(String email) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public Optional<User> findByEmailOptional(String email) {
        return Optional.ofNullable(findByEmail(email));
    }
    public Optional<User> findByUsernameOptional(String username) {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

}
