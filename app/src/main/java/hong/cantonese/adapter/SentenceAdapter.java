package hong.cantonese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hong.cantonese.MainActivity;
import hong.cantonese.R;

import static hong.cantonese.R.id.iv_synthesize;

/**
 * Created by hongenit on 2017/1/15.
 */

public class SentenceAdapter extends BaseAdapter {

    private ArrayList<String> mVoiceTextList;

    private Context mContext;
    public SentenceAdapter(ArrayList<String> mVoiceTextList, Context mContext) {
        this.mVoiceTextList = mVoiceTextList;
        this.mContext = mContext;
    }

    public void setmVoiceTextList(ArrayList<String> mVoiceTextList) {
        this.mVoiceTextList = mVoiceTextList;
        notifyDataSetChanged();
    }

    public ArrayList<String> getmVoiceTextList() {
        return mVoiceTextList;
    }

    @Override
    public int getCount() {
        return mVoiceTextList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sentence,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_sentence.setText(mVoiceTextList.get(position));
        holder.iv_synthesize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVoiceTextList.remove(position);
                notifyDataSetChanged();
                MainActivity.currentIndex = 0;
            }
        });

        return convertView;
    }


    class ViewHolder{
        TextView tv_sentence;
        ImageView iv_synthesize;
        public ViewHolder(View view) {
            tv_sentence = (TextView) view.findViewById(R.id.tv_sentence);
            iv_synthesize = (ImageView) view.findViewById(R.id.iv_synthesize);
        }
    }


}
