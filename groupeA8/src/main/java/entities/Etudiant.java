package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

@Entity
	public class Etudiant implements Serializable{

	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name = "ETU_ID")
	    private Integer id;
	    private String prenom;
	    private String nom;
	    private String section;
	    private String classe;
	    private boolean coordonnees;
	    private boolean photo;
	   
	    
	    public Etudiant() {
	        
	    }
	    
	    public Etudiant(String prenom, String nom, String section, String classe, boolean coor, boolean photo) {
	        this.prenom = prenom;
	        this.nom = nom;
	        this.section = section;
	        this.classe = classe;
	        this.coordonnees = coor;
	        this.photo = photo;
	    }
	    
	    public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public String getClasse() {
			return classe;
		}

		public void setClasse(String classe) {
			this.classe = classe;
		}

		public Boolean getCoordonnees() {
			return coordonnees;
		}

		public void setCoordonnees(Boolean coordonnees) {
			this.coordonnees = coordonnees;
		}

		public Boolean getPhoto() {
			return photo;
		}

		public void setPhoto(Boolean photo) {
			this.photo = photo;
		}


	    public Integer getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }
	       
	    
	  
		
		public Etudiant loadFromRow(Row row)
	    {
	        DataFormatter dataFormatter = new DataFormatter();
	        this.nom = dataFormatter.formatCellValue(row.getCell(0));
	        this.prenom = dataFormatter.formatCellValue(row.getCell(1));
	        this.section = dataFormatter.formatCellValue(row.getCell(2));
	        /*switch(dataFormatter.formatCellValue(row.getCell(2)))
	        {
	        case "CT":
	            this.section = sections.stream().filter(section -> section.getName().equals("Comptabilité")).findFirst().get();
	            break;
	        case "IG":
	            this.section = sections.stream().filter(section -> section.getName().equals("Informatique de gestion")).findFirst().get();
	            break;
	        case "AD":
	            this.section = sections.stream().filter(section -> section.getName().equals("Assistant de direction")).findFirst().get();
	            break;
	        }*/
	        this.classe = dataFormatter.formatCellValue(row.getCell(3));
	        this.coordonnees = dataFormatter.formatCellValue(row.getCell(4)).equals("accepte de diffuser");
	        this.photo = dataFormatter.formatCellValue(row.getCell(5)).equals("accepte de diffuser");
	        
	        return this;
	    }

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Etudiant other = (Etudiant) obj;
			if (classe == null) {
				if (other.classe != null)
					return false;
			} else if (!classe.equals(other.classe))
				return false;
			if (coordonnees != other.coordonnees)
				return false;
			if (nom == null) {
				if (other.nom != null)
					return false;
			} else if (!nom.equals(other.nom))
				return false;
			if (photo != other.photo)
				return false;
			if (prenom == null) {
				if (other.prenom != null)
					return false;
			} else if (!prenom.equals(other.prenom))
				return false;
			if (section == null) {
				if (other.section != null)
					return false;
			} else if (!section.equals(other.section))
				return false;
			return true;
		}

	}

