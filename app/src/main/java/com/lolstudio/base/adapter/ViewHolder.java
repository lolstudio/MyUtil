package com.lolstudio.base.adapter;

import android.util.SparseArray;
import android.view.View;

/**
@Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                // init convertView by layout
                convertView = LayoutInflater.from(context.inflate(R.layout.item, parent, false));
            }
            ImageView imageView = ViewHolder.get(convertView, R.id.imageView_id);
            imageView.setImageResource(R.drawable.ic_launcher);
            
            return convertView;
        }
 *
 */
public class ViewHolder {
	 // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
