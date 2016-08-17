package ryanpoulier.spotlight2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ASUS on 24/02/2016.
 */
public class CommentsDataAdapter extends ArrayAdapter<String> {

    String[] username = {};
    String[] commenttimestamp = {};
    String[] comment = {};
    Context c;
    LayoutInflater inflater;

    public CommentsDataAdapter(Context context, String[] username, String[] timestamp, String[] comment) {
        super(context, R.layout.comments_list_row, username);

        this.c = context;
        this.username = username;
        this.commenttimestamp = timestamp;
        this.comment = comment;
    }

    //View holder class
    public class ViewHolder {
        TextView USERNAME;
        TextView COMMENTTIMESTAMP;
        TextView COMMENT;
    }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.comments_list_row, null);
            }

            //Viewholder object
            final ViewHolder holder = new ViewHolder();

            //initializing the view
            holder.USERNAME = (TextView) convertView.findViewById(R.id.txt_comment_username);
            holder.COMMENTTIMESTAMP = (TextView) convertView.findViewById(R.id.txtCommentTimestamp);
            holder.COMMENT = (TextView) convertView.findViewById(R.id.txtComment);


            // assigning data to views

            holder.USERNAME.setText(username[position]);
            holder.COMMENTTIMESTAMP.setText(commenttimestamp[position]);
            holder.COMMENT.setText(comment[position]);

            return convertView;
        }
    }

