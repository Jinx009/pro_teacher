package yuexiang.service;

import nbBase.database.models.ZaAdminUser;
import nbBase.helper.common.nbReturn;

public interface YxAdminService {

	nbReturn getBookListForAdmin(Integer startIndex, Integer pageSize, Boolean isWarehouse, Boolean isVerbalOnly);

	nbReturn getApprovalStatus(Integer bookId);

	nbReturn approve(Integer bookId, Integer approvelIndex, Boolean isUnApprove, ZaAdminUser adminUser);

	nbReturn setBestComment(Integer bookId, Integer parentCommentId, Integer commentId, Boolean isIncludeChild);

	nbReturn addVerbalToBook(Integer bookId, String verbalUrl);

	nbReturn getVerbalStatus(Integer bookId);

	nbReturn setWxUserExpert(Integer wxUserId, ZaAdminUser adminUser, Boolean isExpert);
	
	//nbReturn copyUserFeedToWarehouse(Integer bookUserFeedId, Boolean isOverwriteReferredItem);


}
