package com.nexuslink.govermanage.util;

import android.util.Log;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

public abstract class MyOKutilCallback extends Callback<String> {
        @Override
        public boolean validateReponse(Response response, int id) {
            return true;
        }

        @Override
        public String parseNetworkResponse(Response response, int id) throws Exception {

            if(response.code()>=200 && response.code()<300){
                return response.body().string();
            }else{
                throw new Exception("code is:"+response.code()+"\n"+response.body().string());
            }

        }



}
