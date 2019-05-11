package umn.ac.mecinan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import umn.ac.mecinan.R;
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
        Mail mail = mailList.get(i);

        //mailViewHolder.iv_mailList_mailImage.setImageDrawable(mCtx.getResources().getDrawable(mail.getMailImage()));

        mailViewHolder.tv_mailList_title.setText(mail.getMailTitle());
        //mailViewHolder.tv_mailList_date.setText(String.valueOf(mail.getMailReceivedDate()));

        mailViewHolder.tv_mailList_projectName.setText(String.valueOf(mail.getProjectName()));
        mailViewHolder.tv_mailList_content.setText(mail.getMailContent());

    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    public class MailViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_mailList_mailImage;
        TextView tv_mailList_title, tv_mailList_content, tv_mailList_date;
        TextView tv_mailList_projectName;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_mailList_mailImage = itemView.findViewById(R.id.iv_mailList_mailImage);
            tv_mailList_title = itemView.findViewById(R.id.tv_mailList_title);
            tv_mailList_content = itemView.findViewById(R.id.tv_mailList_content);
            tv_mailList_date = itemView.findViewById(R.id.tv_mailList_date);
            tv_mailList_projectName = itemView.findViewById(R.id.tv_mailList_projectName);

        }
    }

    public void updateMailList(List<Mail> mails) {
        mailList.clear();

        mailList = mails;

        this.notifyDataSetChanged();
    }
}
