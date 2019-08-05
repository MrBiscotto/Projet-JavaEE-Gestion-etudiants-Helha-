package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.NoteEtudiant;

@Stateless
@LocalBean
public class DAONoteEtudiant implements Serializable{

 	@PersistenceContext(unitName="groupeA8")
    private EntityManager em;
 	
 	public DAONoteEtudiant() {}
 	
    public List<NoteEtudiant> selectNotesEtu(int idEtu)
    {
        Query query= em.createQuery("select s from NoteEtudiant s where s.etuId = :idEtu");
        query.setParameter("idEtu", idEtu);
        return (List<NoteEtudiant>) query.getResultList();    
    }
    
    public NoteEtudiant addNote(NoteEtudiant u)
    {
        em.persist(u);
        return u;    
    }
 	
    public NoteEtudiant updateNote(NoteEtudiant note) {
    	em.merge(note);
    	return note;
    }
 	
    public void deleteAllNote() {
    	Query query = em.createQuery("DELETE FROM NoteEtudiant s WHERE s.id >=0 ");
    	query.executeUpdate();
    }
    
    public void deleteNote(NoteEtudiant s)
    {
        Query query = em.createQuery("DELETE FROM NoteEtudiant s WHERE s.id = :id ");
        query.setParameter("id", s.getId());
        query.executeUpdate();
    }
    
    public void deleteNotesEtudiant(int id) {
        Query query = em.createQuery("DELETE FROM NoteEtudiant s WHERE s.etuId = :id ");
        query.setParameter("id", id);
        query.executeUpdate();
    }
 	
    public void close()
    {
        em.clear();
        em.close();
    } 
}
