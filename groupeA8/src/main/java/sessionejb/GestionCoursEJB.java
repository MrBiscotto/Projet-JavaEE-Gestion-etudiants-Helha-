package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAOCours;
import dao.DAOSeminaire;
import entities.Cours;
import entities.Seminaire;

@Stateless
@LocalBean
public class GestionCoursEJB implements IGestionCoursEJBRemote{

         @EJB
        private DAOCours dao;
        public GestionCoursEJB()
        {

        }

        public List<Cours> selectAll() {
            return dao.selectAll();
        }

        public List<Cours> selectCoursSection(String idSection){
        	return dao.selectCoursSection(idSection);
        }
        
        public Cours addCours(Cours u) {
            if(u==null)                     
            {
                return null;
            }
            return dao.addCours(u);
        }

        public void deleteCours(Cours u)
        {
            dao.deleteCours(u);
        }
        
        public int nbCours(String idSec) {
        	return dao.nbCours(idSec);
        }

        public Cours getCours(int u) {
            return dao.getCours(u);
        }

}