package sessionejb;

import java.util.List;

import javax.ejb.Remote;

import entities.Seminaire;

@Remote
public interface IGestionSeminaireEJBRemote {
    List<Seminaire> selectAll();
    Seminaire addSemi(Seminaire s);
    void deleteSemi(Seminaire s);
}