package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Entity
public class NoteEtudiant implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String texte;
    private String date;
    private int etuId;
    private String utilisateur;
    
    public NoteEtudiant() {}
    
    public NoteEtudiant(String texte, int etuId,String date,String uti) {
    	super();
    	this.texte = texte;
    	this.etuId = etuId;
    	this.date = date;
    	this.utilisateur = uti;
    }

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public int getEtuId() {
		return etuId;
	}

	public void setEtuId(int etuId) {
		this.etuId = etuId;
	}
}
