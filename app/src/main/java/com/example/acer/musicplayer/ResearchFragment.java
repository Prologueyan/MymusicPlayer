package com.example.acer.musicplayer;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.utils.Bean;
import com.example.acer.utils.MyAdpter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ResearchFragment extends Fragment {

    private List<Map<String, String>> musicList = new ArrayList<Map<String, String>>();
    private int size = 0;
    private List<Bean> songs = new List<Bean>() {
        @Override
        public void add(int location, Bean object) {

        }

        @Override
        public boolean add(Bean object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends Bean> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Bean> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Bean get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<Bean> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<Bean> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Bean> listIterator(int location) {
            return null;
        }

        @Override
        public Bean remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Bean set(int location, Bean object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public List<Bean> subList(int start, int end) {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        musicList = loadSongs();
        View view = inflater.inflate(R.layout.research, container, false);
        ListView content = (ListView) view.findViewById(R.id.listView);
        content.setAdapter(new MyAdpter(getActivity(), songs));
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
        return view;
    }

    public List<Map<String, String>> loadSongs() {
        // 查找sdcard卡上的所有歌曲信息(注意自己加上内部存储的音乐）
        // 加入封装音乐信息的代码
        // 查询所有歌曲
        ContentResolver musicResolver = getActivity().getContentResolver();
        Cursor musicCursor = musicResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,  //external_content_uri
                MediaStore.Audio.Media.SIZE + ">80000", null, null);
        int musicColumnIndex;
        int musicTime;
        String musicPath;
        String musicName;
        String musicAlbum;
        String musicArtist;
        String musicAlbumKey;
        String musicAlbumArtPath;
        if (null != musicCursor && musicCursor.getCount() > 0) {
            for (musicCursor.moveToFirst(); !musicCursor.isAfterLast(); musicCursor
                    .moveToNext()) {
                Map<String, String> musicDataMap = new HashMap<String, String>();
                Bean thissong = new Bean();
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns._ID);
                int musicRating = musicCursor.getInt(musicColumnIndex);
                musicDataMap.put("musicRating", musicRating + "");
                musicDataMap.put("id", size + "");
                size = size + 1;
                // 取得音乐播放路径
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                musicPath = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicPath", musicPath);
                // 取得音乐的名字
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
                musicName = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicName", musicName);
                // 取得音乐的专辑名称
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
                musicAlbum = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicAlbum", musicAlbum);
                // 取得音乐的演唱者
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                musicArtist = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("musicArtist", musicArtist);
                // 取得歌曲对应的专辑对应的Key
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
                musicAlbumKey = musicCursor.getString(musicColumnIndex);
                // 取得歌曲的大小
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
                musicTime = musicCursor.getInt(musicColumnIndex);

                //
                // Time musicTime = new Time();
                // musicTime.set(musicTime);
                String readableTime = ":";
                int m = musicTime % 60000 / 1000;
                int o = musicTime / 60000;
                if (o == 0) {
                    readableTime = "00" + readableTime;
                } else if (0 < o && o < 10) {
                    readableTime = "0" + o + readableTime;
                } else {
                    readableTime = o + readableTime;
                }
                if (m < 10) {
                    readableTime = readableTime + "0" + m;
                } else {
                    readableTime = readableTime + m;
                }
                musicDataMap.put("musicTime", readableTime);
                //
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                String path = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("path", path);

                //
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                String display = musicCursor.getString(musicColumnIndex);
                musicDataMap.put("displayName", display);

                String[] argArr = {musicAlbumKey};
                ContentResolver albumResolver = getActivity().getContentResolver();
                Cursor albumCursor = albumResolver.query(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                        MediaStore.Audio.AudioColumns.ALBUM_KEY + " = ?",
                        argArr, null);
                if (null != albumCursor && albumCursor.getCount() > 0) {
                    albumCursor.moveToFirst();
                    int albumArtIndex = albumCursor
                            .getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
                    musicAlbumArtPath = albumCursor.getString(albumArtIndex);
                    if (null != musicAlbumArtPath
                            && !"".equals(musicAlbumArtPath)) {
                        musicDataMap.put("musicAlbumImage", musicAlbumArtPath);
                    } else {
                        musicDataMap.put("musicAlbumImage",
                                "");
                    }
                } else {
                    // 没有专辑定义，给默认图片
                    musicDataMap.put("musicAlbumImage",
                            "");
                }
                musicList.add(musicDataMap);
                thissong.setSongName(musicDataMap.get(musicName));
                thissong.setArtist(musicDataMap.get(musicArtist));
                songs.add(thissong);
            }
        }


        //获取内部音乐文件
        ContentResolver musicResolver2 = getActivity().getContentResolver();
        Cursor musicCursor2 = musicResolver2.query(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null,  //ternal_content_uri
                MediaStore.Audio.Media.SIZE + ">80000", null, null);
        int musicColumnIndex2;
        if (null != musicCursor2 && musicCursor2.getCount() > 0) {
            for (musicCursor2.moveToFirst(); !musicCursor2.isAfterLast(); musicCursor2
                    .moveToNext()) {
                Map<String, String> musicDataMap = new HashMap<String, String>();
                Bean thissong = new Bean();

                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns._ID);
                int musicRating = musicCursor2.getInt(musicColumnIndex2);
                musicDataMap.put("musicRating", musicRating + "");
                musicDataMap.put("id", size + "");
                size = size + 1;
                // 取得音乐播放路径
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                musicPath = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("musicPath", musicPath);
                // 取得音乐的名字
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
                musicName = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("musicName", musicName);
                // 取得音乐的专辑名称
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
                musicAlbum = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("musicAlbum", musicAlbum);
                // 取得音乐的演唱者
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                musicArtist = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("musicArtist", musicArtist);
                // 取得歌曲对应的专辑对应的Key
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
                musicAlbumKey = musicCursor2.getString(musicColumnIndex2);
                // 取得歌曲的大小
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
                musicTime = musicCursor2.getInt(musicColumnIndex2);

                //
                // Time musicTime = new Time();
                // musicTime.set(musicTime);
                String readableTime = ":";
                int m = musicTime % 60000 / 1000;
                int o = musicTime / 60000;
                if (o == 0) {
                    readableTime = "00" + readableTime;
                } else if (0 < o && o < 10) {
                    readableTime = "0" + o + readableTime;
                } else {
                    readableTime = o + readableTime;
                }
                if (m < 10) {
                    readableTime = readableTime + "0" + m;
                } else {
                    readableTime = readableTime + m;
                }
                musicDataMap.put("musicTime", readableTime);
                //
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                String path = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("path", path);

                //
                musicColumnIndex2 = musicCursor2
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                String display = musicCursor2.getString(musicColumnIndex2);
                musicDataMap.put("displayName", display);

                String[] argArr = {musicAlbumKey};
                ContentResolver albumResolver2 = getActivity().getContentResolver();
                Cursor albumCursor2 = albumResolver2.query(
                        MediaStore.Audio.Albums.INTERNAL_CONTENT_URI, null,
                        MediaStore.Audio.AudioColumns.ALBUM_KEY + " = ?",
                        argArr, null);
                if (null != albumCursor2 && albumCursor2.getCount() > 0) {
                    albumCursor2.moveToFirst();
                    int albumArtIndex = albumCursor2
                            .getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
                    musicAlbumArtPath = albumCursor2.getString(albumArtIndex);
                    if (null != musicAlbumArtPath
                            && !"".equals(musicAlbumArtPath)) {
                        musicDataMap.put("musicAlbumImage", musicAlbumArtPath);
                    } else {
                        musicDataMap.put("musicAlbumImage",
                                "");
                    }
                } else {
                    // 没有专辑定义，给默认图片
                    musicDataMap.put("musicAlbumImage",
                            "");
                }
                musicList.add(musicDataMap);
                thissong.setSongName(musicDataMap.get(musicName));
                thissong.setArtist(musicDataMap.get(musicArtist));
                songs.add(thissong);
            }
        }


        return musicList;

    }
}