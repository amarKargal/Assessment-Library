package com.pratham.assessment_lib.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pratham.assessment_lib.R;
import com.pratham.assessment_lib.Utility.Assessment_Constants;
import com.pratham.assessment_lib.Utility.Assessment_Utility;
import com.pratham.assessment_lib.domain.SubOptions;
import com.pratham.assessment_lib.interfaces.AssessmentAnswerListener;
import com.pratham.assessment_lib.interfaces.StartDragListener;
import com.pratham.assessment_lib.science.ScienceAssessmentActivity;
import com.pratham.assessment_lib.viewpager_fragments.ArrangeSequenceFragment;
import com.pratham.assessment_lib.viewpager_fragments.ItemMoveCallback;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pratham.assessment_lib.Utility.Assessment_Constants.assessPath;
import static com.pratham.assessment_lib.Utility.Assessment_Utility.setOdiaFont;


public class ArrangeSeqDragDropAdapter extends RecyclerView.Adapter<ArrangeSeqDragDropAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {
    List<SubOptions> draggedList = new ArrayList<>();
    private List<SubOptions> data;
    Context context;
    //    DragDropListener dragDropListener;
    //    QuestionTypeListener questionTypeListener;
    StartDragListener startDragListener;
    AssessmentAnswerListener assessmentAnswerListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        ImageView iv_choice_image;
        View rowView;
        ScrollView sv_arr_seq;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTitle = itemView.findViewById(R.id.tv_text);
            iv_choice_image = itemView.findViewById(R.id.iv_choice_image);
            sv_arr_seq = itemView.findViewById(R.id.sv_arr_seq);
        }
    }


    public ArrangeSeqDragDropAdapter(ArrangeSequenceFragment fragment, List<SubOptions> data, Context context) {
        this.data = data;
        this.context = context;
//        questionTypeListener = scienceAdapter;
        startDragListener = fragment;
        assessmentAnswerListener = (ScienceAssessmentActivity) context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_text_row_arr_seq, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        holder.mTitle.setText(data.get(position));
//        if(data.get(position))
        setOdiaFont(context, holder.mTitle);

        holder.mTitle.setTextColor(Assessment_Utility.selectedColor);

        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_swap_vertical_small);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.2),
                (int) (drawable.getIntrinsicHeight() * 0.2));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 10, 10);
        holder.mTitle.setCompoundDrawablesWithIntrinsicBounds(sd.getDrawable(), null, null, null);

        draggedList.clear();
        if (data.size() > 0) {
            SubOptions subOptions = data.get(position);
            if (!subOptions.getChoiceurl().equalsIgnoreCase("")) {
                final String path = /*Assessment_Constants.loadOnlineImagePath +*/ subOptions.getChoiceurl();

                String fileName = Assessment_Utility.getFileName(subOptions.getQid(), subOptions.getChoiceurl());
                final String localPath = assessPath + Assessment_Constants.STORE_DOWNLOADED_MEDIA_PATH + "/" + fileName;


                holder.iv_choice_image.setVisibility(View.VISIBLE);
                holder.mTitle.setVisibility(View.GONE);
                holder.mTitle.setTextColor(Color.WHITE);
                Glide.with(context).asBitmap().
                        load(path).apply(new RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL))
                        .into(holder.iv_choice_image);

                holder.iv_choice_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /*  ZoomImageDialog zoomImageDialog = new ZoomImageDialog(context, path, localPath);
                        zoomImageDialog.show();*/
                        Assessment_Utility.showZoomDialog(context, path, localPath);
                    }
                });
            } else holder.mTitle.setText(subOptions.getChoicename());

            holder.mTitle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() ==
                            MotionEvent.ACTION_DOWN) {
                        startDragListener.requestDrag(holder);
                    }
                    return false;
                }
            });

            holder.iv_choice_image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() ==
                            MotionEvent.ACTION_DOWN) {
                        startDragListener.requestDrag(holder);
                    }
                    return false;
                }
            });

            //todo add bubbleshowcase
       /* if (!Assessment_Constants.isShowcaseDisplayed)
            if (position == 0) {
                Assessment_Constants.isShowcaseDisplayed = true;
                new BubbleShowCaseBuilder((Activity) context)
                        .title("Note: ")
                        .description("swap to match the answer on the right to the word on the left")
                        .backgroundColor(ContextCompat.getColor(context, R.color.colorAccentDark))
                        .closeActionImage(ContextCompat.getDrawable(context, R.drawable.ic_close))
                        .targetView(holder.itemView).show();
            }*/
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        try {

            Log.d("QQQ", fromPosition + " " + toPosition);
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Log.d("QQQfor1", fromPosition + " " + toPosition);
                    Collections.swap(data, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Log.d("QQQfor2", fromPosition + " " + toPosition);
                    Collections.swap(data, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
//            draggedList = data;
            Log.d("sss", draggedList.toString());
//        dragDropListener.setList(draggedList, data.get(0).getQid());
//        questionTypeListener.setAnswer("", "", data.get(0).getQid(), draggedList);
            assessmentAnswerListener.setAnswerInActivity("", "", data.get(0).getQid(), data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRowSelected(MatchPairDragDropAdapter.MyViewHolder myViewHolder) {

    }

    @Override
    public void onRowSelected(ArrangeSeqDragDropAdapter.MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradient_selector));
        myViewHolder.mTitle.setTextColor(Assessment_Utility.selectedColor);
    }

    @Override
    public void onRowClear(MatchPairDragDropAdapter.MyViewHolder myViewHolder) {

    }

    @Override
    public void onRowClear(ArrangeSeqDragDropAdapter.MyViewHolder myViewHolder) {

    }

  /*  @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
//       myViewHolder.rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ripple_rectangle));
        *//*  myViewHolder.mTitle.setTextColor(Assessment_Utility.selectedColor);
     *//*
        myViewHolder.rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradient_selector));
        myViewHolder.mTitle.setTextColor(Assessment_Utility.selectedColor);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
//        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
       *//* myViewHolder.rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradient_selector));
        myViewHolder.mTitle.setTextColor(Assessment_Utility.selectedColor);*//*

    }*/
}


