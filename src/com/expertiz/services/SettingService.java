package com.expertiz.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

import com.expertiz.entities.Setting;

/**
 * Session Bean implementation class SettingService
 */
@Stateless(mappedName = "settingService")
public class SettingService implements SettingInterface {
	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public SettingService() {
    }

	@Override
	public Setting create(Setting s) {
		em.persist(s);
        return s;
	}

	@Override
	public Setting update(Setting s) {
		em.merge(s);
	     return s;
	}

	@Override
	public void remove(int id) {
		 em.remove(getSetting(id));
		
	}

	@Override
	public Setting getSetting(int id) {
		return em.find(Setting.class, id);
	}

	@Override
	public List<Setting> getAllSetting() {
		return em.createNamedQuery("Setting.getAll", Setting.class).getResultList();
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Setting> searchSettings(String keyword) {
		TypedQuery<Setting> query = em.createQuery("SELECT s FROM Setting s WHERE s.cle LIKE :keyword", Setting.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
		
	}
    
    

}
