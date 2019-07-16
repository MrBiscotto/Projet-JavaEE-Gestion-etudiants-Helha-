package sessionejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dao.DAONoteEtudiant;
import entities.NoteEtudiant;

@Stateless
@LocalBean
public class GestionNoteEtudiantEJB implements IGestionNoteEtudiantEJBRemote {

	 @EJB
	 private DAONoteEtudiant dao;
	 public GestionNoteEtudiantEJB()
	 {
	       
	 }
	
	public List<NoteEtudiant> selectNotesEtu(int idEtu) {
		// TODO Auto-generated method stub
		return dao.selectNotesEtu(idEtu);
	}

	@Override
	public NoteEtudiant addNote(NoteEtudiant s) {
		// TODO Auto-generated method stub
		return dao.addNote(s);
	}
	
	@Override
	public void deleteNote(NoteEtudiant s) {
		// TODO Auto-generated method stub
		dao.deleteNote(s);
	}

	@Override
	public NoteEtudiant updateNote(NoteEtudiant note) {
		// TODO Auto-generated method stub
		return dao.updateNote(note);
	}

}
