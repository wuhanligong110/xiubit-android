#app与H5交互接口定义，app中定义接口如下：
1.获取app来源，getAppResource();
	app返回给H5"android","ios";

2.获取app中的token,getAppToken();
	app返回数据 "token";

3.注销接口，跳转到登录界面,getAppLogOut();
	app无返回数据

4.获取app中的分享功能， getAppShareFunction(String str);
	H5传参为json格式
	例如：{"shareDesc":"理财师为你精心推荐的高收益产品，下手要快哦",
	"shareImgurl":"http://mchannel.xiaoniuapp.com/static/images/share_logo.png",
	"link":"http://minvestor.xiaoniuapp.com/pages/manage_finances/product_detail_credits.html?recommendCode=18898766534&productId=ca06ddf5-def3-11e5-be9a-000c29da3681",
	"shareTitle":"领会宝180天期"}
	app无返回数据
	
