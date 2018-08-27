package com.my51c.see51.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.my51c.see51.BaseActivity;
import com.my51c.see51.Logger.LoggerSave;
import com.my51c.see51.adapter.MySpinnerAdapter;
import com.my51c.see51.app.OneKeyStarActivity;
import com.my51c.see51.common.AppData;
import com.my51c.see51.widget.NiceDialog;
import com.synertone.commonutil.view.BaseNiceDialog;
import com.synertone.commonutil.view.ViewConvertListener;
import com.synertone.commonutil.view.ViewHolder;
import com.synertone.netAssistant.R;

import org.afinal.simplecache.ACache;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.my51c.see51.app.http.XTHttpUtil.GET_TIAN_STYLE;


/*
 * 高级设置
 */
public class SuperSetActivity extends BaseActivity implements OnClickListener {
    private LinearLayout mOnestartLL, mRefLL;// 卫星设置下面的 （一键对星
    // ，参数设置，调试控制）
    private LinearLayout mLink_State, mRouteset, mBandwidth;// 链路状态
    // （链路状态，路由设置，带宽叠加）
    private LinearLayout mSystem_Update;// 系统升级 密码修改
    //private ToggleButton mBtn_kuandaidiejia;//宽带叠加按钮
    private ACache mACache;
    private String SET_REF_TAG="setreftag";
    private Boolean isChoose=false;//是否选择开启宽带叠加
    private SharedPreferences sPreferences;//用于保存控件选择的状态
    private Editor editor;
    private TextView super_weixingshezhi,super_shoudongduixing,super_canshushezhi;
    private RequestQueue mRequestQueue;
    private Spinner mTianxianStyle;
    public static  boolean mSateFlag = false;
    private String mOduType,mToken;
    private HashMap<String, Toast> toaHashMap=new HashMap<>();
    private RelativeLayout rl_top_bar;
    private TextView mTittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_set_activity);
        mACache=ACache.get(this);
        mACache.put(SET_REF_TAG,"1");
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        if(AppData.accountModel!=null) {
            mToken = AppData.accountModel.getSessionToken();
        }
        initView();
        initToasts();
        initEvent();
    }
    private void initEvent() {
        rl_top_bar.setOnTouchListener(new ComBackTouchListener());
    }
    @SuppressLint("ShowToast")
    private void initToasts() {
        Toast toast=Toast.makeText(SuperSetActivity.this,"正在查询天线类型，请稍等！", Toast.LENGTH_LONG);
        toaHashMap.put("正在查询天线类型，请稍等！", toast);
    }
    // 查找控件
    private void initView() {
        rl_top_bar= (RelativeLayout) findViewById(R.id.rl_top_bar);
        mTittle= (TextView)findViewById(R.id.tv_bar_title);
        mTittle.setText(R.string.high_set);
        super_weixingshezhi=(TextView) findViewById(R.id.super_weixingshezhi);
        super_weixingshezhi.setTypeface(AppData.fontPutu);
        super_canshushezhi=(TextView) findViewById(R.id.super_canshushezhi);
        super_canshushezhi.setTypeface(AppData.fontXiti);
        super_shoudongduixing=(TextView) findViewById(R.id.super_shoudongduixing);
        super_shoudongduixing.setTypeface(AppData.fontXiti);
        mOnestartLL = (LinearLayout) findViewById(R.id.superset_onestart);
        mRefLL = (LinearLayout) findViewById(R.id.superset_ref);
        mOnestartLL.setOnClickListener(this);
        mRefLL.setOnClickListener(this);
        querySessionStatus(new OnSessionStatusListener() {
            @Override
            public void sessionSuccess() {
                queryTianStyle();
            }

            @Override
            public void sessionErrorResponse() {
                queryTianStyle();
            }
        });
    }

    Intent mIntent;

    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.superset_onestart:
                //Toast.makeText(this, "点击了一键对星", Toast.LENGTH_SHORT).show();
                //mIntent = new Intent(SuperSetActivity.this, OneStartActivity.class);
                mIntent = new Intent(SuperSetActivity.this, OneKeyStarActivity.class);
                startActivity(mIntent);
                break;

            case R.id.superset_ref:
               //tianStyleDialog();
                if(mOduType==null){
                    if (toaHashMap.get("正在查询天线类型，请稍等！")!=null){
                        toaHashMap.get("正在查询天线类型，请稍等！").show();
                    }
                }else{
                    if("0".equals(mOduType)){
                        tianStyleDialog();
                    }else if("1".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        startActivity(mIntent);
                    }else if("2".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        startActivity(mIntent);
                    }else if("3".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        startActivity(mIntent);
                    }else if("4".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        mIntent.putExtra("compass", "S(三轴)");
                        startActivity(mIntent);
                    }else if("5".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        mIntent.putExtra("compass", "S(三轴)");
                        startActivity(mIntent);
                    }else if("6".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        mIntent.putExtra("compass", "S(三轴)");
                        startActivity(mIntent);
                    }else if("7".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        mIntent.putExtra("compass", "C系列");
                        startActivity(mIntent);
                    }else if("8".equals(mOduType)){
                        Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                        mIntent.putExtra("compass", "C系列");
                        startActivity(mIntent);
                    }
                }
                break;

        }
    }
    protected void tianStyleDialog() {
        NiceDialog.init().setLayoutId(R.layout.star_type_device).setConvertListener(new ViewConvertListener() {
            @Override
            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                View convertView = holder.getConvertView();
                mTianxianStyle =(Spinner) convertView.findViewById(R.id.spinner_tianxianstyle);// 天线类型
                mTianxianStyle.setAdapter(new MySpinnerAdapter(SuperSetActivity.this,
                        R.layout.spinner_stytle, getResources().getStringArray(R.array.tianxian_style)));
                mTianxianStyle.setSelection(0);
                holder.setOnClickListener(R.id.bt_cancel, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                holder.setOnClickListener(R.id.bt_ok, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToActivityByType();
                        dialog.dismiss();
                    }
                });
            }

            private void goToActivityByType() {
                if(mTianxianStyle.getSelectedItem().toString().equals("S(两轴)")){
                    Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("S(三轴)")){
                    Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                    mIntent.putExtra("compass", "S(三轴)");
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("V系列")){
                    Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("N系列")){
                    Intent mIntent = new Intent(SuperSetActivity.this,ManualControlActivity.class);
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("C系列")){
                    Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                    mIntent.putExtra("compass", "C系列");
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("P(自动)")){
                    Intent mIntent = new Intent(SuperSetActivity.this,SuperSetRefActivity.class);
                    startActivity(mIntent);

                }else if(mTianxianStyle.getSelectedItem().toString().equals("P(手动)")){
                    Intent mIntent = new Intent(SuperSetActivity.this,ManualControlActivity.class);
                    startActivity(mIntent);

                }
            }
        }).show(getSupportFragmentManager());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void queryTianStyle() {
        String queryTianStyleUrl= GET_TIAN_STYLE;
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("sessionToken", mToken);
            LoggerSave.requestLog(GET_TIAN_STYLE,jsonObject.toString());
            JsonObjectRequest request = new JsonObjectRequest(Method.POST,
                    queryTianStyleUrl, jsonObject,
                    new Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                LoggerSave.responseLog(GET_TIAN_STYLE,response.toString());
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.optString("msg");
                                if("0".equals(code)) {
                                    //Toast.makeText(SuperSetActivity.this, "天线类型查询成功",0).show();
                                }else if("-1".equals(code)){
                                    if("acu_occupy".equals(msg)){
                                        showMutualDialog();
                                    }
                                }else if("-2".equals(code)){
                                    showLoginDialog();
                                }
                                mOduType  = jsonObject.getString("odutype");
                                if("0".equals(mOduType)){
                                    System.out.println("未知天线类型");
                                }else if("1".equals(mOduType)){
                                    System.out.println("V4");
                                }else if("2".equals(mOduType)){
                                    System.out.println("V6");
                                }else if("3".equals(mOduType)){
                                    System.out.println("S6");
                                }else if("4".equals(mOduType)){
                                    System.out.println("S6A");//三轴
                                }else if("5".equals(mOduType)){
                                    System.out.println("S8");
                                }else if("6".equals(mOduType)){
                                    System.out.println("S9");
                                }else if("7".equals(mOduType)){
                                    System.out.println("C6");
                                }else if("8".equals(mOduType)){
                                    System.out.println("C9");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mOduType="0";
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(10*1000,0,0f));
            mRequestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            Iterator<Entry<String, Toast>> iter = toaHashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Toast> entry = (Entry<String, Toast>) iter.next();
                Toast toast=entry.getValue();
                Field field = toast.getClass().getDeclaredField("mTN");
                field.setAccessible(true);
                Object obj = field.get(toast);
                java.lang.reflect.Method m=obj.getClass().getDeclaredMethod("hide", new Class[0]);
                m.invoke(obj, new Object[]{});
                iter.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
