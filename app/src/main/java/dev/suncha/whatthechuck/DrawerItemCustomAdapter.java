package dev.suncha.whatthechuck;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by MSI on 1/20/2017.
 */

public class DrawerItemCustomAdapter extends ArrayAdapter<CategoryModel> {

    Context mContext;
    int layoutResourceId;
    CategoryModel categoryModel[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, CategoryModel[] categoryModel) {
        super(mContext, layoutResourceId, categoryModel);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.categoryModel = categoryModel;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        TextView categoryName = (TextView) listItem.findViewById(R.id.categoryName);
        CategoryModel category = categoryModel[position];
        categoryName.setText(category.categoryName);

        return listItem;
    }
}
