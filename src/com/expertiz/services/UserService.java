package com.expertiz.services;

import com.expertiz.entities.User;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

/**
 * Session Bean implementation class UserService
 */
@Stateless(mappedName = "userService")
public class UserService implements UserInterface {
	@PersistenceContext
	EntityManager em;
	
    /**
     * @see User#User()
     */
    public UserService() {
    }

	@Override
	public User create(User u) {
		em.persist(u);
        return u;
	}

	@Override
	public User update(User u) {
		 em.merge(u);
	     return u;
	}

	@Override
	public void remove(int id) {
		 em.remove(getUser(id));
		
	}

	@Override
	public User getUser(int id) {
		return em.find(User.class, id);
	}

	@Override
	public List<User> getAllUsers() {
		 return em.createNamedQuery("User.getAll", User.class).getResultList();
	}

	@Override
	public User LoginUser(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
