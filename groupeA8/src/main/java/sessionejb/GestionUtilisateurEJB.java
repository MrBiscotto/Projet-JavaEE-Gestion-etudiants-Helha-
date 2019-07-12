package sessionejb;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAOUtilisateur;
import entities.Utilisateur;

@Stateless
@LocalBean
public class GestionUtilisateurEJB implements IGestionUtilisateurEJBRemote{


    @EJB
    private DAOUtilisateur dao;
    public GestionUtilisateurEJB()
    {

    }

    public List<Utilisateur> selectAll() {
        return dao.selectAll();
    }

    public Utilisateur addUtilisateur(Utilisateur u) throws NoSuchAlgorithmException {
        if(u==null || u.getNom()==null)                     //Logique métier présente ici et pas dans le dao pcq le dao est laissé au spécialiste sql (et non pro pret hypotequaire)
        {
            return null;
        }
        return dao.addUtilisateur(u);
    }

    public void deleteUtilisateur(Utilisateur u)
    {
        dao.deleteUtilisateur(u);
    }

	public Utilisateur updateUtilisateur(Utilisateur u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Utilisateur getUtilisateur(Utilisateur u) {
		return dao.getUtilisateur(u);
	}
	
	public boolean verifUtilisateur(String nomUtil, String mdpUtil) throws NoSuchAlgorithmException {
		return dao.verifUtilisateur(nomUtil, mdpUtil);
	}
	
	public Utilisateur getUtilisateur(String nomUtil, String mdpUtil) {
		return dao.getUtilisateur(nomUtil, mdpUtil);
	}
	
	public int utilisateurExist(String nomUtil) {
		return dao.utilisateurExist(nomUtil);
	}
	
	
}