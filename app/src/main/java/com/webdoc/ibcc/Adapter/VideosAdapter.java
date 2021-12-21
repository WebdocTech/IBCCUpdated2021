package com.webdoc.ibcc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentCheckListModel;
import com.webdoc.ibcc.Model.VideosModel;
import com.webdoc.ibcc.R;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    Activity context;

    public VideosAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videos_item, parent, false);
        return new VideosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideosAdapter.ViewHolder holder, final int position) {
        VideosModel item = Global.videosModel.get(position);
        String title = item.getVideoTitle();
        String duration = item.getVideoDuration();
        String date = item.getDatetime();
        String url = item.getUrl();
        String id = item.getVideoId();

        holder.tv_video_title.setText(title);
        holder.tv_date.setText(date);
        holder.tv_duration.setText(duration);

        holder.mWebView.clearCache(true);
        holder.mWebView.clearHistory();
        holder.mWebView.getSettings().setJavaScriptEnabled(true);
        //holder.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        holder.mWebView.loadUrl(url);
        holder.mWebView.setWebViewClient(new WebViewClient());

    }

    @Override
    public int getItemCount() {
        return Global.videosModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private WebView mWebView;
        CardView cc;
        TextView tv_video_title, tv_date, tv_duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mWebView = itemView.findViewById(R.id.webView);
            cc = itemView.findViewById(R.id.videoCard);
            tv_video_title = itemView.findViewById(R.id.tv_video_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_duration = itemView.findViewById(R.id.tv_duration);
        }
    }

}
