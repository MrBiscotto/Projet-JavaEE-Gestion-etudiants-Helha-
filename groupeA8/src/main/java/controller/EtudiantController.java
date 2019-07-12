package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.poi.EncryptedDocumentException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import entities.Etudiant;
import entities.Seminaire;
import sessionejb.GestionEtudiantEJB;
import util.ExcelReader;

@Named
@SessionScoped
public class EtudiantController implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	   private GestionEtudiantEJB gestionEtudiant;
	   private List<Etudiant> etudiants = new ArrayList<Etudiant>();
	   private Etudiant etudiant;
	   private String section;
	   
	   public EtudiantController() {}
	  
	   public void init() {
	       etudiant = new Etudiant();
	   }
	   
	   public Etudiant getEtudiant() {
	       if(etudiant == null) {
	           init();
	       }
	       return etudiant;
	   }

	   public void setEtudiant(Etudiant etudiant) {
	       this.etudiant = etudiant;
	   }

	   
	   public List<Etudiant> getEtudiants() {
		etudiants = gestionEtudiant.selectAll();
		return etudiants;
	   }
	   
	   public List<Etudiant> getEtudiantsSection(String section) {	
		etudiants = gestionEtudiant.selectAllEtudiantSection(section);
		return etudiants;
	   }

	public void deleteEtudiant(Etudiant etudiant) {
		   gestionEtudiant.deleteEtudiant(etudiant);;
		   etudiants = gestionEtudiant.selectAll();
	   }
	   
	 /*  public void updateSeminaire() {
	       gestionSeminaire.updateSeminaire(seminaire);
	   }
	   */
	   public void addEtudiant() {
		   gestionEtudiant.addEtudiant(etudiant);
	   }
	
	public String navDetailsEtudiant(Etudiant e){
		etudiant = e;
		return "DetailEtudiant.xhtml?faces-redirect=true";
	}
	   
	private Part file; 
	public String save() throws InvalidFormatException, EncryptedDocumentException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException {
		
	        File cFile = new File(file.getSubmittedFileName());
	        try {
	            cFile.createNewFile();
	        } catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
	        try (InputStream input = file.getInputStream()) {
	            Files.copy(input, cFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        for(Etudiant e: etudiants) {
	        	gestionEtudiant.deleteEtudiant(e);
	        	System.out.println("test " + e.toString());
	        }
	        etudiants.clear();
	        
	        etudiants = ExcelReader.getEtudiants(cFile);
	        
	        for(Etudiant e : etudiants) {
	        	gestionEtudiant.addEtudiant(e);
	        }
	        
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La liste des étudiants a été ajoutée !", null));
	        return null;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getSection() {
		return section;
	}

	public String setSection(String section) {
		this.section = section;
		return "ListeEtudiant.xhtml?faces-redirect=true";
	}


}
