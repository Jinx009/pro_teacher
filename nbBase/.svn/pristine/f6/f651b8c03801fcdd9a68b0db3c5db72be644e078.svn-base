package nbBase.dao.common;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaFrontWxConfig;

@Transactional
@Repository("wxConfigDao")
public class _WxConfigDaoImpl extends BaseDaoImpl<ZaFrontWxConfig> implements _WxConfigDao{

	@Override
	public ZaFrontWxConfig findByAppId(String appId) {
		
		if( appId == null )
			return null;
		
		String hql = "select a from ZaFrontWxConfig a where a.wxappid=:appId";
		
		Query query = em.createQuery(hql);
		query.setParameter("appId", appId);
		
		@SuppressWarnings({"unchecked" })
		List<ZaFrontWxConfig> resultList = query.getResultList();
		
		if( resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		
		return null;
	}

	@Override
	public ZaFrontWxConfig findByEventId(Integer eventId) {
		if( eventId == null )
			return null;
		
		String hql = "select a from ZaFrontWxConfig a, ZaCoreEvent b where a.id=b.wxConfigId and b.id=:eventId";
		
		Query query = em.createQuery(hql);
		query.setParameter("eventId", eventId);
		
		@SuppressWarnings({"unchecked" })
		List<ZaFrontWxConfig> resultList = query.getResultList();
		
		if( resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		
		return null;
	}

	@Override
	public ZaFrontWxConfig findByDefault() {
		
		String hql = "select a from ZaFrontWxConfig a where a.isDefault=true and a.isActive=true";
		
		Query query = em.createQuery(hql);
		
		@SuppressWarnings({"unchecked" })
		List<ZaFrontWxConfig> resultList = query.getResultList();
		
		if( resultList != null && resultList.size() > 0){
			return resultList.get(resultList.size()-1);
		}
		
		return null;
	}

	@Override
	public ZaFrontWxConfig updateCurrentPageToken(int configId, String pageToken, Long pageTokenExpire) {
		
		ZaFrontWxConfig wxc = this.find(configId);
		if( wxc != null){
			wxc.setCurrentPageToken(pageToken);
			wxc.setCurrentPageTokenExpire(pageTokenExpire);
			wxc = this.update(wxc);
		}
		
		return wxc;
	}

}
