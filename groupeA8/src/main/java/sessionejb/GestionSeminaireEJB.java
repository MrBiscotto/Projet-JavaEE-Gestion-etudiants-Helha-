package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAOSeminaire;
import entities.Seminaire;

@Stateless
@LocalBean
public class GestionSeminaireEJB implements IGestionSeminaireEJBRemote {

	 @EJB
	    private DAOSeminaire dao;
	    public GestionSeminaireEJB()
	    {

	    }

	    public List<Seminaire> selectAll() {
	        return dao.selectAll();
	    }

	    
	    public List<Seminaire> selectSeminaireSection(String idSection) {
	        return dao.selectSeminaireSection(idSection);
	    }
	    
	    public Seminaire addSemi(Seminaire u) {
	        if(u==null)             
	        {
	            return null;
	        }
	        return dao.addSemi(u);
	    }

	    public void deleteSemi(Seminaire u)
	    {
	        dao.deleteSemi(u);
	    }
	    
	    public void updateSeminaire(Seminaire seminaire) {
	    	dao.updateSeminaire(seminaire);
	    }
		
		public Seminaire getSemi(int u) {
			return dao.getSeminaire(u);
		}
		
		 public List<Seminaire> selectSeminaireId(int idEtu){
			 return dao.selectSeminaireId(idEtu);
		 }
		 
		 public long getSeminaireId(int idEtu){
			 return dao.getSeminaireId(idEtu);
		 }
		 
		 public long  getCountSeminaireSection(String section){
			 return dao.getCountSeminaireSection(section);
		 }
		 
		 public int etudiantInscrit(int idSec,int idEtu) {
			 return dao.etudiantInscrit(idSec, idEtu);
		 }
	 
	}