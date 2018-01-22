package marash.com.irtvcast;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.gms.cast.framework.CastContext;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mDialog;
    VideoView videoView;
    ImageView btnPlayPause;
    GridView channelGridView;
    String[] channelsName = new String[]{"channel-1", "channel-2", "channel-3", "channel-4", "channel-5"};
    String[] channelsURL = new String[]{"https://live.cdn.asset.aparat.com/astv1/edge/tv1_high/index.m3u8?wmsAuthSign=67098b403d13f1475de97ecb85cb115a",
            "https://live.cdn.asset.aparat.com/astv1/edge/tv2_high/index.m3u8?wmsAuthSign=b77027a217718cc7dc6c60b3093cbc7b",
            "https://live.cdn.asset.aparat.com/astv1/edge/tv3_high/index.m3u8?wmsAuthSign=cde6435c79c428b3c6decdcde5f89525"};
    String videoURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CastContext castContext = CastContext.getSharedInstance(this);

        videoView = findViewById(R.id.videoView);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnPlayPause.setClickable(false);
        mDialog = new ProgressDialog(MainActivity.this);

        channelGridView = findViewById(R.id.channelGridView);
        ChannelAdapter channelAdapter = new ChannelAdapter(getApplicationContext());
        channelGridView.setAdapter(channelAdapter);

        channelGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (channelsName[position].equals("channel-1") || channelsName[position].equals("channel-2")) {

                    mDialog.setMessage("Please wait...");
                    mDialog.setCanceledOnTouchOutside(true);
                    mDialog.show();

                    try {
                        videoURL = channelsURL[position];
                        Uri uri = Uri.parse(videoURL);
                        videoView.setVideoURI(uri);
                        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                btnPlayPause.setImageResource(R.drawable.play);
                            }
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    videoView.requestFocus();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mDialog.dismiss();
                            mediaPlayer.setLooping(true);
                            videoView.start();
                            btnPlayPause.setClickable(true);
                            btnPlayPause.setImageResource(R.drawable.pause);
                        }
                    });


                } else {
                    //TODO show message that tyou have buy pro to see these channels!
                    mDialog.setMessage("Please buy version pro to have access to all channels.");
                    mDialog.setCanceledOnTouchOutside(true);
                    mDialog.show();
                }
            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnPlayPause.isClickable()) {
                    if (!videoView.isPlaying()) {
                        mDialog = new ProgressDialog(MainActivity.this);
                        mDialog.setMessage("Please wait...");
                        mDialog.setCanceledOnTouchOutside(true);
                        mDialog.show();

                        Uri uri = Uri.parse(videoURL);
                        videoView.setVideoURI(uri);
                        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                btnPlayPause.setImageResource(R.drawable.play);
                            }
                        });
                    } else {
                        videoView.pause();
                        btnPlayPause.setImageResource(R.drawable.play);
                    }
                } else {


                    videoView.requestFocus();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mDialog.dismiss();
                            mediaPlayer.setLooping(true);
                            videoView.start();
                            btnPlayPause.setImageResource(R.drawable.pause);
                        }
                    });
                }
            }
        });
    }
}
