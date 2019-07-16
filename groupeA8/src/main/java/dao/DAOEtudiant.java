package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Etudiant;

@Stateless
@LocalBean
public class DAOEtudiant implements Serializable {
	 @PersistenceContext(unitName="groupeA8")
    private EntityManager em;//JAMAIS DEVOIoR INSTANCIER DES COMPOSANTS EJB
    //TOUJOURS UN CONSTRUCTEUR VIDE
    public DAOEtudiant() {
        
    }
    
    public List<Etudiant> selectAll()
    {
        Query query= em.createQuery("select e from Etudiant e");
        return (List<Etudiant>) query.getResultList();    
    }
    
    public List<Etudiant> selectAllEtudiantSection(String section)
    {
        Query query= em.createQuery("select e from Etudiant e where e.section = :section").setParameter("section", section);
        return (List<Etudiant>) query.getResultList();
    }
    
    
    public Etudiant addEtudiant(Etudiant u)
    {
        em.persist(u);
        return u;    
    }
    
    
    /*public Etudiant updateEtudiantSeminaire(Etudiant newEtu) {
       
        return (Etudiant) query.getSingleResult();
    }
    */
    
    public Etudiant getEtudiant(int idEtu)
    {
        Query query= em.createQuery("select e from Etudiant e where e.id = :id ");
        query.setParameter("id", idEtu);
        return (Etudiant) query.getSingleResult();
    }
    

    public void deleteEtudiant(Etudiant e)
    {
        Query query = em.createQuery("delete from Etudiant e where e.id = :id ");
        query.setParameter("id", e.getId());
        query.executeUpdate();
    }
    
    public void deleteAll() {
    	Query query = em.createQuery("delete from Etudiant e where e.id >= 0 ");
    	query.executeUpdate();
    }
    
    public void close()
    {
        em.clear();
        em.close();
    } 
	
}
