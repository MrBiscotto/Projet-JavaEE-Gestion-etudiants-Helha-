package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.sun.jdo.api.persistence.support.Transaction;

import entities.Etudiant;
import entities.Seminaire;
import sessionejb.GestionSeminaireEJB;

@Named
@SessionScoped
public class SeminaireController implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
    private GestionSeminaireEJB gestionSeminaire;
    private List<Seminaire> seminaires = new ArrayList<Seminaire>();
    private Seminaire seminaire;
    private String section;    
    
    public SeminaireController() {}
    
    public void init() {
        seminaire = new Seminaire();
    }
    
    public Seminaire getSeminaire() {
		if(seminaire == null) {
			init();
		}
    	return seminaire;
	}

	public void setSeminaire(Seminaire seminaire) {
		this.seminaire = seminaire;
	}

	public List<Seminaire> getSeminaires(){
        seminaires = gestionSeminaire.selectAll();
        return seminaires;
    }
    
	public List<Seminaire> getSeminairesSection(String idSection){
        seminaires = gestionSeminaire.selectSeminaireSection(idSection);
        return seminaires;
    }
	
    public void deleteSeminaire(Seminaire seminaire) {
        gestionSeminaire.deleteSemi(seminaire);;
        seminaires = gestionSeminaire.selectAll();
    }
    
    public String addSeminaire() {
    	if(seminaire.getNomSeminaire() !="") {
	    	seminaire.setSection(section);
	        gestionSeminaire.addSemi(seminaire);
	        return "ListeSeminaire.xhtml?face-redirect=true";
    	}
    	return null;
    }

    public void ajouterEtudiant(Etudiant e) {
    	Etudiant tmpEtudiant = e;
    	System.out.println(tmpEtudiant.getId());
    	tmpEtudiant.setId(e.getId());
    	seminaire.ajouterEtudiant(tmpEtudiant);
    	gestionSeminaire.updateSeminaire(seminaire);
    	
    }
    
	public String getSection() {
		return section;
	}

	public String setSection(String section) {
		this.section = section;
		return "ListeSeminaire.xhtml?face-redirect=true";
	}
	
	
	public String onPageLoad() { 
	    // Do your stuff here.
		return "ListeSeminaire.xhtml?face-redirect=true";
	}
	
	public String navAjouterEtudiantSerminaire(Seminaire s) {
		seminaire = s;
		return "AjouterEtudiantSeminaire.xhtml?face-redirect=true";
	}
	
	public List<Seminaire> getSeminairesId(int idEtu){
		seminaires = gestionSeminaire.selectSeminaireId(idEtu);
		return seminaires;
	}
	
	public long getCountId(int idEtu){
		long count = gestionSeminaire.getSeminaireId(idEtu);
		return count;
	}
	
	public long getCountSeminaire(String section){
		long count = gestionSeminaire.getCountSeminaireSection(section);
		return count;
	}
	
	

}