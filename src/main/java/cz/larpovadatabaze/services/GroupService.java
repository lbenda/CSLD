package cz.larpovadatabaze.services;

import cz.larpovadatabaze.entities.CsldGroup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 17.4.13
 * Time: 18:18
 */
public interface GroupService extends GenericService<CsldGroup> {
    public void insert(CsldGroup group);

    List<CsldGroup> orderedByName();
}