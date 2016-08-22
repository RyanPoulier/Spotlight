package rest;

import android.provider.ContactsContract;

/**
 * Created by shamal on 8/16/16.
 */
public abstract class GetResponseCallback{

        /**
         * Called when the response data for the REST call is ready. <br/>
         * This method is guaranteed to execute on the UI thread.
         *
         * @param profile The {@code Profile} that was received from the server.
         */
        abstract void onDataReceived(ContactsContract.Profile profile);

    /*
     * Additional methods like onPreGet() or onFailure() can be added with default implementations.
     * This is why this has been made and abstract class rather than Interface.
     */
}
