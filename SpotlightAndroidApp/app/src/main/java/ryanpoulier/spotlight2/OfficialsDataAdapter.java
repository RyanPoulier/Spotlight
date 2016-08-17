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
public class OfficialsDataAdapter extends ArrayAdapter<String> {
    //List mylist = new ArrayList();

    String[] officialsnames = {};
    String[] officialsposition = {};
    //String[] officialsdepartment = {};
    Context c;
    LayoutInflater inflater;

    public OfficialsDataAdapter(Context context, String[] officialsnames,String[] officialsposition) {
        super(context, R.layout.officials_suggestions_list_row, officialsnames);

        this.c = context;
        this.officialsnames= officialsnames;
        this.officialsposition = officialsposition;
        //this.officialsdepartment = officialsdepartment;
    }

    //View holder class
    public class ViewHolder {
        TextView OFFICIALSNAME,POSITION,DEPARTMENT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.officials_suggestions_list_row, null);
        }

        //Viewholder object
        final ViewHolder holder = new ViewHolder();

        //initializing the view
        holder.OFFICIALSNAME = (TextView) convertView.findViewById(R.id.txt_officialsname);
        holder.POSITION = (TextView) convertView.findViewById(R.id.txt_officials_position);
        //holder.DEPARTMENT= (TextView) convertView.findViewById(R.id.txt_Department);

        // assigning data to views
        holder.OFFICIALSNAME.setText(officialsnames[position]);
        holder.POSITION.setText(officialsposition[position]);
        //holder.DEPARTMENT.setText(officialsdepartment[position]);

        return convertView;

    }
}
