package sessionejb;

import java.util.List;

import javax.ejb.Remote;

import entities.NoteEtudiant;

@Remote
public interface IGestionNoteEtudiantEJBRemote {
    List<NoteEtudiant> selectNotesEtu(int idEtu);
    NoteEtudiant addNote(NoteEtudiant s);
    NoteEtudiant updateNote(NoteEtudiant note);
    void deleteNote(NoteEtudiant s);
}
