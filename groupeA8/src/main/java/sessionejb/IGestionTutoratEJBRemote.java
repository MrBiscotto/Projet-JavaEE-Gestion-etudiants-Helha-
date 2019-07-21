package sessionejb;

import java.util.List;

import javax.ejb.Remote;

import entities.Tutorat;

@Remote
public interface IGestionTutoratEJBRemote {
    List<Tutorat> selectAll();
    Tutorat addTutorat(Tutorat u);
    void deleteTutorat(Tutorat u);
    Tutorat getTutorat(int u);
}
