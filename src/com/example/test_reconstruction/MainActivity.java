package com.example.test_reconstruction;
 
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.test_reconstruction.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener,Runnable{
     
    private static final String URL = "http://192.168.11.2/test3.php";
    public String str = null;
    //private TextView ed1 = null;
    
    int [] severID  = null;
    int [] share = null;
    int secnum;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
	    
         
        Button button = (Button)findViewById(R.id.button);
        
        button.setOnClickListener(this);   
    }
 
    @Override
    public void onClick(View v) {  
    	str="null";
    	try{
	        Thread thread = new Thread(this);
	        thread.start();
	      //�����I���܂őҋ@
	        
	        thread.join();
	        str="secret num:"+str;
	        Toast.makeText(getApplication(), str, Toast.LENGTH_LONG).show();
    	}catch(InterruptedException e){
    	
    	}
    }
 
    @Override
    public void run() {
        try{
        	severID = new int[10];
        	share = new int[10];
            HttpUriRequest httpGet = new HttpGet(URL); 
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            //Log.d("aaaa",EntityUtils.toString(entity));
            //JSONObject object = new JSONObject(EntityUtils.toString(entity));
            JSONArray json=null;
            json = new JSONArray(EntityUtils.toString(entity));
            
            
            for(int i = 0; i < json.length();i++){
                JSONObject book = json.getJSONObject(i);
                 
                severID[i] = Integer.parseInt(book.getString("severID"));
                share[i] = Integer.parseInt(book.getString("share"));
                
                 
                /* �m�F�p
                Log.d("iiii","convertJSONArray : Array");     
                Log.d("iiii", "convertJSONArray : severID["+i+"]:" + severID[i]);
                Log.d("iiii", "convertJSONArray : share["+i+"]:" + share[i]);  
                */              
            }     
            
            secnum = xknfunc(severID[0],share[0],severID[1],share[1]);
            str=String.valueOf(secnum);
            Log.d("iiii","secnum : " + str);
            
        }catch(IOException e){
            System.out.println("�ʐM���s");
        }catch(JSONException e){
            System.out.println("JSON�擾�Ɏ��s");
        }  
        
    }
    
    
    public int xknfunc(int x1,int d1,int x2,int d2){
      	int scrt;	//�閧���
      	int a1 = (d1-d2)/(x1-x2) ;
      	scrt = d1 - a1*x1 ;
      	return scrt;
      }
    
}