package cz.larpovadatabaze.components.page.author;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.page.game.GameDetail;
import cz.larpovadatabaze.components.panel.author.AuthorsPanel;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.providers.SortableAuthorProvider;
import cz.larpovadatabaze.services.CsldUserService;
import cz.larpovadatabaze.services.GameService;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 *
 */
public class ListAuthor extends CsldBasePage {
    @SpringBean
    CsldUserService csldUserService;
    @SpringBean
    GameService gameService;

    @SuppressWarnings("unchecked")
    public ListAuthor(){
        Image authorsIcon = new Image("authorsIcon", new ContextRelativeResource(cz.larpovadatabaze.entities.Image.getLightBulbIconPath()));
        add(authorsIcon);

        SortableAuthorProvider sap = new SortableAuthorProvider(csldUserService);
        final DataView<CsldUser> propertyList = new DataView<CsldUser>("listAuthor",sap) {
            @Override
            protected void populateItem(Item<CsldUser> item) {
                CsldUser actualAuthor = item.getModelObject();

                PageParameters params = new PageParameters();
                params.add("id", actualAuthor.getId());

                final BookmarkablePageLink<CsldUser> authorName =
                        new BookmarkablePageLink<CsldUser>("authorName", AuthorDetail.class, params);
                final Label nicknameLabel = new Label("authorNicknameContent", actualAuthor.getPerson().getNickname());
                final Label nameLabel = new Label("authorNameContent", actualAuthor.getPerson().getName());
                authorName.add(nameLabel);
                authorName.add(nicknameLabel);
                item.add(authorName);

                Game bestGame = actualAuthor.getBestGame();
                PageParameters gameParams = new PageParameters();
                int bestGameId = (bestGame == null) ? -1: bestGame.getId();
                gameParams.add("id", bestGameId);

                final BookmarkablePageLink<CsldUser> gameName =
                        new BookmarkablePageLink<CsldUser>("gameName", GameDetail.class, gameParams);
                final Label bestGameName = new Label("bestGameName", bestGame != null ? bestGame.getName(): "");
                final Label bestGameRating = new Label("bestGame", bestGame != null ? bestGame.getTotalRating(): 0);
                gameName.add(bestGameName);
                gameName.add(bestGameRating);
                item.add(gameName);
            }
        };
        propertyList.setOutputMarkupId(true);
        propertyList.setItemsPerPage(25L);

        add(propertyList);
        add(new PagingNavigator("navigator", propertyList));

        add(new OrderByBorder("orderByName", "form.wholeName", sap)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged()
            {
                propertyList.setCurrentPage(0);
            }
        });

        add(new OrderByBorder("orderByBestGame", "bestGame.name", sap)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged()
            {
                propertyList.setCurrentPage(0);
            }
        });

        add(new AuthorsPanel("addAuthorPanel"));
    }
}
