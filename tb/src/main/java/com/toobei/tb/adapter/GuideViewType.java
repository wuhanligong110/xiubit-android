package com.toobei.tb.adapter;

import com.toobei.tb.R;

public enum GuideViewType {
	MAINACTIVITY_GUIDE_SERVICE(R.drawable.img_guide_service), FRAGMENTMINE_GUIDE_QR(
			R.drawable.img_guide_service), FRAGMENTMINE_GUIDE_FACE(R.drawable.img_guide_service),FRAGMENTMINE_GUIDE_RED_PACKET(R.drawable.img_guide_my_redpacket),FRAGMENTMINE_GUIDE_MYINVESTORG(R.drawable.img_guide_my_investorg);
	//	Product(R.drawable.img_first_guide_product), FragmentInteraction(
	//			R.drawable.img_first_guide_interaction), FragmentMine(R.drawable.img_first_guide_mine);

	int value;

	GuideViewType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}