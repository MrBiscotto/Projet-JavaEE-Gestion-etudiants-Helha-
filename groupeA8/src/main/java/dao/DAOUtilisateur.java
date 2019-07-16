package dao;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Utilisateur;

@Stateless
@LocalBean
public class DAOUtilisateur implements Serializable{

    @PersistenceContext(unitName="groupeA8")
    private EntityManager em;//JAMAIS DEVOIoR INSTANCIER DES COMPOSANTS EJB
    //TOUJOURS UN CONSTRUCTEUR VIDE
    public DAOUtilisateur() {

    }

    public List<Utilisateur> selectAll()
    {
        Query query= em.createQuery("select u from Utilisateur u");
        return (List<Utilisateur>) query.getResultList();
    }

    public Utilisateur addUtilisateur(Utilisateur u) throws NoSuchAlgorithmException
    {
		//Hachage du mot de passe
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String mdp = u.getMotDePasse() + u.getNom();
        md.update(mdp.getBytes());

        byte byteData[] = md.digest();

        //convertir le tableau de bits en une format hexadécimal - méthode 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        u.setMotDePasse(sb.toString());

        em.persist(u);
        return u;


    }

    public Utilisateur getUtilisateur(Utilisateur utilisateur)
    {
        Query query= em.createQuery("select u from Utilisateur u where u.nom = :utilisateur ");
        query.setParameter("utilisateur", utilisateur);
        return (Utilisateur) query.getSingleResult();
    }


    public void deleteUtilisateur(Utilisateur u)
    {
        Query query = em.createQuery("delete from Utilisateur u where u.nom = :utilisateur ");
        query.setParameter("utilisateur", u.getNom());
        query.executeUpdate();
    }
    
    public boolean verifUtilisateur(String nomUtil, String mdpUtil) throws NoSuchAlgorithmException {
    		
    	 Query query = em.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.nom = :nomUtil and u.motDePasse = :mdpUtil");
    	 query.setParameter("nomUtil", nomUtil);
    	 query.setParameter("mdpUtil", mdpUtil);
         return (long)query.getSingleResult() > 0 ? true : false;
    }
    
    public void close()
    {
        em.clear();
        em.close();
    }
    
    public Utilisateur getUtilisateur(String nomUtil, String mdpUtil) {
    	 Query query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :nomUtil and u.motDePasse = :mdpUtil");
    	 query.setParameter("nomUtil", nomUtil);
    	 query.setParameter("mdpUtil", mdpUtil);
    	 return (Utilisateur) query.getSingleResult();
	}
    
    public int utilisateurExist(String nomUtil) {
	   	 Query query = em.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.nom = :nomUtil");
		 query.setParameter("nomUtil", nomUtil);
		 return ((Number) query.getSingleResult()).intValue();
    }
}

