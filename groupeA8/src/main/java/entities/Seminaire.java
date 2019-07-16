package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Seminaire implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SEC_ID")
    private Integer id;
    private String nomSeminaire;
    private String date;
    private String section; 
    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "SEMINAIRE_ETUDIANT", 
    joinColumns = { @JoinColumn(name = "SEC_ID") }, 
    inverseJoinColumns = { @JoinColumn(name = "ETU_ID") })
    private Set<Etudiant> etudiants = new HashSet<>();
    
    
    public Seminaire() {
    	
    }
    
    public Seminaire(String nomSeminaire, String section, String date) {
    	super();
        this.nomSeminaire = nomSeminaire;
        this.section = section;
        this.date = date;
    }
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomSeminaire() {
		return nomSeminaire;
	}

	public void setNomSeminaire(String nomSeminaire) {
		this.nomSeminaire = nomSeminaire;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Set<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Set<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public boolean ajouterEtudiant(Etudiant etudiant)
	{
		if(!etudiants.contains(etudiant)) {
			return etudiants.add(etudiant);
		}
		return false;
	}
	
	
	
	public boolean retirerEtudiant(Etudiant etudiant) {
		return etudiants.remove(etudiant);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		System.out.println("ENCULE DE TES MOETAS LAS DATE C EST CA" + date);
		this.date = date;
	}

}
