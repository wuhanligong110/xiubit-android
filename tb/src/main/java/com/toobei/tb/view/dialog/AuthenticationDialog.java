package com.toobei.tb.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.toobei.tb.R;
import com.toobei.tb.activity.CardManagerAdd;
import com.toobei.tb.util.C;

public class AuthenticationDialog extends Dialog implements android.view.View.OnClickListener {
	private Context context;
	private ImageView closedIv;
	private Button authenticationBtn;


	public AuthenticationDialog(Context context) {
		super(context, R.style.customDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(
				R.layout.authentication_dialog, null);
		this.setContentView(view);
		closedIv = (ImageView) findViewById(R.id.closedIv);
		authenticationBtn = (Button) findViewById(R.id.authenticationBtn);
		closedIv.setOnClickListener(this);
		authenticationBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId()==R.id.closedIv){
			
		}else if(arg0.getId()==R.id.authenticationBtn){
			Intent intent = new Intent(context, CardManagerAdd.class);
			((Activity) context).startActivityForResult(intent, C.REFRESH_DATA);
		}
		dismiss();
	}
}