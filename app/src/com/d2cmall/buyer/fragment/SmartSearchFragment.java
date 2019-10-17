package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SmartSearchApi;
import com.d2cmall.buyer.bean.SmartSearchBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Base64;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SmartSearchFragment extends Fragment implements ListView.OnItemClickListener {


    @Bind(R.id.listView)
    ListView listView;
    private List<String> list = new ArrayList<>();
    public List<String> itemClick = new ArrayList<>();
    private SmartSearchAdapter adapter;
    private SmartSearchListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview2, container, false);
        ButterKnife.bind(this, view);
        adapter = new SmartSearchAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                if (listView.getScrollY() > 0) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
        return view;
    }

    public void requestNet(String keyvalue) {
        list.clear();
        adapter.notifyDataSetChanged();
        SmartSearchApi api = new SmartSearchApi();
        api.setKeyword(Base64.encode(keyvalue.getBytes()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<SmartSearchBean>() {
            @Override
            public void onResponse(SmartSearchBean response) {
                if (response.getData().getList() != null && response.getData().getList().size() > 0) {
                    list.clear();
                    list.addAll(response.getData().getList());
                    adapter.notifyDataSetChanged();
                }
                if (listener != null) {
                    listener.hasContent(list.size()>0);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.hasContent(false);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemClick.clear();
        itemClick.add(list.get(position));
        if (listener != null) {
            listener.clickItemContent(itemClick.get(0));
        }
    }

    public class SmartSearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_smart_search, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(list.get(position));

            return convertView;
        }
    }

    public class ViewHolder {
        TextView tv_name;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setSmartSearchListener(SmartSearchListener listener) {
        this.listener = listener;
    }

    public static interface SmartSearchListener {
        void hasContent(boolean is);

        void clickItemContent(String content);
    }
}
