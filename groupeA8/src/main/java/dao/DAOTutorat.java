package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Cours;
import entities.Tutorat;

@Stateless
@LocalBean
public class DAOTutorat {

     @PersistenceContext(unitName="groupeA8")
        private EntityManager em;
       
        public DAOTutorat() {
            
        }
        
        public List<Tutorat> selectAll()
        {
            Query query= em.createQuery("select c from Tutorat");
            return (List<Tutorat>) query.getResultList();    
        }
        
	    public  List<String> selectTutoSection(String idSection)
	    {
	    	Query query= em.createQuery("select t.id,t.nom,e.nom,e.prenom from Tutorat t INNER JOIN Etudiant e on e.id = t.etuId where t.section like :section");
	    	query.setParameter("section", idSection);
	        return (List<String>) query.getResultList();
	    }
        
        public Tutorat addTutorat(Tutorat u)
        {
            em.persist(u);
            return u;    
        }
        
   
        public Tutorat getTutorat(int idTuto)
        {
            Query query= em.createQuery("select c from Tutorat c where c.id = :id ");
            query.setParameter("id", idTuto);
            return (Tutorat) query.getSingleResult();
        }
        
	    //Requête pour vérifier si l'étudiant est déja inscrit au séminaire
	    public int etudiantInscrit(String idSec, int idEtu,int idCours) {
		   	 Query query = em.createQuery("select count(u) from Tutorat u where u.section like :idSec AND u.etuId = :idEtu AND u.idCours = :idCours");
			 query.setParameter("idSec", idSec);
			 query.setParameter("idEtu", idEtu);
			 query.setParameter("idCours", idCours);
			 return ((Number) query.getSingleResult()).intValue();
	    }

        public void deleteTutorat(Tutorat p)
        {
            Query query = em.createQuery("delete from Tutorat c where c.id = :id ");
            query.setParameter("id", p.getId());
            query.executeUpdate();
        }
        
        public void deleteTutoratCours(int idCours) {
            Query query = em.createQuery("delete from Tutorat c where c.idCours = :id ");
            query.setParameter("id", idCours);
            query.executeUpdate();
        }
        
        public void close()
        {
            em.clear();
            em.close();
        } 
        
}
