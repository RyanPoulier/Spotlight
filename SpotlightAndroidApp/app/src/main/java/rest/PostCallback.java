package rest;

/**
 * Created by shamal on 8/16/16.
 */
public abstract class PostCallback {
    /**
     * Called when a POST success response is received. <br/>
     * This method is guaranteed to execute on the UI thread.
     */
    public abstract void onPostSuccess();

}
