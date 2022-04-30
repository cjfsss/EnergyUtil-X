package hos.demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import hos.thread.hander.MH;
import hos.util.dialog.OnItemDialogListener;
import hos.util.listener.OnTargetListener;
import hos.utilx.dialog.ChoiceDialog;
import hos.utilx.dialog.ProgressDialog;
import hos.utilx.dialog.ProgressHorizontalDialog;
import hos.utilx.dialog.TitleDialog;
import hos.utilx.dialog.TitleDialogFragment;

public class MainActivity extends AppCompatActivity {

    private MaterialButton title;
    private MaterialButton single;
    private MaterialButton multiChoice;
    private MaterialButton progressDialog;
    private MaterialButton progressHorizontalDialog;
    private ProgressDialog progressDialog1;
    private ProgressHorizontalDialog progressDialog2;
    private MaterialButton titleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        title = (MaterialButton) findViewById(R.id.title);
        single = (MaterialButton) findViewById(R.id.single);
        multiChoice = (MaterialButton) findViewById(R.id.multiChoice);
        progressDialog = (MaterialButton) findViewById(R.id.progressDialog);
        progressHorizontalDialog = (MaterialButton) findViewById(R.id.progressHorizontalDialog);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TitleDialog(MainActivity.this)
                        .setTitle("温馨提示")
                        .setContent("这是一场背景")
                        .setConfirm("确定", new OnTargetListener<TitleDialog>() {
                            @Override
                            public void onTarget(@NonNull TitleDialog titleDialog) {

                            }
                        }).show();
            }
        });
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChoiceDialog(MainActivity.this)
                        .setTitle("温馨提示")
                        .setItem(new String[]{"张三", "李四"})
                        .applyBottom()
                        .setOnItemDialogListener(new OnItemDialogListener<ChoiceDialog>() {
                            @Override
                            public void onItemSelected(ChoiceDialog target, Object item, int position, boolean isChecked) {

                            }
                        })
                        .showSingle();

            }
        });
        multiChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChoiceDialog(MainActivity.this)
                        .setTitle("温馨提示")
                        .setItem(new CharSequence[]{"张三", "李四", "王五"})
                        .applyBottom()
                        .showMulti();

            }
        });
        progressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog1 = new ProgressDialog(MainActivity.this);
                progressDialog1.setContent("加载中...");
                progressDialog1.showTip(2000);
            }
        });
        progressHorizontalDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog2 = new ProgressHorizontalDialog(MainActivity.this);
                progressDialog2.show();
                progress(1);
            }
        });
        titleFragment = (MaterialButton) findViewById(R.id.titleFragment);
        titleFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TitleDialogFragment()
                        .setTitle("温馨提示")
                        .setContent("这是一场背景")
                        .setConfirm("确定", new OnTargetListener<TitleDialogFragment>() {
                            @Override
                            public void onTarget(@NonNull TitleDialogFragment titleDialogFragment) {

                            }
                        }).show(getSupportFragmentManager());
            }
        });
    }

    private void progress(int progress) {
        MH.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress == 101) {
                    progressDialog2.dismiss();
                    return;
                }
                progressDialog2.setProgress(progress);
                progress(progress + 1);
            }
        }, 1000);
    }
}