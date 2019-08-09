package controller;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entities.Utilisateur;
import net.bootsfaces.utils.FacesMessages;
import sessionejb.GestionUtilisateurEJB;

@Named
@ViewScoped
public class UtilisateurController implements Serializable {

	
	/**
	 * 
	 */
	private static String perm = "what";
	private static boolean login = false;
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionUtilisateurEJB gestionUtilisateur;
	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;

	public UtilisateurController() {

	}

	@PostConstruct
	public void init() {
		utilisateur = new Utilisateur();
		utilisateurs = new ArrayList<>();

	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Utilisateur> getUtilisateurs(){
		utilisateurs = gestionUtilisateur.selectAll();
		return utilisateurs;
	}

	public void deleteUtilisateur() {
		gestionUtilisateur.deleteUtilisateur(utilisateur);
		utilisateurs = gestionUtilisateur.selectAll();
	}

	public void updateUtilisateur() {
		gestionUtilisateur.updateUtilisateur(utilisateur);
	}

	public String seConnecter(String nomUtil, String mdpUtil) throws NoSuchAlgorithmException {
		
			FacesContext context = FacesContext.getCurrentInstance();
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        String mdp = mdpUtil + nomUtil;
	        md.update(mdp.getBytes());

	        byte byteData[] = md.digest();

	        //convertir le tableau de bits en une format hexadécimal - méthode 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }

	        boolean ok = gestionUtilisateur.verifUtilisateur(nomUtil, sb.toString());
	        if(ok) {
				utilisateur = gestionUtilisateur.getUtilisateur(nomUtil, sb.toString());
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Connexion réussie !", null));
				context.getExternalContext().getFlash().setKeepMessages(true);
				login = true;
				perm = utilisateur.getPermission();
				return "index.xhtml?faces-redirect=true";
			}
	        
	        utilisateur.setMotDePasse(mdpUtil);
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Connexion échouée ! (Mot de passe ou Nom d'utilisateur incorrect)", null));
	        return null;

	}

	public void addUtilisateur() {

		if(gestionUtilisateur.utilisateurExist(utilisateur.getNom().toString()) == 0) {
			
			try {
				gestionUtilisateur.addUtilisateur(utilisateur);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Utilisateur ajouté avec succès !", null));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				utilisateur.setNom(null);
				utilisateur.setMotDePasse(null);
			}
		
		}else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nom d'utilisateur déja existant !", null));
		}
	}

	public Utilisateur getUtilisateur(Utilisateur u) {
		return gestionUtilisateur.getUtilisateur(u);
	}


	public String deconnexion() {
		utilisateur.setNom(null);
		utilisateur.setMotDePasse(null);
		utilisateur.setPermission(null);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Déconnexion effectuée !", null));
		context.getExternalContext().getFlash().setKeepMessages(true);

		login = false;
		return "Connexion.xhtml?faces-redirect=true";
	}

	public boolean checkAdmin() {
		if(utilisateur.getPermission().equals("ADMIN")) {
			return true;
		}
		return false;
	}
	
	public String navAjouterUtilisateur() {
		return "AjouterUtilisateur.xhtml?faces-redirect=true";
	}
	
	public String navNull() {
		return null;
	} 
	
	//empêche d'utiliser les autres pages sans être connecté
	public String checkLogin() {
		
		if(login == false) {
			return "Connexion.xhtml?faces-redirect=true";
		}
		return null;
	}
	
	public String checkAdminAdd() {

		if(perm.equals("ADMIN")) {
			return "AjouterUtilisateur.xhtml?faces-redirect=true";
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Accès réservé aux Administrateurs !", null));
		return "index.xhtml?faces-redirect=true";
		
	}
	
}