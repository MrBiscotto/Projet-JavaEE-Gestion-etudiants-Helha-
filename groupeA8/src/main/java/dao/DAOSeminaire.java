package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Etudiant;
import entities.Seminaire;

@Stateless
@LocalBean
public class DAOSeminaire implements Serializable {

	 	@PersistenceContext(unitName="groupeA8")
	    private EntityManager em;
	   
	    public DAOSeminaire() {
	        
	    }
	    
	    public List<Seminaire> selectAll()
	    {
	        Query query= em.createQuery("SELECT s FROM Seminaire s");
	        return (List<Seminaire>) query.getResultList();    
	    }
	    
	    public Seminaire addSemi(Seminaire u)
	    {
	        em.persist(u);
	        return u;    
	    }
	    
   
	    public Seminaire getSeminaire(int idSemi)
	    {
	        Query query= em.createQuery("select s from Seminaire s where s.id = :id ");
	        query.setParameter("id", idSemi);
	        return (Seminaire) query.getSingleResult();
	    }
	    
	    public  List<Seminaire> selectSeminaireSection(String idSection)
	    {
	    	Query query= em.createQuery("select s from Seminaire s where s.section like :section").setParameter("section", idSection);
	        return (List<Seminaire>) query.getResultList();
	    }

	    public List<Seminaire> selectSeminaireId(int idEtu){
	    	Query query= em.createQuery("SELECT s FROM Seminaire s JOIN s.etudiants e where e.id = :idEtu").setParameter("idEtu", idEtu);
	        return (List<Seminaire>) query.getResultList();
	    }
	    
	    
	    public long getSeminaireId(int idEtu){
	    	Query query= em.createQuery("SELECT count(s) FROM Seminaire s JOIN s.etudiants e where e.id = :idEtu").setParameter("idEtu", idEtu);
	        return (long)query.getSingleResult();
	    }
	    
	    public long  getCountSeminaireSection(String section){
	    	Query query= em.createQuery("select count(s) from Seminaire s where s.section like :section").setParameter("section", section);
	        return (long)query.getSingleResult();
		 }
	    
	    public Seminaire updateSeminaire(Seminaire seminaire) {
	    	em.merge(seminaire);
	    	return seminaire;
	    }
	    
	    public void deleteSemi(Seminaire s)
	    {
	        Query query = em.createQuery("delete from Seminaire s where s.id = :id ");
	        query.setParameter("id", s.getId());
	        query.executeUpdate();
	    }
	    
	    public void deleteSemiEtudiant(String idSec, int idEtu) {
		   	 Query query = em.createQuery("Delete from Seminaire u JOIN fetch u.etudiants p where u.id = :idSec AND p.id = :idEtu");
			 query.setParameter("idSec", idSec);
			 query.setParameter("idEtu", idEtu);
			 query.executeUpdate();
	    }
	    
	    //Requête pour vérifier si l'étudiant est déja inscrit au séminaire
	    public int etudiantInscrit(int idSec, int idEtu) {
		   	 Query query = em.createQuery("select count(u) from Seminaire u JOIN fetch u.etudiants p where u.id = :idSec AND p.id = :idEtu");
			 query.setParameter("idSec", idSec);
			 query.setParameter("idEtu", idEtu);
			 return ((Number) query.getSingleResult()).intValue();
	    }
	    
	    public void close()
	    {
	        em.clear();
	        em.close();
	    } 
		
}
