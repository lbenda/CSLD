package cz.larpovadatabaze.components.page.game;

import cz.larpovadatabaze.components.page.CsldBasePage;
import cz.larpovadatabaze.entities.Comment;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.Rating;
import cz.larpovadatabaze.entities.UserPlayedGame;
import cz.larpovadatabaze.services.CommentService;
import cz.larpovadatabaze.services.GameService;
import cz.larpovadatabaze.services.RatingService;
import cz.larpovadatabaze.services.UserPlayedGameService;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 15.5.13
 * Time: 13:09
 */
public class DeleteGamePage extends CsldBasePage {
    @SpringBean
    GameService gameService;
    @SpringBean
    CommentService commentService;
    @SpringBean
    RatingService ratingService;
    @SpringBean
    UserPlayedGameService userPlayedGameService;

    public DeleteGamePage(PageParameters params) {
        Integer gameId = params.get("id").to(Integer.class);

        Game game = gameService.getById(gameId);
        if(game != null) {
            List<Comment> comments = game.getComments();
            for(Comment comment : comments){
                commentService.remove(comment);
            }
            List<Rating> ratings= game.getRatings();
            for(Rating rating: ratings){
                ratingService.remove(rating);
            }
            List<UserPlayedGame> players = game.getPlayed();
            for(UserPlayedGame playedGame: players){
                userPlayedGameService.remove(playedGame);
            }
            gameService.remove(game);
        }

        throw new RestartResponseException(ListGame.class);
    }
}
