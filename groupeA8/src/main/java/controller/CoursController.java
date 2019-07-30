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
import entities.Tutorat;
import sessionejb.GestionCoursEJB;
import sessionejb.GestionRemediationEJB;
import sessionejb.GestionTutoratEJB;

@Named
@SessionScoped
public class CoursController implements Serializable{
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCours= 0;
	private boolean visibleAdd = true;
	private boolean visibleDelete = false;
	@EJB
   private GestionCoursEJB gestionCours;
	@EJB
   private GestionTutoratEJB gestionTutorat;
	@EJB
   private GestionRemediationEJB gestionRemediation;
   private List<Cours> listeCours = new ArrayList<Cours>();
   private Cours cours;
   private Tutorat tutorat;
   private String section; 
   
   public CoursController() {}
   
   public void init() {
       cours = new Cours();
       tutorat = new Tutorat();
   }
   
   public Cours getCours() {
       if(cours == null) {
           init();
       }
       return cours;
   }
   
   public Tutorat getTutorat() {
       if(tutorat == null) {
           init();
       }
       return tutorat;
   }
   
   public boolean isVisibleAdd() {
   		return visibleAdd;
   }
   
   public void setIdCours(int id) {
	   this.idCours = id;
   }
   
   public boolean isVisibleDelete() {
   		return visibleDelete;
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
       gestionTutorat.deleteTutoratCours(c.getId());
       
       //Récupérer la liste des id tutot à supprimer et supprimer toutes les remédiations
       
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
   
   public void verifEtudiantInscrit(Etudiant etu) {

   	int res = gestionTutorat.etudiantInscrit(etu.getSection(),etu.getId(),cours.getId());
   	if(res == 0) {
   		visibleAdd = true;
   		visibleDelete = false;
   	}else {
   		visibleDelete = true;
   		visibleAdd = false;
   	}
   }

   
   public String deleteTutorat(Etudiant e) {
	   FacesContext context = FacesContext.getCurrentInstance();
	   
	   tutorat = new Tutorat();
	   tutorat.setEtuId(e.getId());
	   tutorat.setIdCours(cours.getId());
	   
	   gestionTutorat.deleteTutorat(tutorat);
	   
       context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tutorat annulé pour " + e.getNom() + " " + e.getPrenom(), null));
       return "AjouterTutoratEtudiant.xhtml?face-redirect=true";
   }
   

   public String addTutorat(Etudiant e) {
		FacesContext context = FacesContext.getCurrentInstance();

		tutorat = new Tutorat();
		tutorat.setIdCours(cours.getId());
		tutorat.setNom(cours.getNom());
		tutorat.setSection(e.getSection());
		tutorat.setEtuId(e.getId());
		
		gestionTutorat.addTutorat(tutorat);
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tutorat ajouté pour " + e.getNom() + " " + e.getPrenom(), null));

		return "AjouterTutoratEtudiant.xhtml?face-redirect=true";
   }
   
   public String navAjouterTutorat(Cours c) {
	   cours = c;
	   section = c.getSection();
	   return "AjouterTutoratEtudiant.xhtml?face-redirect=true";
   }
   
	public void clearNom() {
		init();
		cours.setNom(null);
	}
}