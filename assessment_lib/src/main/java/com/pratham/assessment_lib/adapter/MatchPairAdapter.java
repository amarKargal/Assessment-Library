package com.pratham.assessment_lib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.domain.SubOptions;

import java.util.List;

import static com.pratham.assessment_lib.Utility.Assessment_Constants.assessPath;

public class MatchPairAdapter extends RecyclerView.Adapter<MatchPairAdapter.MyViewHolder> {
    List<SubOptions> pairList;
    Context context;

    public MatchPairAdapter(List<SubOptions> pairList, Context context) {
        this.pairList = pairList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView, iv_zoom_eye;
        RelativeLayout rl_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_text);
            imageView = itemView.findViewById(R.id.iv_choice_image);
            iv_zoom_eye = itemView.findViewById(R.id.iv_zoom_eye);
            rl_img = itemView.findViewById(R.id.rl_img);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_simple_text_row_old, viewGroup, false);
        return new MyViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        ScienceQuestionChoice scienceQuestionChoice = pairList.get(i);
        setOdiaFont(context, myViewHolder.text);

        if (!scienceQuestionChoice.getChoiceurl().equalsIgnoreCase("")) {
            myViewHolder.imageView.setVisibility(View.VISIBLE);
            myViewHolder.text.setVisibility(View.GONE);

            Glide.with(context).asBitmap().
                    load(*//*Assessment_Constants.loadOnlineImagePath + *//*scienceQuestionChoice.getChoiceurl()).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL))
                    .into(myViewHolder.imageView);
        } else myViewHolder.text.setText(scienceQuestionChoice.getChoicename());

    }*/

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        final SubOptions subOptions = pairList.get(i);
        if (!subOptions.getChoiceurl().equalsIgnoreCase("")) {
            final String path = /*Assessment_Constants.loadOnlineImagePath +*/ subOptions.getChoiceurl();

            String fileName = Assessment_Utility.getFileName(subOptions.getQid(), subOptions.getChoiceurl());
            final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;


//                holder.iv_choice_image.setVisibility(View.VISIBLE);
            holder.rl_img.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.GONE);
            holder.text.setTextColor(Color.WHITE);
          /*  Glide.with(context).asBitmap().
                    load(path).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL))
                    .into(holder.imageView);
*/



                Glide.with(context)
                        .load(path)
//                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(Drawable.createFromPath(localPath)))
                        .into(holder.imageView);










//                holder.iv_choice_image.setOnClickListener(new View.OnClickListener() {
            holder.iv_zoom_eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("QQQ", "choice clicked....");
                      /*  ZoomImageDialog zoomImageDialog = new ZoomImageDialog(context, path, localPath);
                        zoomImageDialog.show();*/
                    Assessment_Utility.showZoomDialog(context, path, localPath);
                }
            });
        } else {
            holder.rl_img.setVisibility(View.GONE);
            holder.text.setText(subOptions.getChoicename());
        }

    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }
}
