package marash.com.irtvcast;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mDialog;
    VideoView videoView;
    ImageView btnPlayPause;
    GridView channelGridView;
    String[] channelsName = new String[]{"channel-1", "channel-2", "channel-3", "channel-4", "channel-5"};
    String[] channelsURL = new String[]{"https://live.cdn.asset.aparat.com/astv1/edge/tv1_high/index.m3u8",
            "https://live.cdn.asset.aparat.com/astv1/edge/tv2_high/index.m3u8",
            "https://live.cdn.asset.aparat.com/astv1/edge/tv3_high/index.m3u8"};
    String videoURL;

    // chromecast
    MediaRouteButton mMediaRouteButton;
    private CastSession mCastSession;
    private SessionManager mSessionManager;
    private final SessionManagerListener mSessionManagerListener =
            new SessionManagerListenerImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // chromecasr session manager should be higher than super.onCreate
        mSessionManager = CastContext.getSharedInstance(this).getSessionManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Chromecast
        mMediaRouteButton = findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);
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

    @Override
    protected void onResume() {
        mCastSession = mSessionManager.getCurrentCastSession();
        mSessionManager.addSessionManagerListener(mSessionManagerListener);
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSessionManager.removeSessionManagerListener(mSessionManagerListener);
        mCastSession = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    // TODO: notiy user about unexpected situations may happen to the session
    private class SessionManagerListenerImpl implements SessionManagerListener {
        @Override
        public void onSessionStarting(Session session) {

        }

        @Override
        public void onSessionStarted(Session session, String sessionId) {
            invalidateOptionsMenu();
            runTestCast(session, sessionId);
        }

        @Override
        public void onSessionStartFailed(Session session, int i) {

        }

        @Override
        public void onSessionEnding(Session session) {

        }

        @Override
        public void onSessionResumed(Session session, boolean wasSuspended) {
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumeFailed(Session session, int i) {

        }

        @Override
        public void onSessionSuspended(Session session, int i) {
            //TODO: notify user about session suspension
            finish();
        }

        @Override
        public void onSessionEnded(Session session, int error) {
            finish();
        }

        @Override
        public void onSessionResuming(Session session, String s) {

        }

    }

    private void runTestCast(Session session, String sessionId) {
        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        movieMetadata.putString("mediaType", "video");
        movieMetadata.putString(MediaMetadata.KEY_TITLE, "Shabake 1");

        MediaInfo mediaInfo = new MediaInfo.Builder(channelsURL[1])
                .setMetadata(movieMetadata)
                .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
                .setContentType("application/x-mpegURL")
                .build();

        RemoteMediaClient remoteMediaClient = mSessionManager.getCurrentCastSession().getRemoteMediaClient();
        remoteMediaClient.load(mediaInfo, true);
    }
}
