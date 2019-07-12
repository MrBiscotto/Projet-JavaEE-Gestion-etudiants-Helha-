 
package sessionejb;

import java.util.List;

import javax.ejb.Remote;

import entities.Cours;


@Remote
public interface IGestionCoursEJBRemote {
     List<Cours> selectAll();
     Cours addCours(Cours u);
     void deleteCours(Cours u);
     Cours getCours(int u);
     
}