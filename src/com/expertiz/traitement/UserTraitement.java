package com.expertiz.traitement;

import java.io.IOException;
import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.expertiz.entities.User;
import com.expertiz.services.UserInterface;
import java.io.Serializable;

@ManagedBean(name="userTraitement")
@ViewScoped
public class UserTraitement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User selectedUserToDelete;
	private User selectedUser;
	public User getSelectedUserToDelete() {
		return selectedUserToDelete;
	}


	public void setSelectedUserToDelete(User selectedUserToDelete) {
		this.selectedUserToDelete = selectedUserToDelete;
	}


	public User getSelectedUser() {
		return selectedUser;
	}


	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
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


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	private List<User> allUsers;
	
	
	public List<User> getAllUsers() {
        if (allUsers == null) {
        	allUsers = userInterface.getAllUsers();
        }
        return allUsers;
    }
	
	public void createUser() {
	    if (userName == null || userLogin == null || userPassword == null || userName.isEmpty() || userLogin.isEmpty() || userPassword.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez remplir tous les champs."));
	        return;
	    }

	    User user = new User();
	    user.setNom(userName);
	    user.setLogin(userLogin);
	    user.setPassword(userPassword);

	    // Call the create method
	    userInterface.create(user);

	    // Reset field values
	    userName = null;
	    userLogin = null;
	    userPassword = null;

	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "L'utilisateur a été créé avec succès."));
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    try {
	    	PrimeFaces.current().ajax().update(":form:userTable"); // Refresh the user table
	        externalContext.redirect(externalContext.getRequestContextPath() + "/pages/user.xhtml");
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void selectUserForEdit(User user) {
	    this.selectedUser = user;
	}

	public void editUser(User user) {
	    userInterface.update(user);
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "L'utilisateur a été modifié avec succès."));

	    selectedUser = null;

	    PrimeFaces.current().ajax().update(":form:userTable");

	    PrimeFaces.current().executeScript("PF('editDialog').hide();");
	}

	public void deleteUser(User user) {
	    this.selectedUserToDelete = user;
	}

	public void confirmDeleteUser() {
	    if (this.selectedUserToDelete != null) {
	        userInterface.remove(selectedUserToDelete.getId());
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted successfully.", null));
	        this.selectedUserToDelete = null;

	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        try {
	            externalContext.redirect(externalContext.getRequestContextPath() + "/pages/user.xhtml");
	            PrimeFaces.current().ajax().update(":form:userTable"); // Refresh the user table
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


	public void login() {
		User user = userInterface.Login(userLogin, userPassword);
	    
	   if (user != null) {

		   try {
	            FacesContext context = FacesContext.getCurrentInstance();
	            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	            context.getExternalContext().redirect(request.getContextPath() + "/pages/home.xhtml");
	            
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
	
	
	public void edit() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = context.getExternalContext();
	    HttpSession session = (HttpSession) externalContext.getSession(false);
	    int userId = (int) session.getAttribute("userId");

	    User user = userInterface.getUser(userId);
	    user.setNom(userName);
	    user.setLogin(userLogin);
	    user.setPassword(userPassword);

	    userInterface.update(user);

	    // Message de succès
	    String successMessage = "Utilisateur mis à jour avec succès";
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", successMessage));

	    // Redirection vers la page d'accueil ou une autre page appropriée
	    try {
	        externalContext.redirect(externalContext.getRequestContextPath() + "/pages/home.xhtml");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void logout() {
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
	
}
	
