package com.example.lg.searchblog;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AsyncTask1 extends AsyncTask<String, Void, String> {

    String apiURL = "";
    String response = "";
    String strCookie = "";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String ... strings) {
        try {
            String text = URLEncoder.encode(strings[2], "UTF-8");//strings[2]==한글검색어

            apiURL = "https://openapi.naver.com/v1/search/blog.json?query=" + text+"&display="+strings[3]+"&start="+strings[4];//strings[3]==displayNum,strings[4]==startNum

            URL url = new URL(apiURL);

            //Log.v("url값",String.valueOf(url));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", strings[0]);//strings[0] == client-id
            conn.setRequestProperty("X-Naver-Client-Secret", strings[1]);//strings[1]==client-secret
            conn.setDoInput(true); // 읽기모드 지정
            conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
            conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

            strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

            BufferedReader reader;
            StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체

            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); //문자열 셋 세팅
            }else{
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;//한줄씩 읽어오는걸 임시 저장

            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            response = builder.toString(); //최종 string
            //Log.v("글복사",response);

        } catch (Exception e) {
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
    }
}
