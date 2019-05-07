package umn.ac.mecinan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.listener.OnGetEmployeeListener;
import umn.ac.mecinan.model.User;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> implements Filterable {

    private Context mCtx;
    private List<User> employeeList;
    private boolean myRequest;

    private List<User> searchList;
    private List<User> storedList;

    public EmployeeAdapter(Context mCtx, List<User> employeeList) {
        this.mCtx = mCtx;
        this.employeeList = this.searchList = this.storedList = employeeList;
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
        User employee = storedList.get(i);

        employeeViewHolder.employeeName.setText(employee.getUsername());
        employeeViewHolder.employeeField.setText(employee.getField());
        employeeViewHolder.employeeRatingBar.setRating(5);
        employeeViewHolder.employeeRate.setText(employee.getPhoneNumber());
        employeeViewHolder.employeeCompletedProject.setText(employee.getEmail());

    }

    @Override
    public int getItemCount() {
        return storedList.size();
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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                Log.d("charString", charString);
                if (charString.isEmpty()) {
                    searchList = storedList = employeeList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : employeeList) {
                        Log.d("user", row.getUsername());
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            Log.d("username ", "username sama");
                            filteredList.add(row);
                        }
                    }
                    searchList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchList = (ArrayList<User>) results.values;
                storedList = searchList;
                notifyDataSetChanged();
            }
        };

    }
}
