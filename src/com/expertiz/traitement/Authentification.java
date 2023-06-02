package com.expertiz.traitement;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.expertiz.entities.User;
import com.expertiz.services.UserInterface;

@ManagedBean(name="authentification")
@SessionScoped
public class Authentification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Authentification() {
		// TODO Auto-generated constructor stub
	}
	
	@EJB
    private UserInterface userInterface;
	
	private String userLogin;
	private int userId;
    private String userPassword;
    private String userName;
    
    
    
	public String getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void login() {
		User user = userInterface.Login(userLogin, userPassword);
		if (user != null) {

		   try {
	            FacesContext context = FacesContext.getCurrentInstance();
	            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	            context.getExternalContext().redirect(request.getContextPath() + "/pages/setting.xhtml");
	            
	            ExternalContext externalContext = context.getExternalContext();
	            HttpSession session = (HttpSession) externalContext.getSession(false);
	            
	            // Sauvegarde de l'id de l'utilisateur et du nom dans la session
	            session.setAttribute("userId", user.getId());
	            session.setAttribute("userName", user.getNom());
	            
	            userName = (String) session.getAttribute("userName");
	            System.out.println("Nom de l'utilisateur connecté : " + userName);
	            
	            userLogin = user.getLogin();
	            userPassword = user.getPassword();
	            
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
	
	
	public void logout() {
		System.out.println("55555555555555555555555");
	    try {
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        HttpSession session = (HttpSession) externalContext.getSession(false);
	        session.removeAttribute("userName");
	        session.invalidate();
	        externalContext.redirect(externalContext.getRequestContextPath() + "/pages/connexion.xhtml");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public User getAllUserInfo(int id) {
		return userInterface.getUser(id);
	}

}
