package cz.larpovadatabaze.services;

import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.exceptions.WrongParameterException;

import java.util.List;

/**
 *
 */
public interface CsldUserService extends GenericService<CsldUser> {
    public CsldUser getById(Integer id);

    boolean saveOrUpdate(CsldUser user);

    void flush();

    List<CsldUser> getAuthorsByGames(long first, long amountPerPage);

    List<CsldUser> getEditors();

    List<CsldUser> getAdmins();

    CsldUser getWithMostComments();

    CsldUser getWithMostAuthored();

    CsldUser authenticate(String username, String password);

    List<CsldUser> getAuthorsByBestGame(long first, long amountPerPage);

    List<CsldUser> getByAutoCompletable(String autoCompletable) throws WrongParameterException;

    List<CsldUser> getOrderedUsersByName(long first, long amountPerPage);

    List<CsldUser> getOrderedUsersByComments(long first, long amountPerPage);

    List<CsldUser> getOrderedUsersByPlayed(long first, long amountPerPage);

    CsldUser getByEmail(String mail);

    int getAmountOfAuthors();

    int getAmountOfOnlyAuthors();

    List<CsldUser> getAuthorsByName(long first, long amountPerPage);
}
