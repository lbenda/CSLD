package cz.larpovadatabaze.components.page.author;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.components.panel.game.GameListPanel;
import cz.larpovadatabaze.components.panel.game.ListGamesWithAnnotations;
import cz.larpovadatabaze.components.panel.user.PersonDetailPanel;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.providers.SortableAnnotatedProvider;
import cz.larpovadatabaze.services.CsldUserService;
import cz.larpovadatabaze.services.GameService;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 29.4.13
 * Time: 9:46
 */
public class AuthorDetail extends CsldBasePage {
    @SpringBean
    CsldUserService csldUserService;
    @SpringBean
    GameService gameService;

    public AuthorDetail(PageParameters params){
        Integer authorId = params.get("id").to(Integer.class);
        CsldUser author = csldUserService.getById(authorId);

        add(new PersonDetailPanel("personDetail",author));

        SortableAnnotatedProvider provider = new SortableAnnotatedProvider(gameService);
        provider.setAuthor(author);
        add(new ListGamesWithAnnotations("annotatedGamesOfAuthor", provider));

        add(new GameListPanel("gamesPanel",author.getAuthorOf()));
    }
}
