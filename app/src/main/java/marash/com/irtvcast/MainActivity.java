package marash.com.irtvcast;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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

import static marash.com.irtvcast.R.drawable.channelnamayesh;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mDialog;
    VideoView videoView;
    ImageView btnPlayPause;
    GridView channelGridView;

    Integer currentChannelIndex = null;

    // chromecast
    MediaRouteButton mMediaRouteButton;
    private CastSession mCastSession;
    private SessionManager mSessionManager;
    private final SessionManagerListener mSessionManagerListener = new SessionManagerListenerImpl();

    boolean playState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // chromecast session manager should be higher than super.onCreate
        mSessionManager = CastContext.getSharedInstance(this).getSessionManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Chromecast
        mMediaRouteButton = findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);
        CastContext castContext = CastContext.getSharedInstance(this);


        videoView = findViewById(R.id.videoView);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnPlayPause.setEnabled(false);
        mDialog = new ProgressDialog(MainActivity.this);

        channelGridView = findViewById(R.id.channelGridView);
        ChannelAdapter channelAdapter = new ChannelAdapter(getApplicationContext());
        channelGridView.setAdapter(channelAdapter);

        channelGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentChannelIndex = position;
                playState = true;

                if (mSessionManager.getCurrentCastSession() != null) {
                    startCast();

                } else {
                    mDialog.setMessage("Please wait...");
                    mDialog.setCanceledOnTouchOutside(true);
                    mDialog.show();

                    videoView.setVideoURI(Uri.parse(Channels.channelsInfo.get(currentChannelIndex).url));
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mDialog.dismiss();
                            mediaPlayer.setLooping(true);
                            videoView.start();
                            btnPlayPause.setEnabled(true);
                            btnPlayPause.setImageResource(R.drawable.pause2);
                        }
                    });
                }

            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnPlayPause.isEnabled()) {
                    CastSession currentCastSession = mSessionManager.getCurrentCastSession();
                    if (currentCastSession != null) {
                        if (playState) {
                            currentCastSession.getRemoteMediaClient().pause();
                            playState = false;
                            btnPlayPause.setImageResource(R.drawable.play2);
                        } else {
                            startCast();
                        }
                    } else {
                        if (!videoView.isPlaying()) {
                            mDialog.setMessage("Please wait...");
                            mDialog.setCanceledOnTouchOutside(true);
                            mDialog.show();

                            videoView.setVideoURI(Uri.parse(Channels.channelsInfo.get(currentChannelIndex).url));
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mDialog.dismiss();
                                    mediaPlayer.setLooping(true);
                                    videoView.start();
                                    btnPlayPause.setImageResource(R.drawable.pause2);
                                    playState = true;
                                }
                            });
                        } else {
                            videoView.pause();
                            btnPlayPause.setImageResource(R.drawable.play2);
                            playState = false;
                        }
                    }
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
            startCast();
        }

        @Override
        public void onSessionStartFailed(Session session, int i) {
            stopCast();
        }

        @Override
        public void onSessionEnding(Session session) {

        }

        @Override
        public void onSessionResumed(Session session, boolean wasSuspended) {
            invalidateOptionsMenu();
            startCast();
        }

        @Override
        public void onSessionResumeFailed(Session session, int i) {
            stopCast();
        }

        @Override
        public void onSessionSuspended(Session session, int i) {
            stopCast();
        }

        @Override
        public void onSessionEnded(Session session, int error) {
            stopCast();
        }

        @Override
        public void onSessionResuming(Session session, String s) {

        }

    }

    private void startCast() {
        if (currentChannelIndex != null) {
            if (videoView.isPlaying()){
                videoView.pause();
            }
            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
            movieMetadata.putString("mediaType", "video");
            movieMetadata.putString(MediaMetadata.KEY_TITLE, "FARSI CAST | " + Channels.channelsInfo.get(currentChannelIndex).name);

            MediaInfo mediaInfo = new MediaInfo.Builder(Channels.channelsInfo.get(currentChannelIndex).url)
                    .setMetadata(movieMetadata)
                    .setStreamType(MediaInfo.STREAM_TYPE_LIVE)
                    .setContentType("application/x-mpegURL")
                    .build();

            RemoteMediaClient remoteMediaClient = mSessionManager.getCurrentCastSession().getRemoteMediaClient();
            remoteMediaClient.load(mediaInfo, true);

            btnPlayPause.setEnabled(true);
            btnPlayPause.setImageResource(R.drawable.pause2);
            playState = true;
        }
    }

    private void stopCast() {
        playState = false;
        btnPlayPause.setImageResource(R.drawable.play2);
        if (videoView.isPlaying()){
            videoView.pause();
        }
    }
}
