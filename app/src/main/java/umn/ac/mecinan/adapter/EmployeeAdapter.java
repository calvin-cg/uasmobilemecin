package umn.ac.mecinan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{

    private Context mCtx;
    private List<User> employeeList;
    private boolean myRequest;

    public EmployeeAdapter(Context mCtx, List<User> employeeList){
        this.mCtx = mCtx;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.employee_list, null);
        EmployeeAdapter.EmployeeViewHolder holder = new EmployeeAdapter.EmployeeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder employeeViewHolder, int i) {
        User employee = employeeList.get(i);

        employeeViewHolder.employeeName.setText(employee.getUsername());
        employeeViewHolder.employeeField.setText(employee.getTagline());
        employeeViewHolder.employeeRatingBar.setRating(5);
        employeeViewHolder.employeeRate.setText(employee.getPhoneNumber());
        employeeViewHolder.employeeCompletedProject.setText(employee.getEmail());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView employeeName, employeeField, employeeRate, employeeCompletedProject;
        RatingBar employeeRatingBar;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeeField = itemView.findViewById(R.id.employeeField);
            employeeRate = itemView.findViewById(R.id.employeeRate);
            employeeRatingBar = itemView.findViewById(R.id.employeeRatingBar);
            employeeCompletedProject = itemView.findViewById(R.id.employeeCompletedProject);
        }
    }
}
