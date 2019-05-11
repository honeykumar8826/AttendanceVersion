package com.attendnce.cloudanalogy.attendancev1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Birtday_AniversaryAdapter extends RecyclerView.Adapter<Birtday_AniversaryAdapter.MyViewHolder> {
    private List<Birthday_AniversaryGetterSetter> birthdaylist;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthday_list_show, parent, false);

        return new Birtday_AniversaryAdapter.MyViewHolder(itemView);
    }
    public Birtday_AniversaryAdapter(List<Birthday_AniversaryGetterSetter> birthdaylist) {
        this.birthdaylist = birthdaylist;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Birthday_AniversaryGetterSetter birthday = birthdaylist.get(position);
        holder.bdate.setText(birthday.getDateofbirtday());
        holder.bname.setText(birthday.getName());
    }

    @Override
    public int getItemCount() {
        return  birthdaylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bdate, bname;
        public ImageView rimg;
        public MyViewHolder(View view) {
            super(view);
            bdate = (TextView) view.findViewById(R.id.birthdate);
            bname = (TextView) view.findViewById(R.id.birthname);
            // rimg = view.findViewById(R.id.imp_img);
        }
    }
}
