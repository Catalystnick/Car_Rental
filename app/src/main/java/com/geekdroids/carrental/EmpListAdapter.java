package com.geekdroids.carrental;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class EmpListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Employee> employeeList;
    public  EmpListAdapter(Activity mContext,List<Employee> employeeList){
        super(mContext,R.layout.activity_employee_list,employeeList);
        this.mContext = mContext;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_employee_list,null,true);

        TextView empName = listItemView.findViewById(R.id.empName);

        Employee employee = employeeList.get(position);

        empName.setText(employee.getName());

        return listItemView;

    }
}
