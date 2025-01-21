package com.naeng_biseo.naeng_biseo.repository;


import com.naeng_biseo.naeng_biseo.domain.Auth;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public Auth save(Auth auth){
        em.persist(auth);
        return auth;
    }
}
