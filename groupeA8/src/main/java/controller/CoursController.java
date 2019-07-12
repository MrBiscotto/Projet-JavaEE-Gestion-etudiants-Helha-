package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entities.Cours;
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
   
   public CoursController() {}
   
   public void init() {
       cours = new Cours();
   }
   
   public Cours getSeminaire() {
       if(cours == null) {
           init();
       }
       return cours;
   }

   public void setCours(Cours cours) {
       this.cours = cours;
   }

   public List<Cours> getCours(){
       listeCours = gestionCours.selectAll();
       return listeCours;
   }
   
   public void deleteCours() {
       
       gestionCours.deleteCours(cours);;
       listeCours = gestionCours.selectAll();
   }
   
 /*  public void updateSeminaire() {
       gestionSeminaire.updateSeminaire(seminaire);
   }
   */
   public void addCours() {
       gestionCours.addCours(cours);
   }

}