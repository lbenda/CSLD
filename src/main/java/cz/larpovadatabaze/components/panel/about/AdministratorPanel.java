package cz.larpovadatabaze.components.panel.about;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.user.UserDetail;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.services.CsldUserService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * It shows information about all administrators of the database.
 */
public class AdministratorPanel extends Panel {
    @SpringBean
    CsldUserService csldUserService;

    public AdministratorPanel(String id) {
        super(id);

        List<CsldUser> admins = csldUserService.getAdmins();
        ListView<CsldUser> editorsList = new ListView<CsldUser>("listAdministrator", admins) {
            @Override
            protected void populateItem(ListItem<CsldUser> item) {
                CsldUser admin = item.getModelObject();

                PageParameters params = new PageParameters();
                params.add("userId", admin.getId());
                final BookmarkablePageLink<CsldBasePage> adminLink =
                        new BookmarkablePageLink<CsldBasePage>("administratorLink", UserDetail.class, params);
                final Image adminImage = new Image("administratorImage",
                        new ContextRelativeResource(admin.getImage().getPath()));
                adminLink.add(adminImage);
                item.add(adminLink);

                final BookmarkablePageLink<CsldBasePage> administratorLinkContent =
                        new BookmarkablePageLink<CsldBasePage>("administratorLinkContent", UserDetail.class, params);
                final Label administratorNick = new Label("nicknameAdministrator", admin.getPerson().getNickname());
                final Label administratorName = new Label("nameAdministrator", admin.getPerson().getName());
                administratorLinkContent.add(administratorNick);
                administratorLinkContent.add(administratorName);
                item.add(administratorLinkContent);

                final Label administratorMail = new Label("administratorMail", admin.getPerson().getEmail());
                item.add(administratorMail);
            }
        };
        add(editorsList);

    }
}
