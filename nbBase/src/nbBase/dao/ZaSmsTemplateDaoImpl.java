package nbBase.dao;

import org.springframework.stereotype.Repository;

import nbBase.database.common.BaseDaoImpl;
import nbBase.database.models.ZaSmsTemplate;


@Repository("smsTemplateDao")
public class ZaSmsTemplateDaoImpl extends BaseDaoImpl<ZaSmsTemplate> implements ZaSmsTemplateDao{

}
