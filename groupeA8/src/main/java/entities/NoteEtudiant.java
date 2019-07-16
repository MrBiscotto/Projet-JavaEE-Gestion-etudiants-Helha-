package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NoteEtudiant implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String texte;
    private int etuId;
    
    public NoteEtudiant() {}
    
    public NoteEtudiant(String texte, int etuId) {
    	super();
    	this.texte = texte;
    	this.etuId = etuId;
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
