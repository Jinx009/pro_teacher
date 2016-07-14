package nbBase.service.common;

public interface If_ErrorCheckService {

	public Integer getLastErrCode();
	public String getLastErrMsg();
	
	public void SetLastError(Integer code, String msg);
	
}
