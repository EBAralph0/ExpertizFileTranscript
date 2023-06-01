package com.expertiz.traitement;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.RequestContext;
import org.primefaces.PrimeFaces;

import com.expertiz.entities.Setting;
import com.expertiz.services.SettingInterface;

@ManagedBean(name="serviceTraitement")
@ViewScoped
public class ServiceTraitement implements Serializable {
    private Setting selectedSettingToDelete;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceTraitement() {
	}
	private Setting selectedSetting;
	@EJB
	private SettingInterface settingInterface;
	
	private int id;
	private String cle;
	private String valeur;
	
	
	public Setting getSelectedSetting() {
        return selectedSetting;
    }

    public void setSelectedSetting(Setting selectedSetting) {
        this.selectedSetting = selectedSetting;
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCle() {
		return cle;
	}
	public void setCle(String cle) {
		this.cle = cle;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	private String selectedCle;

	public String getSelectedCle() {
	    return selectedCle;
	}

	public void setSelectedCle(String selectedCle) {
	    this.selectedCle = selectedCle;
	}
	
	
	public Setting getSelectedSettingToDelete() {
		return selectedSettingToDelete;
	}

	public void setSelectedSettingToDelete(Setting selectedSettingToDelete) {
		this.selectedSettingToDelete = selectedSettingToDelete;
	}
	
	private List<Setting> allSettings;
	
	

    public List<Setting> getAllSettings() {
        if (allSettings == null) {
            allSettings = settingInterface.getAllSetting();
        }
        return allSettings;
    }
	public void createSetting() {
	    if (cle == null || valeur == null || cle.isEmpty() || valeur.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Veuillez remplir tous les champs."));
	        return;
	    }
	    Setting setting = new Setting();
	    setting.setCle(cle);
	    setting.setValeur(valeur);

	    // Appeler la méthode de création
	    settingInterface.create(setting);
	    // Réinitialiser les valeurs des champs
	    cle = null;
	    valeur = null;

	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Le setting a été créé avec succès."));
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/pages/setting.xhtml");
            PrimeFaces.current().ajax().update(":form:settingTable"); // Rafraîchir le tableau des réglages
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void selectSettingForEdit(Setting setting) {
	    this.selectedSetting = setting;
	}

    public void editSetting(Setting setting) {
        settingInterface.update(setting);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Le réglage a été modifié avec succès."));

        selectedSetting = null;

        PrimeFaces.current().ajax().update(":form:settingTable");

        // Cacher le dialogue d'édition
        PrimeFaces.current().executeScript("PF('editDialog').hide();");

    }

    public void deleteSetting(Setting setting) {
        this.selectedSettingToDelete = setting;
    }

    public void confirmDeleteSetting() {
        if (this.selectedSettingToDelete != null) {
            System.out.println("---------------------" + selectedSettingToDelete.getCle());
            settingInterface.remove(selectedSettingToDelete.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Setting deleted successfully.", null));
            this.selectedSettingToDelete = null;
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/pages/setting.xhtml");
                PrimeFaces.current().ajax().update(":form:settingTable"); // Rafraîchir le tableau des réglages
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("******************************");
        }
    }


    

}
