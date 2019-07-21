package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.poi.EncryptedDocumentException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import entities.Etudiant;
import entities.NoteEtudiant;
import entities.Seminaire;
import sessionejb.GestionEtudiantEJB;
import sessionejb.GestionNoteEtudiantEJB;
import sessionejb.GestionSeminaireEJB;
import util.ExcelReader;

@Named
@SessionScoped
public class EtudiantController implements Serializable {

	 /**
	 * 
	 */
	private int tmpId = 0;
	private boolean loadFile = false;
	private String comboSection = null;
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionEtudiantEJB gestionEtudiant;
	@EJB
	private GestionNoteEtudiantEJB gestionNote;
	private List<Etudiant> etudiants = new ArrayList<Etudiant>();
	private List<NoteEtudiant> notes = new ArrayList<NoteEtudiant>();
	private Etudiant etudiant;
	private NoteEtudiant note;
	private String section;
	   
	   public EtudiantController() {}
	  
	   @PostConstruct
	   public void init() {
	       etudiant = new Etudiant();
	       note = new NoteEtudiant();
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
	   
	   /*public List<Etudiant> getEtudiants() {
		etudiants = gestionEtudiant.selectAll();
		return etudiants;
	   }*/
	   
	   public NoteEtudiant getNoteEtudiant() {
	       if(note == null) {
	           init();
	       }
	       return note;
	   }

	   public void setNoteEtudiant(NoteEtudiant note) {
	       this.note = note;
	   }
	   
	   public List<NoteEtudiant> getListNoteEtudiants() {
		notes = gestionNote.selectNotesEtu(etudiant.getId());
		return notes;
	   }
	   
	   public List<Etudiant> getEtudiantsSection(String section) {
		etudiants = gestionEtudiant.selectAllEtudiantSection(section);
		return etudiants;
	   }

	public String deleteEtudiant(Etudiant etudiant) {
			gestionNote.deleteNotesEtudiant(etudiant.getId());
		   gestionEtudiant.deleteEtudiant(etudiant);
		   etudiants = gestionEtudiant.selectAll();
		   return "ListeEtudiant.xhtml?faces-redirect=true";
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
	public void save() throws InvalidFormatException, EncryptedDocumentException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException {
		
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
	        
	       /* for(Etudiant e: etudiants) {
	        	gestionEtudiant.deleteEtudiant(e);
	        }*/
	        gestionEtudiant.deleteAll();
	        etudiants.clear();
	        
	        try {
	        	etudiants = ExcelReader.getEtudiants(cFile);
	        	
		        for(Etudiant e : etudiants) {
		        	gestionEtudiant.addEtudiant(e);
		        }
		        
		        loadFile = true;
	    		
	        }catch(Exception e) {
	        	loadFile = false;
	        }
	}
	
	public void messageLoadFile() {
		if(loadFile) {
    		FacesContext context = FacesContext.getCurrentInstance();
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Liste ajoutée", null));
		}else {
    		FacesContext context = FacesContext.getCurrentInstance();
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fichier non correct !", null));
		}

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
	
	
	//Detail étudiant
	public String ajouterNote() {
		if(!note.getTexte().equals("")) {
			note.setEtuId(etudiant.getId());
			SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy 'à' HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			note.setDate(formatter.format(date));
			gestionNote.addNote(note);
			note.setTexte(null);
			return "DetailEtudiant.xhtml?faces-redirect=true";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aucun texte n'a été entré !", null));
		return null;
	}
	
	public String supprimerNote(NoteEtudiant nu) {
		   gestionNote.deleteNote(nu);
		   notes = gestionNote.selectNotesEtu(etudiant.getId());
		   return "DetailEtudiant.xhtml?faces-redirect=true";
	}
	//Detail étudiant
	
	
	//Ajouter étudiant
	public void clearInput() {
		etudiant.setClasse(null);
		etudiant.setCoordonnees(false);
		etudiant.setPhoto(false);
		etudiant.setNom(null);
		etudiant.setPrenom(null);
	}
	
    public void changeSection (ValueChangeEvent event) {
        comboSection = event.getNewValue().toString();
    }
    
   public String ajouterUnEtudiant() {
		FacesContext context = FacesContext.getCurrentInstance();
	   if(!etudiants.contains(etudiant)) {
		   	gestionEtudiant.addEtudiant(etudiant);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Etudiant ajouté !", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
		   return "ListeEtudiant.xhtml?faces-redirect=true";
	   }
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cet étudiant existe déja !", null));
	   return null;
   }
  //Ajouter étudiant
   
   public String modifierNote(NoteEtudiant n) {
	   note.setId(n.getId());
	   gestionNote.updateNote(note);
	   note.setTexte(null);
	   return "DetailEtudiant.xhtml?faces-redirect=true";
   }
   
   public void getIdNote(NoteEtudiant n) {
	   note.setId(n.getId());
   }


}
