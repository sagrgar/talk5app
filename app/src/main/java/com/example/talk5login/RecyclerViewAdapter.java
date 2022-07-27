package com.example.talk5login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.talk5login.Model.AuditResult;
import com.example.talk5login.Model.Data;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Data> dataArrayList;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data newdata = dataArrayList.get(position);
        holder.question_tv.setText(String.valueOf(newdata.getQuestionCount()) + " Questions");
        holder.resultTitle_tv.setText(newdata.getAuditName());
       if (newdata.getQuestionCount() > newdata.getQuestionCompleted() && newdata.getQuestionCompleted()> 0 ){
           holder.regGreen.setBackgroundColor(Color.parseColor("#0F9D58"));
       }else {
           holder.regGreen.setBackgroundColor(Color.parseColor("#009ada"));
       }
       if (newdata.getQuestionCompleted() == 0){
           holder.regGreen.setBackgroundColor(Color.parseColor("#DB4437"));
       }

       holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               if (newdata.getQuestionCompleted() < newdata.getQuestionCount()){
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                   builder.setMessage("Set the item as completed?");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           newdata.setQuestionCompleted(newdata.getQuestionCount());
                           notifyDataSetChanged();
                       }
                   });

                   builder.create().show();
               }

               if (newdata.getQuestionCompleted() == newdata.getQuestionCount()){
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                   builder.setMessage("Restart the task?");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           newdata.setQuestionCompleted(0);
                           notifyDataSetChanged();
                       }
                   });
                   builder.create().show();
               }

               return false;
           }
       });




    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public void setData(ArrayList<Data> data){
        this.dataArrayList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout regGreen;
        private TextView question_tv, resultTitle_tv;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            regGreen = itemView.findViewById(R.id.redGreen);
            question_tv = itemView.findViewById(R.id.question_tv);
            resultTitle_tv = itemView.findViewById(R.id.resultTitle_tv);
        }
    }
}
