package com.xl.exdiary.model.impl;


import android.os.Environment;

import com.xl.exdiary.model.inter.IUserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class UserModel implements IUserModel {
    @Override
    public boolean saveUserInfoInLocal(JSONObject userInfo) {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"ExDiary/");
            if(!file.exists())
                file.mkdir();
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(),"ExDiary/userInfo.json")));
            bw.write(userInfo.toString());
            bw.flush();
            bw.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveUserInfoOnServer(JSONObject userInfo) {
        return false;
    }

    @Override
    public JSONObject getUserInfo() {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"ExDiary/");
            if(!file.exists())
                file.mkdir();
            StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(
                    new File(Environment.getExternalStorageDirectory(),"ExDiary/userInfo.json")));
            String s = null;
            while ((s = br.readLine()) != null)
                result.append(System.lineSeparator() + s);
            br.close();
            return new JSONObject(result.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveFriendInLocal(JSONObject friend) {
        JSONArray friends=getAllFriend();
        int flag=0;
        try{
            for(int i=0;i<friends.length();i++)
                if(friends.getJSONObject(i).get("uuid").equals(friend.get("uuid"))) {
                    friends.remove(i);
                    friends.put(friend);
                    flag=1;
                }
            if(flag==0)
                friends.put(friend);
            File file=new File(Environment.getExternalStorageDirectory(),"ExDiary/");
            if(!file.exists())
                file.mkdir();
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(),"ExDiary/friends.json")));
            bw.write(friends.toString());
            bw.flush();
            bw.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delFriendInLocal(JSONObject friend) {
        JSONArray friends=getAllFriend();
        try{
            for(int i=0;i<friends.length();i++)
                if(friends.getJSONObject(i).get("uuid").equals(friend.get("uuid"))) {
                    friends.remove(i);
                    break;
                }
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(),"ExDiary/friends.json")));
            bw.write(friends.toString());
            bw.flush();
            bw.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public JSONArray getAllFriend() {
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"ExDiary/");
            if(!file.exists())
                file.mkdir();
            StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(
                    new File(Environment.getExternalStorageDirectory(),"ExDiary/friends.json")));
            String s = null;
            while ((s = br.readLine()) != null)
                result.append(System.lineSeparator() + s);
            br.close();
            return new JSONArray(result.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONArray();
    }

    @Override
    public boolean delFriendOnServer(String myUUID,String friendUUID) {
        return false;
    }

    @Override
    public boolean addFriendOnServer(String myUUID,String friendUUID) {

        return false;
    }

}
