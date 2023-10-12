package org.java.pizzeria.db.repo;

import org.java.pizzeria.db.pojo.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Integer> {

}
