package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAOEtudiant;
import entities.Etudiant;
import entities.Seminaire;

@Stateless
@LocalBean
public class GestionEtudiantEJB implements IGestionEtudiantEJBRemote{

   
   @EJB
   private DAOEtudiant dao;
   public GestionEtudiantEJB()
   {
       
   }
   
  
   public List<Etudiant> selectAll() {
       return dao.selectAll();
   }
  
   
   public List<Etudiant> selectAllEtudiantSection(String orientation) {
       return dao.selectAllEtudiantSection(orientation);
   }
   
   public void deleteEtudiant(Etudiant u)
   {
       dao.deleteEtudiant(u);
   }
   
  /* public Etudiant updateEtudiantSeminaire(Etudiant e) {
       if(e == null) return null;
 
       return dao.updateEtudiantSeminaire(e);
	}
*/
   public Etudiant addEtudiant(Etudiant e) {
       if(e==null)                     //Logique métier présente ici et pas dans le dao pcq le dao est laissé au spécialiste sql (et non pro pret hypotequaire)
       {
           return null;
       }
       return dao.addEtudiant(e);
   }
 
}
