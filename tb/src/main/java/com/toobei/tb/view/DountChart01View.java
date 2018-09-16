package com.toobei.tb.view;


public class DountChart01View  {
/*public class DountChart01View  extends DemoView{

	private String TAG = "DountChart01View";
	private DountChart chart = new DountChart();
	private float commission = 88.08f, teamCommission = 123.12f, rewards = 48.8f;

	LinkedList<PieData> lPieData = new LinkedList<PieData>();

	public DountChart01View(Context context) {
		super(context);
		initView();
	}

	public DountChart01View(Context context, float commision, float teamCommision, float rewards) {
		super(context);
		this.commission = commision;
		this.teamCommission = teamCommision;
		this.rewards = rewards;
		initView();
	}

	public DountChart01View(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public DountChart01View(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		chartDataSet();
		chartRender();

		//綁定手势滑动事件
		//	this.bindTouch(this, chart);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//图所占范围大小
		chart.setChartRange(w, h);
	}

	protected int[] getPieDefaultSpadding() {
		int[] ltrb = new int[4];
		ltrb[0] = DensityUtil.dip2px(getContext(), 0); //left	
		ltrb[1] = DensityUtil.dip2px(getContext(), 10); //top	
		ltrb[2] = DensityUtil.dip2px(getContext(), 0); //right		
		ltrb[3] = DensityUtil.dip2px(getContext(), 10); //bottom						
		return ltrb;
	}

	private void chartRender() {
		try {
			//设置绘图区默认缩进px值
			int[] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

			//数据源
			chart.setDataSource(lPieData);
			//	chart.setCenterText("新品太多!!!");
			//	chart.getCenterTextPaint().setColor(Color.rgb(242, 167, 69));

			//标签显示(隐藏，显示在中间，显示在扇区外面) 
			chart.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
			chart.getLabelPaint().setColor(Color.WHITE);
			//标题
			//	chart.setShareTitle("环形图");
			//	chart.addSubtitle("(XCL-Charts Demo)");
			//显示key
			//	chart.getPlotLegend().show();		
			//显示图例
			PlotLegend legend = chart.getPlotLegend();
			legend.show();
			legend.setType(XEnum.LegendType.COLUMN);
			legend.setRowSpan(PixelUtil.dip2px(getContext(), 6));
			legend.setHorizontalAlign(XEnum.HorizontalAlign.RIGHT);
			legend.setVerticalAlign(XEnum.VerticalAlign.MIDDLE);
			//legend.setLabelMargin(20);
			legend.showBox();
			legend.getBox().setBorderRectType(XEnum.RectType.ROUNDRECT);
			legend.setOffsetX(PixelUtil.dip2px(getContext(), 40));
			//图背景色
			//	chart.setApplyBackgroundColor(true);
			//	chart.setBackgroundColor(Color.rgb(19, 163, 224));

			//内环背景色
			//	chart.getInnerPaint().setColor(Color.rgb(19, 163, 224));

			//显示边框线，并设置其颜色
			//	chart.getArcBorderPaint().setColor(Color.YELLOW);

			//可用这个修改环所占比例
			chart.setInnerRadius(0.4f);

			chart.setInitialAngle(90.f);
			//设置附加信息
			//	addAttrInfo();

			//保存标签位置
			chart.saveLabelsPosition(XEnum.LabelSaveType.ALL);

			//激活点击监听
			//	chart.ActiveListenItemClick();
			//	chart.showClikedFocus();

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	private void chartDataSet() {
		//设置图表数据源				
		//PieData(标签，百分比，在饼图中对应的颜色)
		Resources re = getContext().getResources();
		float num = commission + teamCommission + rewards;
		lPieData.add(new PieData(re.getString(R.string.commission), String.format("%.2f",
				commission), commission * 100 / num, Color.rgb(77, 83, 97)));
		lPieData.add(new PieData(re.getString(R.string.team_commission), String.format("%.2f",
				teamCommission), teamCommission * 100 / num, Color.rgb(148, 159, 181)));
		lPieData.add(new PieData(re.getString(R.string.rewards), String.format("%.2f", rewards),
				rewards * 100 / num, Color.rgb(253, 180, 90)));
	}

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP) {
			triggerClick(event.getX(), event.getY());
		}
		return true;
	}

	//触发监听
	private void triggerClick(float x, float y) {
		if (!chart.getListenItemClickStatus())
			return;
		ArcPosition record = chart.getPositionRecord(x, y);
		if (null == record)
			return;

		PieData pData = lPieData.get(record.getDataID());

		boolean isInvaldate = true;
		for (int i = 0; i < lPieData.size(); i++) {
			PieData cData = lPieData.get(i);
			if (i == record.getDataID()) {
				if (cData.getSelected()) {
					isInvaldate = false;
					break;
				} else {
					cData.setSelected(true);
				}
			} else
				cData.setSelected(false);
		}

		if (isInvaldate)
			this.invalidate();
	}
*/
}
