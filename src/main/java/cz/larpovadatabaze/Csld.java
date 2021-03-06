package cz.larpovadatabaze;

import cz.larpovadatabaze.components.page.HomePage;
import cz.larpovadatabaze.components.page.about.AboutDatabase;
import cz.larpovadatabaze.components.page.admin.Administration;
import cz.larpovadatabaze.components.page.admin.ManageLabelsPage;
import cz.larpovadatabaze.components.page.admin.ManageUserRightsPage;
import cz.larpovadatabaze.components.page.author.AuthorDetail;
import cz.larpovadatabaze.components.page.author.CreateOrUpdateAuthorPage;
import cz.larpovadatabaze.components.page.author.ListAuthor;
import cz.larpovadatabaze.components.page.game.*;
import cz.larpovadatabaze.components.page.group.CreateOrUpdateGroupPage;
import cz.larpovadatabaze.components.page.group.GroupDetail;
import cz.larpovadatabaze.components.page.group.ListGroup;
import cz.larpovadatabaze.components.page.group.ManageGroupPage;
import cz.larpovadatabaze.components.page.search.SearchResults;
import cz.larpovadatabaze.components.page.user.*;
import cz.larpovadatabaze.converters.CsldUserConverter;
import cz.larpovadatabaze.converters.GameConverter;
import cz.larpovadatabaze.converters.GroupConverter;
import cz.larpovadatabaze.converters.LabelConverter;
import cz.larpovadatabaze.entities.CsldGroup;
import cz.larpovadatabaze.entities.CsldUser;
import cz.larpovadatabaze.entities.Game;
import cz.larpovadatabaze.entities.Label;
import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import cz.larpovadatabaze.services.CsldUserService;
import cz.larpovadatabaze.services.GameService;
import cz.larpovadatabaze.services.GroupService;
import cz.larpovadatabaze.services.LabelService;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 */
@Component(value = "wicketApplication")
public class Csld extends AuthenticatedWebApplication implements ApplicationContextAware
{
    @Autowired
    private CsldUserService csldUserService;
    @Autowired
    private GameService gameService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private LabelService labelService;

    private static final String DEFAULT_ENCODING = "UTF-8";
    private ApplicationContext ctx;

    /**
     * Constructor
     */
	public Csld()
	{
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for ( Logger logger : loggers ) {
            logger.setLevel(Level.OFF);
        }

        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx, true));
        getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);
        getMarkupSettings().setStripWicketTags(true);
        getRequestCycleSettings().setResponseRequestEncoding(DEFAULT_ENCODING);


        mountPages();
	}

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return CsldAuthenticatedWebSession.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return CsldSignInPage.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected IConverterLocator newConverterLocator() {
        ConverterLocator locator = (ConverterLocator) super.newConverterLocator();

        locator.set(CsldUser.class, new CsldUserConverter(csldUserService));
        locator.set(Game.class, new GameConverter(gameService));
        locator.set(CsldGroup.class, new GroupConverter(groupService));
        locator.set(Label.class, new LabelConverter(labelService));

        return locator;

    }

    private void mountPages() {
        mountPage("/sign-out", SignOut.class);
        mountPage("/sign-in", CsldSignInPage.class);
        mountPage("/register", CreateOrUpdateUserPage.class);
        mountPage("/edit-user", UpdateUserPage.class);

        mountPage("/add-group", CreateOrUpdateGroupPage.class);
        mountPage("/add-game", CreateOrUpdateGamePage.class);
        mountPage("/add-author", CreateOrUpdateAuthorPage.class);

        mountPage("/zebricky", ListGame.class);
        mountPage("/autori", ListAuthor.class);
        mountPage("/uzivatele", ListUser.class);
        mountPage("/skupiny", ListGroup.class);

        mountPage("/detail-game", GameDetail.class);
        mountPage("/detail-author", AuthorDetail.class);
        mountPage("/detail-user", UserDetail.class);
        mountPage("/detail-group", GroupDetail.class);

        mountPage("/last-comments", ListComments.class);
        mountPage("/last-games", ListLastGames.class);
        mountPage("/manage-group", ManageGroupPage.class);
        mountPage("/search", SearchResults.class);

        mountPage("/oDatabazi", AboutDatabase.class);
        mountPage("/reset", ResetPassword.class);
        mountPage("/forgot-password", ForgotPassword.class);

        mountPage("/admin", Administration.class);
        mountPage("/admin/manage-labels", ManageLabelsPage.class);
        mountPage("/admin/manage-users", ManageUserRightsPage.class);

        mountPage("/home", HomePage.class);
    }

    public static String getBaseContext(){
        return "/files/upload/";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
