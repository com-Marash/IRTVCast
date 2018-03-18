package marash.com.irtvcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maedeh on 2018-01-26.
 */

abstract class Channels {
    public static final List<ChannelDetail> channelsInfo = new ArrayList<>();
    static {
        channelsInfo.add(new ChannelDetail("channel-1", "http://nimlive3.giniko.com/irib1/irib1/chunklist_w369122987.m3u8", R.drawable.channel1));
        channelsInfo.add(new ChannelDetail("channel-2", "http://nimlive3.giniko.com/irib2/irib2/chunklist_w467988229.m3u8", R.drawable.channel2));
        channelsInfo.add(new ChannelDetail("channel-3", "https://live.cdn.asset.aparat.com/astv1/edge/tv3_high/index.m3u8", R.drawable.channel3));
        channelsInfo.add(new ChannelDetail("channel-4", "https://live.cdn.asset.aparat.com/astv1/edge/tv4_high/index.m3u8", R.drawable.channel4));
        channelsInfo.add(new ChannelDetail("channel-5", "https://live.cdn.asset.aparat.com/astv1/edge/tv5_high/index.m3u8", R.drawable.channel5));
        channelsInfo.add(new ChannelDetail("channel-6", "https://live.cdn.asset.aparat.com/astv1/edge/irinn.m3u8", R.drawable.channelnews));
        channelsInfo.add(new ChannelDetail("IFilm", "https://live.cdn.asset.aparat.com/astv1/edge/ifilm_high/index.m3u8", R.drawable.channelifilm));
        channelsInfo.add(new ChannelDetail("Namayesh", "https://live.cdn.asset.aparat.com/astv1/edge/namayesh_high/index.m3u8", R.drawable.channelnamayesh));
        channelsInfo.add(new ChannelDetail("Varzesh", "https://live.cdn.asset.aparat.com/astv1/edge/varzesh_high/index.m3u8", R.drawable.channelvarzesh));
        channelsInfo.add(new ChannelDetail("Nasim", "https://live.cdn.asset.aparat.com/astv1/edge/nasim_high/index.m3u8", R.drawable.channelnasim));
        channelsInfo.add(new ChannelDetail("Tamasha", "https://live.cdn.asset.aparat.com/astv1/edge/hd_hd/index.m3u8", R.drawable.channeltamasha));
        channelsInfo.add(new ChannelDetail("Manoto", "https://s3.eu-west-2.amazonaws.com/manos-live-001/profile2/out_0.m3u8", R.drawable.channelmanoto));
    }
}
