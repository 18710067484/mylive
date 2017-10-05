package com.jiyun.mylive.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jiyun.mylive.R;
import com.jiyun.mylive.adapter.MyAdapter;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ZhuboActivity extends FragmentActivity implements View.OnClickListener {
    @InjectView(R.id.zhubo_video)
    TXCloudVideoView zhuboVideo;
    @InjectView(R.id.lianmai)
    Button lianmai;
    @InjectView(R.id.blianmai)
    Button blianmai;
    @InjectView(R.id.ying)
    Button zhubo_ying;
    @InjectView(R.id.tuiliu)
    Button tuiliu;
    @InjectView(R.id.laliu)
    Button laliu;
    @InjectView(R.id.btui)
    Button btui;
    @InjectView(R.id.bla)
    Button bla;
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    @InjectView(R.id.et_msg)
    EditText etMsg;
    @InjectView(R.id.guan_video)
    TXCloudVideoView guanVideo;
    @InjectView(R.id.btn_send)
    Button btnSend;
    @InjectView(R.id.tv_listView)
    ListView tvListView;
    private TXLivePusher pusher;
    private TXLivePushConfig config;
    String tuiUrl = "rtmp://3891.livepush.myqcloud.com/live/3891_test001_A?bizid=3891&txSecret=70f6e3c168c95838cc1113410630f572&txTime=5C2A3CFF";
    String laurl = "rtmp://3891.liveplay.myqcloud.com/live/3891_test001_A?bizid=3891&txSecret=70f6e3c168c95838cc1113410630f572&txTime=5C2A3CFF";
    String btuiurl = "rtmp://3891.livepush.myqcloud.com/live/3891_test001_B?bizid=3891&txSecret=c955e184a0aac1ba071d1980e7a42683&txTime=5C2A3CFF";
    String blaliu = "rtmp://3891.liveplay.myqcloud.com/live/3891_test001_B?bizid=3891&txSecret=c955e184a0aac1ba071d1980e7a42683&txTime=5C2A3CFF";
    private TXLivePlayer player;
    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhubo);
        ButterKnife.inject(this);
        initView();

        initData();
    }

    private void initData() {

    }

    private void initView() {
        //开启直播
        pusher = new TXLivePusher(this);
        config = new TXLivePushConfig();
        player = new TXLivePlayer(this);

        zhubo_ying.setOnClickListener(this);
        tuiliu.setOnClickListener(this);
        laliu.setOnClickListener(this);
        guanVideo.setOnClickListener(this);
        btui.setOnClickListener(this);
        bla.setOnClickListener(this);
        lianmai.setOnClickListener(this);
        blianmai.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        tvListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (int x = 0; x < messages.size(); x++) {
                    list.add(messages.get(x).toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ying:
                config.setHardwareAcceleration(0);
                break;
            case R.id.tuiliu:
                pusher.setConfig(config);
                pusher.startPusher(tuiUrl);
                pusher.startCameraPreview(zhuboVideo);
                break;
            case R.id.laliu:
                player.setPlayerView(zhuboVideo);
                player.startPlay(laurl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                break;
            case R.id.btui:
                pusher.setConfig(config);
                pusher.startPusher(btuiurl);
                pusher.startCameraPreview(zhuboVideo);
                break;
            case R.id.bla:
                player.setPlayerView(zhuboVideo);
                player.startPlay(blaliu, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                break;
            case R.id.lianmai:
                player.setPlayerView(guanVideo);
                player.startPlay(blaliu, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                break;
            case R.id.blianmai:
                player.setPlayerView(guanVideo);
                player.startPlay(laurl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
                break;
            case R.id.btn_send:
                sendMsg();
                etMsg.setText("");
                break;
        }
    }

    private void sendMsg() {
        String Msg = etMsg.getText().toString();
        EMMessage message = EMMessage.createTxtSendMessage(Msg, "666");
        EMClient.getInstance().chatManager().sendMessage(message);
        list.add(Msg);
        adapter = new MyAdapter(list, getApplicationContext());
        tvListView.setAdapter(adapter);
    }
}
