package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAORemediation;
import dao.DAOTutorat;
import entities.Remediation;

@Stateless
@LocalBean
public class GestionRemediationEJB {

    @EJB
   private DAORemediation dao;
   public GestionRemediationEJB(){}
   
   public List<Remediation> getRemediationEtudiant(int idTuto){
	   return dao.getRemediationEtudiant(idTuto);
   }
   
   public Remediation addRemediation(Remediation reme) {
	   return dao.addRemediation(reme);
   }
   
   public void deleteAllRemediation(int tutoId) {
	   dao.deleteAllRemediation(tutoId);
   }
   
   public int getNbReme(int idTuto) {
	   return dao.getNbReme(idTuto);
   }
   
   public void deleteRemediation(Remediation r) {
	   dao.deleteRemediation(r);
   }
   
   public List<Object[]> getListRemeEtu(int idEtu){
	   return dao.getListRemeEtu(idEtu);
   }
}
