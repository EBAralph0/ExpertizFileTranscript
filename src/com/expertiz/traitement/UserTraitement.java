package com.expertiz.traitement;

import java.io.IOException;
import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import com.expertiz.entities.User;
import com.expertiz.services.UserInterface;
import java.io.Serializable;

@ManagedBean(name="userTraitement")
@SessionScoped
public class UserTraitement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
    private UserInterface userInterface;
	
	private String userLogin;
    private String userPassword;

	
	public String getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public void login() {
		System.out.println("uioiojiklj");
		User user = userInterface.Login(userLogin, userPassword);
	    
	   if (user != null) {
	    	try {
	            FacesContext context = FacesContext.getCurrentInstance();
	            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	            context.getExternalContext().redirect(request.getContextPath() + "/pages/home.xhtml");
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	    } else {
	    	 // Erreur de connexion, affichage du message d'erreur
	        String errorMessage = "Identifiant ou mot de passe incorrect";
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connexion", errorMessage));
	        
	        // Appel d'une fonction JavaScript pour afficher le message d'erreur
	        String script = "showErrorMessage('" + errorMessage + "');";
	        PrimeFaces.current().executeScript(script);
	    }
    }
}