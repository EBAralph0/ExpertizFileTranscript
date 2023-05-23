package com.expertiz.services;

import java.util.List;

import javax.ejb.Remote;

import com.expertiz.entities.User;


@Remote
public interface UserInterface {
	
	public User create(User u);
    public User update(User u);
    public void remove(int id);
    public User getUser(int id);
    public List<User> getAllUsers();
	public User LoginUser(String login, String password);
	public long count();

}
