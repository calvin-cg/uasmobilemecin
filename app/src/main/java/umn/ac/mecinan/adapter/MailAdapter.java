package umn.ac.mecinan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.activity.MailDetails;
import umn.ac.mecinan.model.Mail;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder>{

    private Context mCtx;
    private List<Mail> mailList;

    public MailAdapter(Context mCtx, List<Mail> mailList){
        this.mCtx = mCtx;
        this.mailList = mailList;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mail_list, viewGroup, false);
        MailAdapter.MailViewHolder holder = new MailAdapter.MailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MailViewHolder mailViewHolder, int i) {
        final Mail mail = mailList.get(i);

        mailViewHolder.tv_mailList_title.setText(mail.getMailTitle());

        /** Mail Date **/
        String strDateFormat, formattedDate;
        DateFormat dateFormat;
        strDateFormat = "dd-MMMM-yyyy kk:mm:ss";
        dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(mail.getMailReceivedDate());
        mailViewHolder.tv_mailList_date.setText(formattedDate);

        mailViewHolder.tv_mailList_projectName.setText(String.valueOf(mail.getProjectName()));
        mailViewHolder.tv_mailList_content.setText(mail.getMailContent());

        /** Changing Card Background & icon according to read status **/
        if(mail.getMailIsRead()) {
            mailViewHolder.cv_mail_list.setCardBackgroundColor(mCtx.getResources().getColor(R.color.colorBorderLight));
            mailViewHolder.iv_mailList_mailImage.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.email_opened_orange_256));
        } else {
            mailViewHolder.iv_mailList_mailImage.setImageDrawable(mCtx.getResources().getDrawable(R.mipmap.mail_closed_orange_256));
        }

        mailViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MailDetails.class);

                Bundle extras = new Bundle();
                extras.putString("mail_details_id_mail", mail.getIdMail());
                extras.putString("mail_details_category", mail.getMailCategory());
                extras.putString("mail_details_project_field", mail.getProjectField());
                extras.putString("mail_details_project_category", mail.getProjectCategory());
                extras.putString("mail_details_project_name", mail.getProjectName());
                extras.putLong("mail_details_date", mail.getMailReceivedDate());
                extras.putString("mail_details_title", mail.getMailTitle());
                extras.putString("mail_details_content", mail.getMailContent());

                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        CardView cv_mail_list;
        ImageView iv_mailList_mailImage;
        TextView tv_mailList_title, tv_mailList_content, tv_mailList_date;
        TextView tv_mailList_projectName;
        View view;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_mail_list = itemView.findViewById(R.id.cv_mail_list);
            iv_mailList_mailImage = itemView.findViewById(R.id.iv_mailList_mailImage);
            tv_mailList_title = itemView.findViewById(R.id.tv_mailList_title);
            tv_mailList_content = itemView.findViewById(R.id.tv_mailList_content);
            tv_mailList_date = itemView.findViewById(R.id.tv_mailList_date);
            tv_mailList_projectName = itemView.findViewById(R.id.tv_mailList_projectName);
            view = itemView;
        }
    }

    public void updateMailList(List<Mail> mails) {
        mailList.clear();

        mailList = mails;

        this.notifyDataSetChanged();
    }
}
