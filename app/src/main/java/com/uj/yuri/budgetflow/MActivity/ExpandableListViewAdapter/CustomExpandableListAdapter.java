package com.uj.yuri.budgetflow.MActivity.ExpandableListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.Utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuri on 2016-01-17.
 */public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private TextView amount;
    private TextView name_of;
    private TextView category;
    private TextView time_hours;

    private ImageView circle_im_cat;
    private ImageView note_img;
    private Context context;
    private List<Map.Entry<Category, String>> expandableListTitle;
    private HashMap< Map.Entry<Category, String>, List<Outcome>> expandableListDetail;
    //key = e.getKey();
    public CustomExpandableListAdapter(Context context, List<Map.Entry<Category, String>> expandableListTitle,
                                       HashMap< Map.Entry<Category, String>, List<Outcome>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        Iterator<Map.Entry<Map.Entry<Category, String>, List<Outcome>>> ift = expandableListDetail.entrySet().iterator();
        int i = 0;
        while (ift.hasNext()) {

            Map.Entry<Map.Entry<Category, String>, List<Outcome>> e = ift.next();

            List<Outcome> value = e.getValue();
            if ( i == listPosition){
                return value.get(expandedListPosition);
            }
            i++;

        }

        return null;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    private void setLay(View v){
        circle_im_cat = (ImageView) v.findViewById(R.id.circle_im_cat);
        circle_im_cat.setBackground(v.getResources().getDrawable(R.drawable.ic_note));
        amount = (TextView) v.findViewById(R.id.amount);
        name_of = (TextView) v.findViewById(R.id.name_of);
        name_of.setVisibility(View.INVISIBLE);
        time_hours = (TextView) v.findViewById(R.id.time_hours);
        note_img = (ImageView) v.findViewById(R.id.note_img);
        note_img.setVisibility(View.INVISIBLE);
        category = (TextView) v.findViewById(R.id.categorie);
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View v, ViewGroup parent) {
        final Outcome expandedListText = (Outcome) getChild(listPosition, expandedListPosition);
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.itemlists, null);
        }

        setLay(v);

        if(!expandedListText.getName().equals("")) {
            category.setText(expandedListText.getName());
            circle_im_cat.setVisibility(View.VISIBLE);
        } else {
            category.setText("");
            circle_im_cat.setVisibility(View.INVISIBLE);
        }
        time_hours.setText(expandedListText.getStartTime());
        amount.setText(expandedListText.getAmount().toFormattedString());
        return v;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        Iterator<Map.Entry<Map.Entry<Category, String>, List<Outcome>>> ift = expandableListDetail.entrySet().iterator();
        int i = 0;
        while (ift.hasNext()) {
            Map.Entry<Map.Entry<Category, String>, List<Outcome>> e = ift.next();
            List<Outcome> value = e.getValue();
            if ( i == listPosition){
                return value.size();
            }
            i++;

        }
        return 0;

    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Map.Entry<Category, String> listTitle = (Map.Entry<Category, String>) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.circle_of_day);
        TextView saldo = (TextView) convertView.findViewById(R.id.time_hours);
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.text_day_of_week);

        Utility.getColorBall(listTitle.getKey().getColor(), img, this.context);
        listTitleTextView.setText(listTitle.getKey().getName());
        saldo.setText("Saldo: " + listTitle.getValue());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
