package cz.larpovadatabaze.services.impl;

import cz.larpovadatabaze.dao.LabelDAO;
import cz.larpovadatabaze.entities.Label;
import cz.larpovadatabaze.exceptions.WrongParameterException;
import cz.larpovadatabaze.services.LabelService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelDAO labelDAO;

    public List<Label> getAll(){
        return labelDAO.findAll();
    }

    @Override
    public List<Label> getUnique(Label example) {
        return labelDAO.findByExample(example, new String[]{});
    }

    public List<Label> getRequired(){
        Criterion[] criterions = new Criterion[1];
        criterions[0] = Restrictions.eq("required", "true");
        return labelDAO.findByCriteria(criterions);
    }

    public List<Label> getOptional(){
        Criterion[] criterions = new Criterion[1];
        criterions[0] = Restrictions.eq("required", "false");
        return labelDAO.findByCriteria(criterions);
    }

    @Override
    public void update(Label label) {
        labelDAO.saveOrUpdate(label);
    }

    @Override
    public List<Label> getByAutoCompletable(String labelName) throws WrongParameterException {
        return labelDAO.getByAutoCompletable(labelName);
    }

    @Override
    public boolean saveOrUpdate(Label label) {
        return labelDAO.saveOrUpdate(label);
    }

    @Override
    public void remove(Label toRemove) {
        labelDAO.makeTransient(toRemove);
    }
}
