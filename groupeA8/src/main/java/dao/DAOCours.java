package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Cours;

@Stateless
@LocalBean
public class DAOCours {

     @PersistenceContext(unitName="groupeA8")
        private EntityManager em;
       
        public DAOCours() {
            
        }
        
        public List<Cours> selectAll()
        {
            Query query= em.createQuery("select c from Cours");
            return (List<Cours>) query.getResultList();    
        }
        
        public Cours addCours(Cours u)
        {
            em.persist(u);
            return u;    
        }
        
   
        public Cours getCours(int idCours)
        {
            Query query= em.createQuery("select c from Cours c where c.Id = :id ");
            query.setParameter("id", idCours);
            return (Cours) query.getSingleResult();
        }
        

        public void deleteCours(Cours p)
        {
            Query query = em.createQuery("delete from Cours c where c.Id = :id ");
            query.setParameter("id", p.getId());
            query.executeUpdate();
        }
        public void close()
        {
            em.clear();
            em.close();
        } 
        
}