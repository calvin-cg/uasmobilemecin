package umn.ac.mecinan.model;

import android.widget.ImageView;

public class Mail {

    private ImageView mailImage;
    private String mailTitle, mailContent, mailReceivedDate;
    private String projectName, projectEmployeeName, projectClientName;

    public Mail(ImageView mailImage, String mailTitle, String mailContent, String mailReceivedDate, String projectName, String projectEmployeeName, String projectClientName) {
        this.mailImage = mailImage;
        this.mailTitle = mailTitle;
        this.mailContent = mailContent;
        this.mailReceivedDate = mailReceivedDate;
        this.projectName = projectName;
        this.projectEmployeeName = projectEmployeeName;
        this.projectClientName = projectClientName;
    }

    public ImageView getMailImage() { return mailImage; }
    public void setMailImage(ImageView mailImage) { this.mailImage = mailImage; }

    public String getMailTitle() { return mailTitle; }
    public void setMailTitle(String mailTitle) { this.mailTitle = mailTitle; }

    public String getMailContent() { return mailContent; }
    public void setMailContent(String mailContent) { this.mailContent = mailContent; }

    public String getMailReceivedDate() { return mailReceivedDate; }
    public void setMailReceivedDate(String mailReceivedDate) { this.mailReceivedDate = mailReceivedDate; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectEmployeeName() { return projectEmployeeName; }
    public void setProjectEmployeeName(String projectEmployeeName) { this.projectEmployeeName = projectEmployeeName; }

    public String getProjectClientName() { return projectClientName; }
    public void setProjectClientName(String projectClientName) { this.projectClientName = projectClientName; }
}
