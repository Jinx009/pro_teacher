package yuexiang.service;

import java.util.List;
import java.util.Map;

import nbBase.database.models.ZaAdminUser;
import nbBase.database.models.ZaFrontUserWx;
import nbBase.helper.common.nbReturn;
import nbBase.service.wechat.WechatConfigure;

public interface YxApplicationService {

	nbReturn loadYuexiangWxConfig();

	/**
	 * 获取数据库已经存有的所有的tag
	 * @param isIncludeAdminTypeTags 是否包含只有admin才能设置的tag
	 * @param selected 是否只获取已经选择的tag,这个参数已选的tag的id的list
	 * @return
	 */
	nbReturn getAllTags(Boolean isIncludeAdminTypeTags, List<Integer> selected);

	nbReturn saveNewBookShare(Map<String, Object> bookInfo, ZaFrontUserWx wxUser, ZaAdminUser adminUser, WechatConfigure wxCon);

	nbReturn getBooksForListView(Integer startIndex, Integer pageSize, List<Integer> tagFilterList,
			String searchKeyWord, Boolean isVerbalOnly, Boolean forceVerbalTag);

	nbReturn createNewComment(Integer bookId, Integer topicId, Integer parentCommentId, List<Map<String, Object>> picList,
			String commentText, ZaFrontUserWx wxUser, ZaAdminUser operatorUser);

	nbReturn likeAComment(Integer commentId, Boolean isUnlike, ZaFrontUserWx wxUser);

	nbReturn getBookInfo(Integer bookId);

	nbReturn getBooksComments(Integer bookId, Integer startIndex, Integer pageSize, ZaFrontUserWx wxUser, Boolean isIncludeChild);

	nbReturn getBookCommentInfo(Integer commentId, ZaFrontUserWx wxUser, Boolean isIncludeChild);

	nbReturn deleteComment(Integer bookId, Integer topicId, Integer parentCommentId, Integer commentId, ZaFrontUserWx wxUser, ZaAdminUser adminUser);

	nbReturn getPromptKeywords(String inputedKeywords, Integer maxNumber, Boolean isWarehouse, Boolean isVerbalOnly, Boolean forceVerbalTag);

	nbReturn getTopicComments(Integer topicId, Integer startIndex, Integer pageSize, ZaFrontUserWx wxUser,
			Boolean isIncludeChild, Boolean isPublic);

	nbReturn getUserPointsInfo(ZaFrontUserWx wxUser);

	nbReturn getVerbalAritcles();

	nbReturn batchUpdateYuexiangBookData(List<List<String>> dataMatrix);

	nbReturn getYuexiangBookData();

}
