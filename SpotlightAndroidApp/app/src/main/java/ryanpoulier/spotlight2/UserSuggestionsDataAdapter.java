package ryanpoulier.spotlight2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 24/02/2016.
 */
public class UserSuggestionsDataAdapter extends ArrayAdapter<String> {
    //List mylist = new ArrayList();

    String[] username = {};
    String[] reputation = {};
    int[] images = {};
    Context c;
    LayoutInflater inflater;

    public UserSuggestionsDataAdapter(Context context, String[] username,String[] reputation, int[] images) {
        super(context, R.layout.user_suggestions_list_row, username);

        this.c = context;
        this.username = username;
        this.reputation = reputation;
        this.images = images;
    }

    //View holder class
    public class ViewHolder {
        TextView USERNAME,NUMBER,REPUTATION;
        ImageView USERIMAGE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_suggestions_list_row, null);
        }

        //Viewholder object
        final ViewHolder holder = new ViewHolder();

        //initializing the view
        holder.USERNAME = (TextView) convertView.findViewById(R.id.txt_username);
        holder.USERIMAGE = (ImageView) convertView.findViewById(R.id.imageViewUser);
        holder.REPUTATION= (TextView) convertView.findViewById(R.id.txtUserReputation);
        holder.NUMBER= (TextView) convertView.findViewById(R.id.txtUserNumber);

        // assigning data to views
        holder.USERIMAGE.setImageResource(images[position]);
        holder.USERNAME.setText(username[position]);
        holder.REPUTATION.setText(reputation[position]);
        holder.NUMBER.setText(String.valueOf(position+1));

        return convertView;

    }
}
