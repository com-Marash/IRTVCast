package marash.com.irtvcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maedeh on 2018-01-26.
 */

abstract class Channels {
    public static final List<ChannelDetail> channelsInfo = new ArrayList<>();
    static {
        channelsInfo.add(new ChannelDetail("channel-1", "http://mysisli.com/36bay2/gin/gin-irib1/tracks-v1a1/mono.m3u8", R.drawable.channel1));
        channelsInfo.add(new ChannelDetail("channel-2", "http://mysisli.com/36bay2/gin/gin-irib2/tracks-v1a1/mono.m3u8", R.drawable.channel2));
        channelsInfo.add(new ChannelDetail("channel-3", "http://mysisli.com/36bay2/gin/gin-irib3/tracks-v1a1/mono.m3u8", R.drawable.channel3));
        channelsInfo.add(new ChannelDetail("channel-4", "http://mysisli.com/36bay2/gin/gin-irib4/tracks-v1a1/mono.m3u8", R.drawable.channel4));
        channelsInfo.add(new ChannelDetail("channel-5", "http://mysisli.com/36bay2/gin/gin-irib5/tracks-v1a1/mono.m3u8", R.drawable.channel5));
        channelsInfo.add(new ChannelDetail("channel-6", "http://mysisli.com/36bay2/gin/gin-irib6/tracks-v1a1/mono.m3u8", R.drawable.channelnews));
        channelsInfo.add(new ChannelDetail("IFilm", "https://live.cdn.asset.aparat.com/astv1/edge/ifilm_high/index.m3u8", R.drawable.channelifilm));
        channelsInfo.add(new ChannelDetail("Namayesh", "https://live.cdn.asset.aparat.com/astv1/edge/namayesh_high/index.m3u8", R.drawable.channelnamayesh));
        channelsInfo.add(new ChannelDetail("Varzesh", "https://live.cdn.asset.aparat.com/astv1/edge/varzesh_high/index.m3u8", R.drawable.channelvarzesh));
        channelsInfo.add(new ChannelDetail("Nasim", "http://mysisli.com/36bay2/gin/gin-iribnasim/tracks-v1a1/mono.m3u8", R.drawable.channelnasim));
        channelsInfo.add(new ChannelDetail("Tamasha", "https://live.cdn.asset.aparat.com/astv1/edge/hd_hd/index.m3u8", R.drawable.channeltamasha));
        channelsInfo.add(new ChannelDetail("Manoto", "https://dzll8dnb13thz.cloudfront.net/live_1500.m3u8", R.drawable.channelmanoto));
    }
}
