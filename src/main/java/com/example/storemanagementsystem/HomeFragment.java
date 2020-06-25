package com.example.storemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    Button b1,b2,b3,b4,b5,b6;
    private IntentIntegrator qrScan;
    Bundle bundle;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        b1 = view.findViewById(R.id.add);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Add.class);
                startActivity(intent);
            }
        });

        b2= view.findViewById(R.id.bill);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Bill.class);
                startActivity(intent);
            }
        });

        b3= view.findViewById(R.id.view);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),DisplaySQLiteDataActivity.class);
                startActivity(intent);
            }
        });

        b4 = view.findViewById(R.id.scan);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan = new IntentIntegrator(getActivity());
                qrScan.forSupportFragment(HomeFragment.this).initiateScan();
            }
        });

        b5 = view.findViewById(R.id.dealers);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity().getApplicationContext(),Dealers.class);
                startActivity(intent);
            }
        });

        b6 = view.findViewById(R.id.account);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),myacc.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), result.getContents(), Toast.LENGTH_LONG).show();
                    String scanContent = result.getContents();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("\nContent:" +scanContent);
                    showMessage("Data",buffer.toString());
                    bundle = new Bundle();
                    bundle.putString("Stuff ", scanContent);
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void showMessage(String title,String Message)
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton( "Next", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                Intent i = new Intent(getActivity().getApplicationContext(),AddScan.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        builder.show();
        builder.create().show();
    }
}
