package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entities.Cours;
import entities.Etudiant;
import sessionejb.GestionCoursEJB;

@Named
@SessionScoped
public class CoursController implements Serializable{
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
   private GestionCoursEJB gestionCours;
   private List<Cours> listeCours = new ArrayList<Cours>();
   private Cours cours;
   private String section; 
   
   public CoursController() {}
   
   public void init() {
       cours = new Cours();
   }
   
   public Cours getCours() {
       if(cours == null) {
           init();
       }
       return cours;
   }
   
	public String getSection() {
		return section;
	}

	public String setSection(String section) {
		this.section = section;
		return "ListeCours.xhtml?face-redirect=true";
	}

   public void setCours(Cours cours) {
       this.cours = cours;
   }

   public List<Cours> getListeCours(String section){
       listeCours = gestionCours.selectCoursSection(section);
       return listeCours;
   }
   
   public String deleteCours(Cours c) {
	   FacesContext context = FacesContext.getCurrentInstance();
       gestionCours.deleteCours(c);
       listeCours.remove(c);
       context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cours supprimé !", null));
       return "ListeCours.xhtml?face-redirect=true";
   }
   
   
 /*  public void updateSeminaire() {
       gestionSeminaire.updateSeminaire(seminaire);
   }
   */
   public String addCours() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(!listeCours.contains(cours)) {
			cours.setSection(section);
			gestionCours.addCours(cours);
		
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cours créé !", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "ListeCours.xhtml?face-redirect=true";
		}
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cours déja existant !", null));
		return null;

   }
   
   public String navAjouterTutorat(Cours c) {
	   cours = c;
	   return "AjouterTutorat.xhtml?face-redirect=true";
   }

   
	public void clearNom() {
		init();
		cours.setNom(null);
	}
}