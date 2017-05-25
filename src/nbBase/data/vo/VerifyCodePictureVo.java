package nbBase.data.vo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class VerifyCodePictureVo implements Serializable {

	private static final long serialVersionUID = -3771186913072823865L;
	
	private String verifyCode;
	private BufferedImage buffImg;
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public BufferedImage getBuffImg() {
		return buffImg;
	}
	public void setBuffImg(BufferedImage buffImg) {
		this.buffImg = buffImg;
	}
}
