package com.expertiz.traitement;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.expertiz.entities.User;
import com.expertiz.services.UserInterface;

@ViewScoped
@ManagedBean
public class UserTraitement {
	@EJB
    private UserInterface userInterface;
	
	public void create(){
		System.out.println("ikdjksdjksdjksd");
        User u = new User();
        u.setLogin("login");
        u.setPassword("password");
        u.setNom("AAAAAAA");
        userInterface.create(u);
        
    }

}