package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.AppMenuBean;
import com.d2cmall.buyer.bean.ModuleBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ModuleGridAdapter extends BaseAdapter {

    private Context context;
    private OnModuleItemClickListener onModuleItemClickListener;
    private UserBean.DataEntity.MemberEntity user;
    private int type = -1;
    private boolean isMore;

    public int[] txts = {R.string.label_vip_center, R.string.label_pre_clothe,
            R.string.label_kefu_help, R.string.label_collage, R.string.label_enter_partner,
            R.string.label_feed_back,  R.string.label_about_d2c,R.string.label_more_tools,};
    public int[] txtsMroe = {R.string.label_tshirt, R.string.label_report_product,
            };
    public int[] imgs = {R.mipmap.icon_mytool_member, R.mipmap.icon_mytool_fitting,
            R.mipmap.icon_mytool_service, R.mipmap.icon_mytool_group,
            R.mipmap.icon_mytool_cooperate,
            R.mipmap.icon_mytool_feedback,
            R.mipmap.icon_mytool_tset, R.mipmap.icon_tool_more};
    public int[] imgsMore = {R.mipmap.icon_mytool_tset, R.mipmap.icon_mytool_report
            };
    public String[] urls = {"", "", "", "", "", "", "", ""};
    public String[] urlsMore = {"", ""};
    private int moduleHeight;
    private ArrayList<ModuleBean> list = new ArrayList<>();

    public ModuleGridAdapter(Context context ,boolean isMore) {
        super();
        this.context = context;
        this.isMore=isMore;
        Point point = Util.getDeviceSize(context);
        moduleHeight = Math.round(point.x / 4);
        user = Session.getInstance().getUserFromFile(context);
        if (user != null) {
            type = user.getType();
        }
        initModuleList();
    }

    private void initModuleList() {
        if(isMore){
            for (int i = 0; i < txtsMroe.length; i++) {
                ModuleBean moduleBean = new ModuleBean();
                moduleBean.setTxtId(txtsMroe[i]);
                moduleBean.setImgId(imgsMore[i]);
                moduleBean.setUrl(urlsMore[i]);
                moduleBean.setTagId(i);//设置tagId
                list.add(moduleBean);
            }
        }else{
            for (int i = 0; i < txts.length; i++) {
                ModuleBean moduleBean = new ModuleBean();
                moduleBean.setTxtId(txts[i]);
                moduleBean.setImgId(imgs[i]);
                moduleBean.setUrl(urls[i]);
                moduleBean.setTagId(i);//设置tagId
                list.add(moduleBean);
            }
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final ModuleBean moduleBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_module, parent, false);
            holder.moduleLayout = convertView.findViewById(R.id.module_layout);
            holder.moduleLayout.getLayoutParams().height = moduleHeight;
            holder.imgName = (ImageView) convertView.findViewById(R.id.img_name);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!Util.isEmpty(moduleBean.getName())) {//如果名不空，后台取的
            UniversalImageLoader.displayImage(context,moduleBean.getIconUrl(),
                    holder.imgName, R.mipmap.ic_default_pic_w);
            holder.tvName.setText(moduleBean.getName());
        } else {
            holder.imgName.setImageResource(moduleBean.getImgId());
            holder.tvName.setText(moduleBean.getTxtId());
        }
        if (!Util.isEmpty(moduleBean.getCount())) {
            holder.tvCount.setVisibility(View.VISIBLE);
            holder.tvCount.setText(moduleBean.getCount());
        } else {
            holder.tvCount.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModuleItemClickListener.onModuleItemClick(moduleBean);
            }
        });
        return convertView;
    }

    class ViewHolder {
        View moduleLayout;
        ImageView imgName;
        TextView tvName;
        TextView tvCount;
    }

    public void removeItemByTagId(int tagId) {
        for (ModuleBean moduleBean : list) {
            if (moduleBean.getTagId() == tagId) {
                list.remove(moduleBean);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void addItemByTagId(int tagId) {
        boolean isExist = false;
        for (ModuleBean moduleBean : list) {
            if (moduleBean.getTagId() == tagId) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            ModuleBean moduleBean = new ModuleBean();
            moduleBean.setTxtId(txts[tagId]);
            moduleBean.setTagId(tagId);
            moduleBean.setImgId(imgs[tagId]);
            list.add(tagId, moduleBean);
            notifyDataSetChanged();
        }
    }

    public void setItemCountByTagId(int tagId, String count) {
        for (ModuleBean moduleBean : list) {
            if (moduleBean.getTagId() == tagId) {
                moduleBean.setCount(count);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public interface OnModuleItemClickListener {
        void onModuleItemClick(ModuleBean moduleBean);
    }

    public void setOnModuleItemClickListener(OnModuleItemClickListener onModuleItemClickListener) {
        this.onModuleItemClickListener = onModuleItemClickListener;
    }

    public void addWebItem(List<AppMenuBean.DataEntity.MenuEntity> menuEntities) {//添加后台取到的icon
        List<ModuleBean> mlist = new ArrayList<>();
        for (int i = 0; i < menuEntities.size(); i++) {
            ModuleBean moduleBean = new ModuleBean();
            moduleBean.setName(menuEntities.get(i).getName());
            moduleBean.setIconUrl(menuEntities.get(i).getIconUrl());
            moduleBean.setUrl(menuEntities.get(i).getUrl());
            moduleBean.setTagId(txts.length + i);
            mlist.add(moduleBean);
        }
        //线上图标放到"关于我们"前面
        list.addAll(list.size() - 1, mlist);
        notifyDataSetChanged();
    }
}