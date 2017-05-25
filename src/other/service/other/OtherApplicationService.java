package other.service.other;

import java.util.Date;

import nbBase.helper.common.nbReturn;

public interface OtherApplicationService {

	nbReturn createNewStatusRecord(
			Date currentDate, 
			Integer client, 
			String picUrl, 
			Boolean weiboShared);

	nbReturn updateStatusRecordWeiboShareResult(String picCode, boolean b);

	nbReturn updateWeixinShared(String picUrl, Boolean weixinShared);

	nbReturn updateWeixinSharedResult(String picUrl, Boolean weixinSharedResult);



	
}
