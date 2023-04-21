package uk.ac.tees.a0547574.learnchineseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import uk.ac.tees.a0547574.learnchineseapp.Info.NotepadBean;
import uk.ac.tees.a0547574.learnchineseapp.R;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;  //布局解析
    private List<NotepadBean> list; //多条信息实体列表
    //构造方法如下
    public NotepadAdapter(Context context, List<NotepadBean> list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null ? 0:list.size();  //如果列表为空则返回0，如果列表不为空则返回列表的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.notepad_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        NotepadBean noteInfo=(NotepadBean) getItem(position);
        viewHolder.tvNotepadContent.setText(noteInfo.getNotepadContent());
        viewHolder.tvNotepadTime.setText(noteInfo.getNotepadTime());
        return convertView;
    }

    //定义一个ViewHolder为了复用item视图，优化列表的加载
    class ViewHolder{
        TextView tvNotepadContent;
        TextView tvNotepadTime;
        public ViewHolder(View view){
            tvNotepadContent=view.findViewById(R.id.item_content);
            tvNotepadTime=view.findViewById(R.id.item_time);
        }
    }
}
