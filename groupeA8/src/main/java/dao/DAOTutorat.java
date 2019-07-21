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
        
	    public  List<Tutorat> selectTutoSection(String idSection)
	    {
	    	Query query= em.createQuery("select s from Tutorat s where s.section like :section").setParameter("section", idSection);
	        return (List<Tutorat>) query.getResultList();
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
        

        public void deleteTutorat(Tutorat p)
        {
            Query query = em.createQuery("delete from Tutorat c where c.id = :id ");
            query.setParameter("id", p.getId());
            query.executeUpdate();
        }
        public void close()
        {
            em.clear();
            em.close();
        } 
        
}
