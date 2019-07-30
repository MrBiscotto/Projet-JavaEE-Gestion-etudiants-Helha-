package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAOTutorat;
import entities.Tutorat;

@Stateless
@LocalBean
public class GestionTutoratEJB implements IGestionTutoratEJBRemote{

         @EJB
        private DAOTutorat dao;
        public GestionTutoratEJB()
        {

        }

        public List<Tutorat> selectAll() {
            return dao.selectAll();
        }

        public List<String> selectTutoSection(String idSection){
        	return dao.selectTutoSection(idSection);
        }
        
        public Tutorat addTutorat(Tutorat u) {
            if(u==null)                     
            {
                return null;
            }
            return dao.addTutorat(u);
        }
        
        public int etudiantInscrit(String idSec, int idEtu, int idCours) {
        	return dao.etudiantInscrit(idSec, idEtu, idCours);
        }

        public void deleteTutorat(Tutorat u)
        {
            dao.deleteTutorat(u);
        }
        
        public void deleteTutoratCours(int idCours) {
        	dao.deleteTutoratCours(idCours);
        }

        public Tutorat getTutorat(int idTuto) {
            return dao.getTutorat(idTuto);
        }
        
        public int nbEtudiantEnTutorat(String idSec) {
        	return dao.nbEtudiantEnTutorat(idSec);
        }
        
        public int nbTutorat(String idSec) {
        	return dao.nbTutorat(idSec);
        }
        
        public List<Tutorat> getListCoursTuto(String idSec){
        	return dao.getListCoursTuto(idSec);
        }
        
        public void deleteTutoratEtudiant(int idEtu) {
        	dao.deleteTutoratEtudiant(idEtu);
        }

}
