package com.joker.allenmp3.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.adapter.MusicList;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.Music;
import com.joker.allenmp3.service.mediaPlayerService;

import java.util.List;


public class LocalListFragment extends Fragment {
    private List<Music> musicList;
    private ListView lv;
    private static getMuisc getInfo;

    public LocalListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) getView().findViewById(R.id.lv);
        musicList = MyApplication.getInstens().getMusics();
        lv.setAdapter(new MusicList(getActivity(), musicList));
        lv.setOnItemClickListener(new ItemClickListener());
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MyApplication.getInstens().setPosition(i);
            Music music = musicList.get(i);
            Intent intent = new Intent(getActivity(), mediaPlayerService.class);
            intent.putExtra("music", music);
            getActivity().startService(intent);
//            musicName.setText(music.getDisplayName());
//            artist.setText(music.getArtist());
//            state.setImageResource(R.mipmap.play_btn_pause);
//            oja.start();
            getInfo.getMusicInfo(music);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
public static void getMusicInfo(Context context){
        getInfo = (getMuisc) context;
    }
public interface getMuisc{
    public void getMusicInfo(Music music);
 }
}
