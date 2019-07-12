package sessionejb;

import java.util.List;

import javax.ejb.Remote;

import entities.Etudiant;
import entities.Seminaire;

@Remote
public interface IGestionEtudiantEJBRemote {
	 List<Etudiant> selectAll();
	 Etudiant addEtudiant(Etudiant e);
	 void deleteEtudiant(Etudiant s);
}
