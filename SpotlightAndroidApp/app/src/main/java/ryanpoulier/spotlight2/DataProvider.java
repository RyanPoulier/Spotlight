package ryanpoulier.spotlight2;

/**
 * Created by ASUS on 24/02/2016.
 */
public class DataProvider {


    private String title;
    private String issueType;
    private String timestamp;
    private String ID;


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public DataProvider(String title,String timestamp,String ID, String issueType){
        this.title= title;
        this.timestamp= timestamp;
        this.ID= ID;
        this.issueType = issueType;

    }

    public String getIssueType(){
        return issueType;
    }

    public  void setIssueType(String issueType){
        this.issueType = issueType;
    }


}
