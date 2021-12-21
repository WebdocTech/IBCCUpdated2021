package com.webdoc.ibcc.DashBoard.Faq;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentFaqsBinding;

public class FaqsFrag extends Fragment {
    private FragmentFaqsBinding layoutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentFaqsBinding.inflate(inflater, container, false);

        layoutBinding.webView1.getSettings().setJavaScriptEnabled(true);
        layoutBinding.webView1.getSettings().setPluginState(WebSettings.PluginState.ON);
        Global.utils.showCustomLoadingDialog(getActivity());
        layoutBinding.webView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Global.utils.hideCustomLoadingDialog();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(getActivity(), "Ooops! error ", Toast.LENGTH_SHORT).show();
            }

        });
        layoutBinding.webView1.loadUrl("https://ibcc.edu.pk/faq/");

        if (Global.utils.HaveNetwork(getActivity())) {
            layoutBinding.webView1.setVisibility(View.VISIBLE);
            layoutBinding.NoInternetLayout.setVisibility(View.GONE);
        } else {
            layoutBinding.webView1.setVisibility(View.GONE);
            layoutBinding.NoInternetLayout.setVisibility(View.VISIBLE);
        }

        clickListeners();

        return layoutBinding.getRoot();
    }

    private void clickListeners() {
        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
            }
        });
    }

}