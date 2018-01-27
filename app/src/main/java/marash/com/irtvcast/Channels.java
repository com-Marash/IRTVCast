package marash.com.irtvcast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maedeh on 2018-01-26.
 */

abstract class Channels {
    public static final List<ChannelDetail> channelsInfo = new ArrayList<>();
    static {
        channelsInfo.add(new ChannelDetail("channel-1", "https://live.cdn.asset.aparat.com/astv1/edge/tv1_high/index.m3u8"));
        channelsInfo.add(new ChannelDetail("channel-2", "https://live.cdn.asset.aparat.com/astv1/edge/tv2_high/index.m3u8"));
        channelsInfo.add(new ChannelDetail("channel-3", "https://live.cdn.asset.aparat.com/astv1/edge/tv3_high/index.m3u8"));
        channelsInfo.add(new ChannelDetail("channel-4", "https://live.cdn.asset.aparat.com/astv1/edge/tv4_high/index.m3u8"));
        channelsInfo.add(new ChannelDetail("channel-5", "https://live.cdn.asset.aparat.com/astv1/edge/tv5_high/index.m3u8"));
        channelsInfo.add(new ChannelDetail("channel-6", "https://live.cdn.asset.aparat.com/astv1/edge/irinn.m3u8"));

    }
}
