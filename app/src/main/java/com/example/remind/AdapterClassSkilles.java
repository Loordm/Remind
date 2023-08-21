package com.example.remind;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterClassSkilles extends BaseAdapter  {

    Context context;
    ArrayList<String> table;
    Boolean delete = false;
    Boolean stop = false;
    Boolean copy = false;
    Boolean modify = false;
    String sharedFileName ;
    TextView ilghaa ;
    TextView indik ;
    public AdapterClassSkilles(Context context, ArrayList<String> table , String sharedFileName) {
        this.context = context;
        this.table = table;
        this.sharedFileName = sharedFileName ;
    }
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
    public void setStop(Boolean stop) {
        this.stop = stop;
    }
    public void setCopy(Boolean copy) { this.copy = copy; }
    public void setModify(Boolean modify) { this.modify = modify; }
    public void setIlghaa(TextView ilghaa) { this.ilghaa = ilghaa; }
    public void setIndik(TextView indik) { this.indik = indik; }

    @Override
    public int getCount() {
        return table.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(context).inflate(R.layout.list_view_skilles, parent, false);
        // ************************************** Show data **************************************
        TextView description = convertView.findViewById(R.id.writeyourwork2);
        description.setText(table.get(position));
        // ************************************** End Show data ***********************************

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(sharedFileName , 0);
                SharedPreferences.Editor editor = sharedPreferences.edit() ;
                if (delete) {
                    editor.remove(table.get(position)) ;
                    editor.apply();
                    table.remove(position);
                    notifyDataSetChanged();
                    Snackbar.make(v, "تم الحذف بنجاح", Snackbar.LENGTH_LONG).show() ;
                    if (stop || table.isEmpty()) {
                        ilghaa.setText("");
                        indik.setText("");
                        setDelete(false);
                        setStop(false);
                    }
                }
                if (copy) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE) ;
                    ClipData clip = ClipData.newPlainText("copy" , table.get(position)) ;
                    clipboard.setPrimaryClip(clip);
                    setCopy(false);
                    Toast.makeText(context.getApplicationContext(),"تم نسخ النص بنجاح" , Toast.LENGTH_SHORT).show();
                }
                if (modify) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()) ;
                    View view = View.inflate(v.getContext() , R.layout.modify , null) ;
                    EditText edit = view.findViewById(R.id.texttomodify) ;
                    Button btn1 , btn2 ;
                    btn1 = view.findViewById(R.id.domodify) ;
                    btn2 = view.findViewById(R.id.backmodify) ;
                    builder.setView(view) ;
                    AlertDialog dialog = builder.create() ;
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(false);
                    edit.setText(table.get(position));
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            setModify(false);
                        }
                    });
                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!edit.getText().toString().isEmpty()) {
                                editor.remove(table.get(position));
                                editor.apply();
                                table.set(position, edit.getText().toString());
                                notifyDataSetChanged();
                                editor.putString(table.get(position), table.get(position));
                                editor.apply();
                                dialog.dismiss();
                                setModify(false);
                            }else {
                                edit.setError("يرجى كتابة النص");
                            }
                        }
                    });
                }
            }
        });
        return convertView;
    }

}
