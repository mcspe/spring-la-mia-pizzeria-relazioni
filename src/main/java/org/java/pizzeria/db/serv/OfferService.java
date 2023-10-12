package org.java.pizzeria.db.serv;

import java.util.List;

import org.java.pizzeria.db.pojo.Offer;
import org.java.pizzeria.db.repo.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepo offerRepo;
	
	public void save(Offer offer) {
		offerRepo.save(offer);
	}
	
	public List<Offer> findAll() {
		return offerRepo.findAll();
	}

	public Offer findById(int id) {
		return offerRepo.findById(id).get();
	}
	
	public void delete(Offer offer) {
		offerRepo.delete(offer);
	}

}
