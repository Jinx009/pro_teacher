package nbBase.database.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHQLStringBuilder {

	public enum OrderType{
		DESC, ASC,
	};
	
	private String getOrderString(OrderType ot){
		if( ot == OrderType.DESC )
			return "desc";
		if( ot == OrderType.ASC )
			return "asc";
		return "";
	}
	private String actionString;
	private List<String> conditions = new ArrayList<String>();
	private List<String> or_conditions = new ArrayList<String>();
	private List<String> joinTables = new ArrayList<String>();
	private List<String> groupBy = new ArrayList<String>();
	private List<Map<String,Object>> orderBy = new ArrayList<Map<String,Object>>();

	
	public String getActionString() {
		return actionString;
	}
	
	public void setActionString(String actionString) {
		this.actionString = actionString;
	}
	
	public void addCondition(String oneCondition){
		if( conditions == null ){
			conditions = new ArrayList<String>();
		}
		
		conditions.add(oneCondition);
	}
	
	private boolean hasWhereClause(StringBuilder sb){
		if(sb.indexOf(" where ") > 0 )
			return true;
		return false;
	}
	
	public String getFullHqlString(){
		StringBuilder sb = new StringBuilder();
		sb.append(actionString);
		
		boolean isFirst = true;
		for(String sentence : joinTables){
			if( isFirst )
				sb.append(" join");
			sb.append(" "+sentence);
		}
		
		if( conditions != null && conditions.size() > 0 ){
			isFirst = !hasWhereClause(sb);
			for( String con: conditions){
				if( isFirst ){
					sb.append(" where ");
					isFirst = false;
				}else{
					sb.append(" and ");
				}
				sb.append(con);
			}
		}
		
		if( or_conditions != null && or_conditions.size() > 0 ){
			isFirst = !hasWhereClause(sb);
			for( String con: or_conditions){
				if( isFirst ){
					sb.append(" where ");
					isFirst = false;
				}else{
					sb.append(" or ");
				}
				sb.append(con);
			}
		}
		
		if( groupBy != null && groupBy.size() > 0){
			sb.append(" group by ");
			boolean first = true;
			for(String con : groupBy){
				if( !first ){
					sb.append(" ,");
				}
				first = false;
				sb.append(con);
			}
		}
		
		if( orderBy != null && orderBy.size() > 0){
			sb.append(" order by");
			boolean first = true;
			for( Map<String,Object> order : orderBy){
				if( !first )
					sb.append(" ,");
				sb.append(" "+order.get("key").toString() + " " +this.getOrderString((OrderType) order.get("ot")) );
				first = false;
			}
		}
		
		return sb.toString();
	}

	public void addJoinTable(String joinTableSentence) {
		if( joinTables == null ){
			joinTables = new ArrayList<String>();
		}
		joinTables.add(joinTableSentence);
	}

	public void addGroupBy(String string) {
		groupBy.add(string);
	}
	
	public void addOrder(OrderType ot, String key){
		Map<String, Object> newOne = new HashMap<String, Object>();
		newOne.put("ot", ot);
		newOne.put("key", key);
		orderBy.add(newOne);
	}

	public void addOrCondition(String oneCondition) {
		if( or_conditions == null ){
			or_conditions = new ArrayList<String>();
		}
		
		or_conditions.add(oneCondition);
	}
	
}
