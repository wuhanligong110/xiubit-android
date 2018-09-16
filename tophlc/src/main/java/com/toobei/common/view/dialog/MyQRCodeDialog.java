//package com.toobei.common.view.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.toobei.common.R;
//import com.toobei.common.utils.PhotoUtil;
//import com.toobei.common.utils.ToastUtil;
//import com.toobei.common.view.RemoteImageView;
//
//public class MyQRCodeDialog extends Dialog implements android.view.View.OnClickListener {
//	private Context context;
//	private String url;
//	private ImageView closedIv;
//	private Button saveBtn;
//	private RemoteImageView qrIv;
//
//
//	public MyQRCodeDialog(Context context,String url) {
//		super(context, R.style.customDialog);
//		this.context = context;
//		this.url = url;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		View view = LayoutInflater.from(context).inflate(
//				R.layout.my_qr_code, null);
//		this.setContentView(view);
//		closedIv = (ImageView) findViewById(R.id.closedIv);
//		saveBtn = (Button) findViewById(R.id.saveBtn);
//		qrIv = (RemoteImageView) findViewById(R.id.qrIv);
//
//		qrIv.setImageUri(url);
//		closedIv.setOnClickListener(this);
//		saveBtn.setOnClickListener(this);
//	}
//	
//	
//	public void setBtnBgResourceId(int resourceId){
//		saveBtn.setBackgroundResource(resourceId);
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		if(arg0.getId()==R.id.closedIv){
//			
//		}else if(arg0.getId()==R.id.saveBtn){
//			Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
//			if (bitmap != null) {
//				Bitmap bitmap2 = PhotoUtil.getViewBitmap(qrIv);
//				MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap2, "title", "description");
//				ToastUtil.showCustomToast("亲，您的二维码已经保存至相册~");
//				
//
//			} else {
//				ToastUtil.showCustomToast("亲，二维码图片还未加载成功，请稍后~");
//			}
//		}
//		dismiss();
//	}
//}