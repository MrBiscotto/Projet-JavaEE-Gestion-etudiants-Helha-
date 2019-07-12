package sessionejb;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Remote;

import entities.Utilisateur;

@Remote
public interface IGestionUtilisateurEJBRemote {
    List<Utilisateur> selectAll();
    Utilisateur addUtilisateur(Utilisateur u) throws NoSuchAlgorithmException;
    Utilisateur updateUtilisateur(Utilisateur u);
    void deleteUtilisateur(Utilisateur u);
}