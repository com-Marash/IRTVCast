package marash.com.irtvcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Maedeh on 2018-01-12.
 */

public class ChannelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Map<String,Integer> channels = new LinkedHashMap<>();

    ChannelAdapter(Context applicationContext) {
        inflater = (LayoutInflater.from(applicationContext));
        channels.put("channel-1",R.drawable.channel1);
        channels.put("channel-2",R.drawable.channel2);
        channels.put("channel-3",R.drawable.channel3);

    }

    @Override
    public int getCount() {
        return channels.size();
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
        channelImage.setImageResource(new ArrayList<>(channels.values()).get(i));

        TextView channelText = view.findViewById(R.id.imageText);
        channelText.setText(new ArrayList<>(channels.keySet()).get(i));

        return view;
    }
}
