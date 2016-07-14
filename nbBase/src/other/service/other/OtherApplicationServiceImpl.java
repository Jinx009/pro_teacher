package other.service.other;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nbBase.helper.common.nbReturn;
import nbBase.helper.common.nbReturn.ReturnCode;
import other.dao.other.OtherEventCasio001Dao;
import other.database.other.OtherEventCasio001;



@Service("otherApplicationService")
public class OtherApplicationServiceImpl implements OtherApplicationService {
	
	@Autowired
	OtherEventCasio001Dao otherEventCasio001Dao;

	@Override
	public nbReturn createNewStatusRecord(
			Date currentDate, 
			Integer client, 
			String picUrl, 
			Boolean weiboShared) {
		
		nbReturn nbRet = new nbReturn();
		
		OtherEventCasio001 oec001  = new OtherEventCasio001();
		oec001.setDate(currentDate);
		oec001.setClient(client);
		oec001.setPicUrl(picUrl);
		oec001.setWeiboShared(weiboShared);
		
		oec001.setWeixinShared(false);
		oec001.setWeixinSharedResult(false);
		oec001.setWeiboSharedResult(false);
		
		oec001 = this.otherEventCasio001Dao.save(oec001);
		
		return nbRet;
	}

	@Override
	public nbReturn updateStatusRecordWeiboShareResult(String picCode, boolean b) {
		nbReturn nbRet = new nbReturn();
		
		OtherEventCasio001 oec001 = this.otherEventCasio001Dao.findByPicUrl(picCode);
		if( oec001 == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		oec001.setWeiboSharedResult(b);
		
		this.otherEventCasio001Dao.update(oec001);
		
		return nbRet;
	}

	@Override
	public nbReturn updateWeixinShared(String picUrl, Boolean weixinShared) {
		
		nbReturn nbRet = new nbReturn();
		
		OtherEventCasio001 oec001 = this.otherEventCasio001Dao.findByPicUrl(picUrl);
		if( oec001 == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		oec001.setWeixinShared(weixinShared);
		
		this.otherEventCasio001Dao.update(oec001);
		
		return nbRet;
	}

	@Override
	public nbReturn updateWeixinSharedResult(String picUrl, Boolean weixinSharedResult) {
		nbReturn nbRet = new nbReturn();
		
		OtherEventCasio001 oec001 = this.otherEventCasio001Dao.findByPicUrl(picUrl);
		if( oec001 == null ){
			nbRet.setError(ReturnCode.INSUFFICIENT_PARAMTERS);
			return nbRet;
		}
		
		oec001.setWeixinSharedResult(weixinSharedResult);
		
		this.otherEventCasio001Dao.update(oec001);
		
		return nbRet;
	}
	
	


}
