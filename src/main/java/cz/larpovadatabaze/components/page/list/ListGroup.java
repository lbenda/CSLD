package cz.larpovadatabaze.components.page.list;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.panel.group.AddGroupPanel;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.providers.SortableGroupProvider;
import cz.larpovadatabaze.services.GroupService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 2.5.13
 * Time: 17:15
 */
public class ListGroup extends CsldBasePage {
    @SpringBean
    private GroupService groupService;

    public ListGroup() {
        SortableGroupProvider sgp = new SortableGroupProvider(groupService);

        final DataView<CsldGroup> groupList = new DataView<CsldGroup>("listGroups", sgp) {

            @Override
            protected void populateItem(Item<CsldGroup> item) {
                CsldGroup group = item.getModelObject();
                final Label label = new Label("groupName", group.getName());
                item.add(label);
            }
        };
        groupList.setOutputMarkupId(true);
        groupList.setItemsPerPage(25L);

        add(groupList);
        add(new AddGroupPanel("addGroup"));
    }
}