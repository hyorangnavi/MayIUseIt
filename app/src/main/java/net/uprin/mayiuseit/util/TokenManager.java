package net.uprin.mayiuseit.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import net.uprin.mayiuseit.model.AccessToken;
import net.uprin.mayiuseit.model.TokenData;

/**
 * Created by uPrin on 2017. 12. 1..
 */

public class TokenManager {


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;
    private JWT jwt;

    private TokenManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new TokenManager(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(AccessToken token){
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putString("REFRESH_TOKEN", token.getRefreshToken()).commit();
    }

    public void deleteToken(){
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();
    }


    public void deleteAccessToken(){
        editor.remove("ACCESS_TOKEN").commit();
        editor.putString("ACCESS_TOKEN", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MTI0OTYxODEsImp0aSI6IjBieGM0UHpCQTlDYjVqTjE3T0x0SUt3Y1duMnpHYWdRSHBOcXI1cExzc0k9IiwiaXNzIjoiZGV2LnVwcmluLm5ldC9tYXlpdXNlaXQiLCJuYmYiOjExMTI0OTYxODEsImV4cCI6MTExMjQ5OTkwNSwic3ViIjoiQUNDRVNTX1RPS0VOIiwibWVtYmVyX3NybCI6IjExMiIsImVtYWlsX2FkZHJlc3MiOiI1MDMxMDlAbmF2ZXIuY29tIiwibmlja25hbWUiOiLslYjtmqjsm5AiLCJpc0FkbWluIjoiWSJ9.ZP0az47q0_jXLiO4nAIj6K6LYdORmh1KlK-mAUhhJGc").commit();
    }
//    public Boolean isExpired(){
//        jwt = new JWT(prefs.getString("ACCESS_TOKEN", null));
//        return jwt.isExpired(10);
//    }
//
//    public int getMemberSrl(){
//        jwt = new JWT(prefs.getString("ACCESS_TOKEN", null));
//        return jwt.getClaim("member_srl").asInt();
//    }
//
//    public int getEmailAddress(){
//        jwt = new JWT(prefs.getString("ACCESS_TOKEN", null));
//        return jwt.getClaim("member_srl").asInt();
//    }

    public boolean isAdmin(){
        jwt = new JWT(prefs.getString("ACCESS_TOKEN", null));
        if (jwt.getClaim("isadmin").asString().equals("Y")){
        return true;
        } else {
            return false;
        }
    }

    public TokenData getTokenData(){
        TokenData tokenData = new TokenData();
        jwt = new JWT(prefs.getString("ACCESS_TOKEN", null));
        Log.e("tag=tokenTag",prefs.getString("ACCESS_TOKEN", null));
        tokenData.setEmail_address(jwt.getClaim("email_address").asString());
        tokenData.setIsAdmin(jwt.getClaim("isAdmin").asString());
        tokenData.setMember_srl(jwt.getClaim("member_srl").asString());
        tokenData.setNickname(jwt.getClaim("nickname").asString());

        return tokenData;
    }

    public AccessToken getToken(){
        AccessToken token = new AccessToken();
        token.setAccessToken(prefs.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(prefs.getString("REFRESH_TOKEN", null));
        return token;
    }
}
