package marash.com.irtvcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Maedeh on 2018-01-12.
 */

public class ChannelAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    ChannelAdapter(Context applicationContext) {
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Channels.channelsInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.gridview_item, parent, false); // inflate the layout
        }
        ImageView channelImage = view.findViewById(R.id.CellImage); // get the reference of ImageView
        channelImage.setImageResource(Channels.channelsInfo.get(i).image);

        TextView channelText = view.findViewById(R.id.imageText);
        channelText.setText(Channels.channelsInfo.get(i).name);

        return view;
    }
}
