package com.codepath.apps.fancytweets.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.fancytweets.R;
import com.codepath.apps.fancytweets.models.User;
import com.squareup.picasso.Picasso;

public class ComposeTweetDialog extends DialogFragment {

    // rounded button: http://android--code.blogspot.com/2015/01/android-rounded-corners-button.html
    // dialog doc: http://developer.android.com/reference/android/support/v4/app/DialogFragment.html#setStyle(int, int)
    // custom cursor color in edittext: http://code2care.org/pages/how-to-change-android-edittext-cursor-color/

    public interface OnSubmitNewTweetListener {
        void onNewTweetSubmitted(String tweetBody);
    }
    private OnSubmitNewTweetListener listener;

    private ImageView ivProfilePhoto;
    private Button btnNewTweet;
    private TextView tvUsername;
    private TextView tvScreenname;
    private EditText etNewTweet;
    private ImageView ivComposeClose;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        listener = (OnSubmitNewTweetListener) activity;
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public ComposeTweetDialog() {
        // empty constructor is needed?
    }

    public static ComposeTweetDialog newInstance(User user) {
        ComposeTweetDialog frag = new ComposeTweetDialog();
        Bundle args = new Bundle();
        args.putString("username", user.getName());
        args.putString("screenname", user.getScreenName());
        args.putString("profile_picture_url", user.getProfileImageUrl());
        frag.setArguments(args);
        return frag;
    }

    // get rid of title bar for now
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compose_tweet, container);

        // x to close dialog
        ImageView ivClose = (ImageView) v.findViewById(R.id.ivComposeClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button btn = (Button) v.findViewById(R.id.btnComposeNewTweet);
        etNewTweet = (EditText) v.findViewById(R.id.etComposeNewTweet);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                listener.onNewTweetSubmitted(etNewTweet.getText().toString());
                getDialog().dismiss();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //do I need a viewholder for everything?
        super.onViewCreated(view, savedInstanceState);
        ivProfilePhoto = (ImageView) view.findViewById(R.id.ivComposeProfilePhoto);
        ivProfilePhoto.setImageResource(android.R.color.transparent); // good place to set a placeholder while clearing out the old content
        Picasso.with(getContext()).load(getArguments().getString("profile_picture_url")).into(ivProfilePhoto);
        tvUsername = (TextView) view.findViewById(R.id.tvComposeUsername);
        tvUsername.setText(getArguments().getString("username"));
        tvScreenname = (TextView) view.findViewById(R.id.tvComposeScreenname);
        // TODO needs refactoring
        tvScreenname.setText(getContext().getString(R.string.at_sign) + getArguments().getString("screenname"));
        ivComposeClose = (ImageView) view.findViewById(R.id.ivComposeClose);
        ivComposeClose.setColorFilter(ContextCompat.getColor(getContext(), R.color.twitter_light_blue));
        etNewTweet = (EditText) view.findViewById(R.id.etComposeNewTweet);
        etNewTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // Get field from view


/*
         = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    */
    }


}
