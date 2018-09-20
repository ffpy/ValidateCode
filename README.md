## 下载
- [validatecode-binary-0.1.jar](https://raw.githubusercontent.com/ffpy/ValidateCode/master/downloads/validatecode-binary-0.1.jar)
- [validatecode-source-0.1.jar](https://raw.githubusercontent.com/ffpy/ValidateCode/master/downloads/validatecode-source-0.1.jar)

## 更改全局设置
```
// 设置默认验证码位数
ValidateCodeConfig.setDefaultCodeBits(4);
// 设置默认发送间隔
ValidateCodeConfig.setDefaultSendInterval(TimeUnit.SECONDS.toMillis(60));
// 设置默认过期时间
ValidateCodeConfig.setDefaultExpireTime(TimeUnit.MINUTES.toMillis(5));
// 设置默认验证码生成器
ValidateCodeConfig.setDefaultGenerator(LetterDigitValidateCodeGenerator.getInstance());
```

## SpringMVC登录示例
### 发送短信验证码到指定手机号
```
@RequestMapping(value = "/create_login_code", method = RequestMethod.GET)
@ResponseBody
public Message createLoginCode(String phone, HttpSession session) {
	// TODO 检查手机号格式
	// 检测发送间隔
	ValidateCode code = SessionValidateCodeStore.getInstance()
		.getCode(session, "login");
	if (!ValidateCodes.checkSendInterval(code))
		return new Message(-1, "发送间隔过短，请稍候发送！");
	// TODO 检查手机号是否已注册
	// 生成验证码
	code = ValidateCodes.createCode(phone);
	// TODO 发送验证码
	// 保存验证码
	SmsCodeUtils.saveCode(code, session);
	// 发送成功
	return new Message(200, "发送成功。");
}
```

### 登录
```
@RequestMapping(value = "/login", method = RequestMethod.POST)
@ResponseBody
public Message login(String phone, String code, HttpSession session) {
	// 读取验证码
	ValidateCode expectedCode = SessionValidateCodeStore.getInstance()
		.getCode(session, "login");
	// 校验验证码
	ValidateCodeErrorCode errorCode = ValidateCodes.validate(phone, code, expectedCode);
	if (errorCode != null)
		return new Message(-1, errorCode.getMessage());
	// TODO 登录操作
	// 清除验证码
	SessionValidateCodeStore.getInstance().removeCode(session, "login");
	// 登录成功
	return new Message(200, "登录成功。");
}
```

## License
ValidateCode is licensed under the Apache License, Version 2.0 