package controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import entities.Cours;
import entities.Tutorat;
import sessionejb.GestionCoursEJB;
import sessionejb.GestionRemediationEJB;
import sessionejb.GestionTutoratEJB;

@Named
@SessionScoped
public class TutoratController implements Serializable{
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
   private GestionTutoratEJB gestionTutorat;
	@EJB
   private GestionCoursEJB gestionCours;
	@EJB
   private GestionRemediationEJB gestionRemediation;
   //private List<Tutorat> listeTutorat = new ArrayList<Tutorat>();
   private List<String> listeTutorat = new ArrayList<String>();
   private Tutorat tutorat;
   private Cours cours;
   private String section; 
   
   public TutoratController() {}
   
   @PostConstruct
   public void init() {
       tutorat = new Tutorat();
       cours = new Cours();
   }
   
   public Tutorat getTutorat() {
       if(tutorat == null) {
           init();
       }
       return tutorat;
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
		return "ListeTutorat.xhtml?face-redirect=true";
	}

   public void setTutorat(Tutorat tuto) {
       this.tutorat = tuto;
   }
   
   public void setCours(Cours c) {
	   this.cours = c;
   }
   
   public List<String> getListTuto(String section) {
	   listeTutorat = gestionTutorat.selectTutoSection(section);
	return listeTutorat;
   }
   
   public String deleteTutorat(int id,String cours ,String nom,String prenom) {
	   FacesContext context = FacesContext.getCurrentInstance();
	   tutorat = new Tutorat();
	   tutorat.setId(id);
       gestionTutorat.deleteTutorat(tutorat);
       gestionRemediation.deleteAllRemediation(tutorat.getId());
       context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tutorat en " + cours + " annulé pour l'étudiant " + nom + " " + prenom, null));
       return "ListeTutorat.xhtml?face-redirect=true";
   }
   
   public int nbEtudiantTuto() {
	   return gestionTutorat.nbEtudiantEnTutorat(section);
   }
   
   //Calcul de la moyenne de cours demandé en tutorat
   public String moyenneCours() {
	   float totalCours  = gestionCours.nbCours(section);
	   float totalCoursDemande = gestionTutorat.nbTutorat(section);
	   
	   if(totalCoursDemande != 0) {
		   float tot = (totalCoursDemande / totalCours) * 100;
		   String result = String.format("%.02f", tot);
		   return result;
	   }
	   return "0";
   }
   
   public List<Tutorat> getListTutoChart(){
	   return gestionTutorat.getListCoursTuto(section);
   }
   

}
