package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Remediation;
import entities.Tutorat;

@Stateless
@LocalBean
public class DAORemediation {

    @PersistenceContext(unitName="groupeA8")
    private EntityManager em;
    
    public DAORemediation() {}
    
    public List<Remediation> getRemediationEtudiant(int idTuto){
    	Query query= em.createQuery("SELECT u FROM Remediation u WHERE u.tutoId = :id");
    	query.setParameter("id", idTuto);
        return (List<Remediation>) query.getResultList();
    }
    
    public Remediation addRemediation(Remediation reme) {
        em.persist(reme);
        return reme;  
    }
    
    public int getNbReme(int idTuto) {
	   	 Query query = em.createQuery("select count(u) from Remediation u where u.tutoId = :id");
	   	query.setParameter("id", idTuto);
		 return ((Number) query.getSingleResult()).intValue();
    }
    
    public void deleteAllRemediation(int tutoId) {
        Query query = em.createQuery("DELETE FROM Remediation c WHERE c.tutoId = :id");
        query.setParameter("id", tutoId);
        query.executeUpdate();
    }
    
    public void deleteRemediation(Remediation r) {
        Query query = em.createQuery("DELETE FROM Remediation c WHERE c.id = :id ");
        query.setParameter("id", r.getId());
        query.executeUpdate();
    }
    
    public List<Object[]> getListRemeEtu(int idEtu){
    	Query query= em.createQuery("SELECT t.nom,r.date FROM Remediation r INNER JOIN Tutorat t on t.id = r.tutoId WHERE t.etuId = :id ORDER BY t.nom");
    	query.setParameter("id", idEtu);
        return query.getResultList();
    }
    
    public void close()
    {
        em.clear();
        em.close();
    } 
}
