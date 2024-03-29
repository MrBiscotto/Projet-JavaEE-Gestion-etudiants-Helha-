package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
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
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.apache.derby.authentication.UserAuthenticator;
import org.apache.poi.EncryptedDocumentException;
import org.primefaces.shaded.commons.io.FilenameUtils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import entities.Etudiant;
import entities.NoteEtudiant;
import entities.Seminaire;
import sessionejb.GestionEtudiantEJB;
import sessionejb.GestionNoteEtudiantEJB;
import sessionejb.GestionSeminaireEJB;
import sessionejb.GestionTutoratEJB;
import util.ExcelReader;

@Named
@SessionScoped
public class EtudiantController implements Serializable {

	 /**
	 * 
	 */
	   
    private Part image;
    private String path ="images/photo2.png";
	private int tmpId = 0;
	private boolean loadFile = false;
	private String comboSection = null;
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionEtudiantEJB gestionEtudiant;
	@EJB
	private GestionNoteEtudiantEJB gestionNote;
	@EJB
	private GestionTutoratEJB gestionTuto;
	@EJB
	private GestionSeminaireEJB gestionSemi;
	private List<Etudiant> etudiants = new ArrayList<Etudiant>();
	private List<NoteEtudiant> notes = new ArrayList<NoteEtudiant>();
	private Etudiant etudiant;
	private NoteEtudiant note;
	private String section;
	private String nomPrenomRecherche = null;
	private Part file; 
	   
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
	   
	   public List<String> getEtudiants(){
			etudiants.clear();
			etudiants = gestionEtudiant.selectAll();
			List<String> listeNomPrenom = new ArrayList<String>();
			String nom = "";
			String prenom ="";
			
			for(Etudiant e : etudiants) {
					nom = e.getNom().replaceAll("-", "~");
					prenom = e.getPrenom().replaceAll("-", "~");
					nom = nom.replaceAll(" ", "-");
					prenom = prenom.replaceAll(" ", "-");

					listeNomPrenom.add(nom.replaceAll("'", "`") + " " + prenom.replaceAll("'", "-"));
			}
			return listeNomPrenom;
			
	   }
	   
	   public String getNomPrenomRecherche() {
		   return this.nomPrenomRecherche;
	   }
	   
	   public void setNomPrenomRecherche(String nom) {
		   this.nomPrenomRecherche = nom;
	   }
	   
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

			//gestionSemi.deleteSemiEtudiant(etudiant.getSection(), etudiant.getId());
			gestionNote.deleteNotesEtudiant(etudiant.getId());
		   gestionEtudiant.deleteEtudiant(etudiant);
		   //gestionTuto.deleteTutoratEtudiant(etudiant.getId());
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
   
   public String rechercheEtudiant() {

	   FacesContext context = FacesContext.getCurrentInstance();

	   nomPrenomRecherche = nomPrenomRecherche.replaceAll("`","'");
	   String[] nP = nomPrenomRecherche.split("\\s+");
	   nP[0] = nP[0].replaceAll("-", " ");
	   nP[1] = nP[1].replaceAll("-", " ");
	   nP[0] = nP[0].replaceAll("~", "-");
	   nP[1] = nP[1].replaceAll("~", "-");
	   
  		nomPrenomRecherche = null;

	   if(gestionEtudiant.getEtudiantNomPrenom(nP[0], nP[1]).getId() != null) {
	   
		   etudiant = gestionEtudiant.getEtudiantNomPrenom(nP[0], nP[1]);
	   		return "DetailEtudiant.xhtml?faces-redirect=true";
	   }
	   
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Etudiant non existant !", null));
	   return null;
   }
   
	public String navDetailsEtudiant(Etudiant e){
		etudiant = e;
		return "DetailEtudiant.xhtml?faces-redirect=true";
	}
	
	public void verifyProfilPicture() {
    	ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
	    String realPath = ctx.getRealPath("/");
	    
	    File f = new File(realPath + "/photoProfil/" +  etudiant.getId() + ".png");
	    
		if(f.exists()  && !f.isDirectory())
		{
			path = "/photoProfil/" +  etudiant.getId() + ".png";
		}else {
			path = "images/photo2.png";
		}
		
	}
	   
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
	        
	       /* for(Etudiant e: etudiants) {
	        	gestionEtudiant.deleteEtudiant(e);
	        }*/
	        gestionEtudiant.deleteAll();
	        //gestionNote.deleteAllNote();
	        //gestionTuto.deleteAllTutorat();
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
	        
	        messageLoadFile();
	        return "index.xhtml?faces-redirect=true";
	}
	
	public void messageLoadFile() {
		if(loadFile) {
    		FacesContext context = FacesContext.getCurrentInstance();
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Liste ajout�e", null));
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
	
	
	//Detail �tudiant
	public String ajouterNote() {
		if(!note.getTexte().equals("")) {
			note.setEtuId(etudiant.getId());
			SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy '�' HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			note.setDate(formatter.format(date));
			
			UtilisateurController user = new UtilisateurController();
			String res = user.getNomPerm();
			note.setUtilisateur(res);
			gestionNote.addNote(note);
			note.setTexte(null);
			return "DetailEtudiant.xhtml?faces-redirect=true";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aucun texte n'a �t� entr� !", null));
		return null;
	}
	
	public String supprimerNote(NoteEtudiant nu) {
		   gestionNote.deleteNote(nu);
		   notes = gestionNote.selectNotesEtu(etudiant.getId());
		   return "DetailEtudiant.xhtml?faces-redirect=true";
	}
	//Detail �tudiant
	
	
	//Ajouter �tudiant
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
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Etudiant ajout� !", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
		   return "ListeEtudiant.xhtml?faces-redirect=true";
	   }
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cet �tudiant existe d�ja !", null));
	   return null;
   }
  //Ajouter �tudiant
   
   public void modifierNote() {
	   UtilisateurController user = new UtilisateurController();
	   String utiPerm = user.getNomPerm();
	   note.setUtilisateur(utiPerm);
	   gestionNote.updateNote(this.note);
	   note.setTexte(null);
   }
   
   public void setTmpId(int id) {
	   this.tmpId = id;
   }
   
   public void getIdNote(NoteEtudiant n) {
	   note.setId(n.getId());
   }

   public void imageUpload(){
       
	    try (InputStream input = image.getInputStream()) {
	    	String format = image.getSubmittedFileName();
	    	
	    	ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
	                .getExternalContext().getContext();
		    String realPath = ctx.getRealPath("/");
		    
		     //String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		    
	    	String[] split = format.split("\\.");
	        Files.copy(input, new File(realPath + "/photoProfil", etudiant.getId() + "." + split[1].toLowerCase()).toPath(),StandardCopyOption.REPLACE_EXISTING);

	       	path = "/photoProfil/" +  etudiant.getId() + "." + split[1].toLowerCase();
	    }
	    catch (IOException e) {
	        // Show faces message?
	    }
   }
   
   public String getPath() {
	   return path;
   }
   
   public void setPath(String p) {
	   this.path = p;
   }

   public Part getImage() {
       return image;
   }

   public void setImage(Part image) {
       this.image = image;
   }

}
