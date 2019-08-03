package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import entities.Cours;
import entities.Remediation;
import entities.Tutorat;
import sessionejb.GestionRemediationEJB;
import sessionejb.GestionTutoratEJB;

@Named
@SessionScoped
public class RemediationController implements Serializable{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionRemediationEJB gestionRemediation;
	private List<Remediation> remediations = new ArrayList<Remediation>();
	private Remediation remediation;
	private String section;
	private int idTuto = 0;
	
	public RemediationController() {}
	
	   @PostConstruct
	   public void init() {
	       remediation = new Remediation();
	   }
	   
	   public Remediation getRemediation() {
	       if(remediation == null) {
	           init();
	       }
	       return remediation;
	   }
	   
	   public void setIdTuto(int id) {
		   this.idTuto = id;
	   }
	   
	   public void setRemediation(Remediation rem) {
	       this.remediation = rem;
	   }
	   
	   public String setSection(String s) {
		   this.section = s;
		   return "ListeRemediation.xhtml?face-redirect=true";
	   }
	   
		public String getSection() {
			return section;
		}
		
		public List<Remediation> getListeRemediation(Tutorat tuto){
			remediations = gestionRemediation.getRemediationEtudiant(tuto.getId());
			return remediations;
		}
		
		public void ajouterRemediation() {
				remediation.setTutoId(this.idTuto);
				
				DateTimeFormatter cestDateTimeFormatter = DateTimeFormatter
		                .ofPattern("EE MMM dd HH:mm:ss z yyyy",
		                Locale.ENGLISH);
		        LocalDateTime timestamp = LocalDateTime.parse(remediation.getDate(), cestDateTimeFormatter);
		 
		        // Formatage dans le nouveau format
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM YYYY à HH:mm", Locale.FRANCE);
		        String resultat = formatter.format(timestamp);
		        
		        remediation.setDate(resultat);
				
				gestionRemediation.addRemediation(remediation);
				remediation.setDate(null);
				//return "ListeRemediation.xhtml?face-redirect=true";
		}
		
		public int nbReme(int idTuto) {
			return gestionRemediation.getNbReme(idTuto);
		}
		
		public String getRemediationsTuto(int idTuto) {
			List<Remediation> listTmp = new ArrayList<Remediation>();
			listTmp = gestionRemediation.getRemediationEtudiant(idTuto);
			
			String tmp = "";
			int size = listTmp.size() - 1;
			int index = 0;
			
			for(Remediation rem : listTmp) {
				if(index < size) {
					tmp += "[" + rem.getDate() + "] - ";
				}else {
					tmp += "[" + rem.getDate() + "]";
				}
				index ++;
			}
			
			return tmp;
			
		}
	
}
