package cz.larpovadatabaze.services.impl;

import cz.larpovadatabaze.dao.GroupHasMembersDAO;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.entities.GroupHasMember;
import cz.larpovadatabaze.services.GroupHasMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class GroupHasMemberServiceImpl implements GroupHasMemberService {
    @Autowired
    GroupHasMembersDAO groupHasMembersDAO;

    @Override
    public List<GroupHasMember> getAll() {
        return groupHasMembersDAO.findAll();
    }

    @Override
    public List<GroupHasMember> getUnique(GroupHasMember example) {
        return groupHasMembersDAO.findByExample(example, new String[]{});
    }

    @Override
    public void remove(GroupHasMember toRemove) {
        groupHasMembersDAO.makeTransient(toRemove);
    }

    @Override
    public void saveOrUpdate(GroupHasMember memberOfGroup) {
        groupHasMembersDAO.saveOrUpdate(memberOfGroup);
    }

    @Override
    public void removeAllMembersOfGroup(CsldGroup group) {
        groupHasMembersDAO.removeAllMembersOfGroup(group);
    }
}
