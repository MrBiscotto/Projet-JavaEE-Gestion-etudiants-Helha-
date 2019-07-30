package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
		
		public void ajouterRemediation(int tutoId) {
			int test = tutoId;
			remediation.setTutoId(tutoId);
			gestionRemediation.addRemediation(remediation);
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
