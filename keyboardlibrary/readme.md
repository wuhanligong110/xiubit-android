改进自： https://github.com/lpy19930103/AndroidKeyBoard
在以上开源库基础上做了如下改动：
1. 屏蔽目标EditText唤起系统软键盘，并且在弹出键盘时将其他系统软键盘收起
2. 新增了身份证输入键盘
基本使用示例：
```
keyBoard = new SecureKeyBoard(this,
                new KeyboardParams(this, KeyboardType.IDCARD), cedtIdentityCard);
                                
cedtIdentityCard.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                keyBoard.show();
                                return false;
                            }
                        });
                        
cedtIdentityCard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                    @Override
                                    public void onFocusChange(View v, boolean hasFocus) {
                                        if (!hasFocus) {
                                            keyBoard.dismiss();
                                        }
                        
                                    }
                                });
                                
                             
```
另外 ：KeyboardParams中还可以进行一些其他配置：比如是否开启乱序和密码输入