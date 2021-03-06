package cz.larpovadatabaze.services.impl;

import cz.larpovadatabaze.dao.GroupDAO;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.exceptions.WrongParameterException;
import cz.larpovadatabaze.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDAO groupDAO;

    @Override
    public boolean insert(CsldGroup group) {
        return groupDAO.saveOrUpdate(group);
    }

    @Override
    public List<CsldGroup> getAll() {
        return groupDAO.findAll();
    }

    @Override
    public List<CsldGroup> getUnique(CsldGroup example) {
        return groupDAO.findByExample(example, new String[]{});
    }

    @Override
    public List<CsldGroup> orderedByName(long first, long amountPerPage) {
        return groupDAO.orderedByName(first, amountPerPage);
    }

    @Override
    public void remove(CsldGroup toRemove) {
        groupDAO.makeTransient(toRemove);
    }

    @Override
    public CsldGroup getById(Integer id){
        return groupDAO.findById(id, false);
    }

    @Override
    public List<CsldGroup> getByAutoCompletable(String groupName) throws WrongParameterException {
        return groupDAO.getByAutoCompletable(groupName);
    }

    @Override
    public void saveOrUpdate(CsldGroup group) {
        groupDAO.saveOrUpdate(group);
    }

    @Override
    public int getAmountOfGroups() {
        return groupDAO.getAmountOfGroups();
    }

    @Override
    public int getAverageOfGroup(CsldGroup group) {
        return groupDAO.getAverageOfGroup(group);
    }
}
