package com.ljfxyj2008.CustomLint;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/10/28.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * Demo Activity with multiple errors that should be scanned by custom lint rules.
 */
public class MainActivity extends AppCompatActivity {
    private HashMap<Integer, Integer> myMap = new HashMap<>();


    private String[][] data = new String[][]{
            {"book", "$23"},
            {"light", "$12"}
    };

    private static final int aa = 2;
    private final int abcHaha = 1;
    private static final String bbs = "adf";
    private RecyclerView rv;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private LayoutInflater mInflater;
        private Context mCotext;
        private String[][] mData;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            myMap.put(1, 1);
            View aaa = mInflater.inflate(R.layout.ite23m_goodsinfo, parent, false);
            return new ViewHolder(mInflater.inflate(R.layout.ite23m_goodsinfo, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvName.setText(mData[position][0]);
            holder.tvPrice.setText(mData[position][1]);
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvName;
            TextView tvPrice;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName = (TextView)itemView.findViewById(R.id.tv_goodsname);
                tvPrice = (TextView)itemView.findViewById(R.id.tv_goodsprice);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_main);
        doInit();

        rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        BlankFragment blankFragment = new BlankFragment();
        Log.d("adf", "adsf" + aa);
        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();


        for(int i=1; i<99; i++){
            for(int j=1; j<99; j++){
                if (1 > 2) {
                    for(int k=1; k<99; k++){
                        try {
                            for(int m=1; m<99; m++){

                            }
                            final int aaa = 1;
                            //aaa++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (1 > 2){

        }else if (3>4){

        }else if (5>6){

        }else{
            if (7>8){

            }
        }



        /*if (abc == 1
                || bbs.equals("adf")
                && 24>23){
            String.format("%d",23);



            if (abc > 1){
               if (abc >= 1){
                   int a = 1;
                   a = 2;
               }
               long curr = System.currentTimeMillis();
           }

            String babab = "发财啊";
        } else if (abc == 999){
            int a = 999;
            a = 999 + 999;
        } else{


            if (abc == 1
                    || bbs.equals("adf")
                    && 24>23){


                if (abc > 1){
                    if (abc >= 1){
                        int a = 1;
                        a = 2;
                    }

                }


            }
        }*/


        new Message();
        Message.obtain();
        handler.obtainMessage();
        handler.sendEmptyMessage(1);

    }

    public void doInit(){


        try {
            Integer.parseInt("123");
            try {
                String.format("%d", 23);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    String.format("%d", 23);

                    if ( 1 > 2){
                        try {
                            String.format("%d", 123345);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            throw new RuntimeException("my custom exception");
        }


    }


}
