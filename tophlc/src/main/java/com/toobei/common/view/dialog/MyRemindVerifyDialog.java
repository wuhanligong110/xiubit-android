//package com.toobei.common.view.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.toobei.common.R;
//
//public class MyRemindVerifyDialog extends Dialog implements android.view.View.OnClickListener {
//	private Activity aty;
//	private String title;
//	private ImageView closedIv;
//	private Button saveBtn;
//	private Class cls;
//	private TextView phoneTv;
//
//	public MyRemindVerifyDialog(Activity aty, String title, Class cls) {
//		super(aty, R.style.customDialog);
//		this.aty = aty;
//		this.title = title;
//		this.cls = cls;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		View view = LayoutInflater.from(aty).inflate(R.layout.my_remindverify, null);
//		this.setContentView(view);
//		closedIv = (ImageView) findViewById(R.id.closedIv);
//		saveBtn = (Button) findViewById(R.id.saveBtn);
//		//qrIv = (RemoteImageView) findViewById(R.id.qrIv);
//		phoneTv = (TextView) findViewById(R.id.phoneTv);
//		phoneTv.setText(title);
//		//		qrIv.setImageUri(url);
//		closedIv.setOnClickListener(this);
//		saveBtn.setOnClickListener(this);
//	}
//
//	public void setBtnBgResourceId(int resourceId) {
//		saveBtn.setBackgroundResource(resourceId);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		if (arg0.getId() == R.id.closedIv) {
//
//		} else if (arg0.getId() == R.id.saveBtn) {
//			Intent in=new Intent(aty, cls);
//			aty.startActivity(in);
//		}
//		dismiss();
//	}
//}