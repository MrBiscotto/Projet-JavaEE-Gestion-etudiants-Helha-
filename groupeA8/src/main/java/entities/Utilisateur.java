package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Utilisateur implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String motDePasse;
    private String permission;

    public Utilisateur() {}
    
    public Utilisateur(String uti, String mdp, String permission) {
        this.nom = uti;
        this.motDePasse = mdp;
        this.permission = permission;
    }

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

    public int getId() {
        return id;
    }

    public void setId(int idUtilisateur) {
        this.id = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String utilisateur) {
        this.nom = utilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Utilisateur [idUtilisateur=" + id + ", utilisateur=" + nom + ", motDePasse="
                + motDePasse + "]";
    }



}