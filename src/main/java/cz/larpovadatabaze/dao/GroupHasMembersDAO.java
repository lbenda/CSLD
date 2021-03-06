package cz.larpovadatabaze.dao;

import cz.larpovadatabaze.api.GenericHibernateDAO;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.entities.GroupHasMember;
import cz.larpovadatabaze.entities.GroupHasMemberPK;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class GroupHasMembersDAO extends GenericHibernateDAO<GroupHasMember, GroupHasMemberPK> {
    public void removeAllMembersOfGroup(CsldGroup group) {
        int groupId = group.getId();
        String removeMembers = "delete from GroupHasMember where groupId= :groupId";
        Query query = sessionFactory.getCurrentSession().createQuery(removeMembers).setInteger("groupId", groupId);
        query.executeUpdate();
        flush();
    }
}
