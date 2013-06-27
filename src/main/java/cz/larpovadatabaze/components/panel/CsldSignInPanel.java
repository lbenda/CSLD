package cz.larpovadatabaze.components.panel;

import cz.larpovadatabaze.security.CsldAuthenticatedWebSession;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 30.4.13
 * Time: 12:22
 */
public class CsldSignInPanel extends SignInPanel {
    private String username;
    private String password;

    public CsldSignInPanel(String id) {
        super(id);
    }

    /**
     * @see org.apache.wicket.Component#onBeforeRender()
     */
    @Override
    protected void onBeforeRender()
    {
        // logged in already?
        if (isSignedIn() == false)
        {
            IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();
            // get username and password from persistence store
            String[] data = authenticationStrategy.load();

            if ((data != null) && (data.length > 1))
            {
                // try to sign in the user
                if (signIn(data[0], data[1]))
                {
                    username = data[0];
                    password = data[1];

                    // logon successful. Continue to the original destination
                    continueToOriginalDestination();
                    // Ups, no original destination. Go to the home page
                    throw new RestartResponseException(getSession().getPageFactory().newPage(
                            getApplication().getHomePage()));
                }
                else
                {
                    // the loaded credentials are wrong. erase them.
                    authenticationStrategy.remove();
                }
            }
        }

        // don't forget
        super.onBeforeRender();
    }

    protected boolean signIn(String username, String password){
        return ((CsldAuthenticatedWebSession)CsldAuthenticatedWebSession.get()).signIn(username, password);
    }

    protected boolean isSignedIn(){
        return CsldAuthenticatedWebSession.get().isSignedIn();
    }
}