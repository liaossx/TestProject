package com.gg.goodsclass6.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/

public class Verify  {
	
	
	
	
	public static  void   getVirify(HttpServletRequest request  , HttpServletResponse response) throws IOException{
		
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		VerifyCode.output(image, response.getOutputStream());
		request.getSession().setAttribute("vCode", vc.getText());
	}
	
}
