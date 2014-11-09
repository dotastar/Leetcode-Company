import java.util.HashMap;
import java.util.Map.Entry;

import data.MappingDataManager;


public class Mapping {
	private String sql;
	private String tname;
	private String[] snames;
	private String[] condition;
	private HashMap<String, String> attMap;		//view as key, origin as value
	
	private final static String insertAttrTemplate = "INSERT INTO \"TEST\".\"CORRESPOND\" (TARGET, SOURCE) VALUES";
	private final static String insertConditionTemplate = "INSERT INTO \"TEST\".\"METADATA\" (K, V1, V2) VALUES";
	
	public static void main(String[] args){
		System.out.println("test Mapping:");
		
		//String sql1 = "create view student as select takes.sid, takes.major from takes";
		//String sql2 = "create view takes__(sid,major,cid) as select takes.sid, takes.major, takes.rowid from takes";
		String sql3 = "create view takes_(sid,major,course,grade) as select student.sid, student.major, enrolled.course, student.rowid from student, enrolled where student.sid=enrolled.sid";
		
		try {
			Mapping mapping = new Mapping(sql3);
			mapping.saveIntoDB();
			String str = "tt.a=yy.b, qq.c>ww.d";
			String[] ss = str.split("=|<|>|,|!=");
			System.out.println("\nend!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Mapping(String sqlIn) throws Exception{
		if(sqlIn.length()==0){
			error("invalid sql!");
		}
		setSql(sqlIn.trim());
		parseTName(getSql());
		parseSNames(getSql());
		parseAtt(getSql());
	}

	
	/**
	 * get TableName from a full name (TableName.AttrName)
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public static String getAttTName(String attr) throws Exception{
		String[] s = attr.split("\\.");
		if(s.length==0)
			throw new Exception("Wrong input:"+attr);
		return s[0];
	}
	
	
	/**
	 * get AttrName from a full name (TableName.AttrName)
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public static String getAttName(String attr) throws Exception{
		String[] s = attr.split("\\.");
		if(s.length<2)
			throw new Exception("Wrong input:"+attr);
		return s[1];
	}
	
	
	public void saveIntoDB(){
		//save into DB
		MappingDataManager.getInstance().executeSQLs(getSql());
		insertAttrs(getAttMap());
		insertCondition();
	}
	
	public String getTname() {
		return tname;
	}

	public String getSql() {
		return sql;
	}

	public String[] getSnames() {
		return snames;
	}

	public String[] getCondition() {
		return condition;
	}
	
	public String getConditionString(){
		String[] cond = getCondition();
		if(cond.length==0)
			return "";
		else
			return cond[cond.length-1];
	}

	public HashMap<String, String> getAttMap() {
		return attMap;
	}
	
	public void outputAtts(){
		System.out.println("attributes: T<-S");
		for(Entry<String,String> entry : getAttMap().entrySet()){
			System.out.println(entry.getKey()+"<-"+entry.getValue());
		}
	}

	/**
	 * insert attributes, mapped attributes into correspond table
	 * @param attrMap
	 * @return
	 */
	private boolean insertAttrs(HashMap<String, String> attrMap){
		StringBuilder sb = new StringBuilder("");
		for(Entry<String, String> entry : attrMap.entrySet()){
			sb.append(insertAttrTemplate);
			sb.append("('" + entry.getKey().trim() + "','" + entry.getValue().trim() + "')\n");
		}
		
		//System.out.println(sb.toString());
		return MappingDataManager.getInstance().executeSQLs(sb.toString().split("\n"));	
	}
	
	
	/**
	 * insert condition into metadata table
	 * @return
	 */
	private boolean insertCondition(){
		String[] cond = getCondition();
		if(cond.length==0) return true;
		
		StringBuilder sb = new StringBuilder(insertConditionTemplate);
		sb.append("('" + getTname()+"'");
		sb.append(",'"+cond[0]);	//('tname','attr0
		
		for(int i=1; i<cond.length-1; i++){
			sb.append(","+cond[i]);
		}
		sb.append("','"+cond[cond.length-1]+"')");
		return MappingDataManager.getInstance().executeSQLs(sb.toString());	
	}
	
	/**
	 * parse source names and condition from a mapping
	 * @param sqlIn
	 * @throws Exception
	 */
	private void parseSNames(String sqlIn) throws Exception{
		int begin = sqlIn.indexOf("from ");
		int where = sqlIn.indexOf("where ");
		if(where<0){
			where = sqlIn.length();
			setCondition(new String[0]);
		}else{
			parseCondition(sqlIn.substring(where+6, sqlIn.length()).trim());
		}
			
		setSnames(sqlIn.substring(begin+5, where).trim().split(","));
		
		//System.out.println(sqlIn.substring(begin, end)+","+snames.length);
	}
	
	/**
	 * parse condition
	 * @param afterWhere
	 * @throws Exception
	 */
	private void parseCondition(String afterWhere) throws Exception{
		String[] atts = afterWhere.split(">=|!=|<=|=|>|<|,");	
		if(atts.length==1&&afterWhere.length()>0)
			throw new Exception();
		else{
			String[] parsedCond = new String[atts.length+1];
			for(int i=0; i<atts.length; i++)
				parsedCond[i] = atts[i];
			parsedCond[atts.length] = afterWhere; 
			setCondition(parsedCond);
		}
	}
	
	/**
	 * parse target name from a mapping
	 * @param sqlIn
	 * @throws Exception
	 */
	private void parseTName(String sqlIn) throws Exception{
		int begin = sqlIn.indexOf("create view ");
		int end = sqlIn.indexOf(" as");
		if(begin<0||end<0||begin>end)	error("parse error!");
		
		//parse view name
		begin += 12;
		String viewName = sqlIn.substring(begin, end).trim();
		//System.out.println(viewName);
		int bracket = viewName.indexOf("(");
		if(bracket>0){
			end = bracket;
			setTname(viewName.substring(0, end).trim());
		}else
			setTname(viewName);
		//System.out.println(tname);
	}
	
	
	/**
	 * parse attributes, mapped attributes from an sql 
	 * from attsO -> attsV
	 * @param sqlIn
	 * @throws Exception
	 */
	private void parseAtt(String sqlIn) throws Exception{
		int begin = sqlIn.indexOf(" as select ");
		int bracket = sqlIn.indexOf("(");
		int end = sqlIn.indexOf(" from");
		if(begin<0||end<0||begin>end)	error("parse error!");
		
		String[] attsOrigin;
		String atts = sqlIn.substring(begin+11, end).trim();
		//System.out.println(atts);
		attsOrigin = atts.split(",");	//source
		
		int n = attsOrigin.length;
		
		String[] attsView;		//target
		if(bracket>0&&bracket<begin){
			atts = sqlIn.substring(bracket+1,begin-1).trim();
			attsView = atts.split(",");
			for(int i=0; i<n; i++){
				attsView[i] = getTname()+"."+attsView[i].trim();
			}
		}else{
			attsView = new String[n];
			for(int i=0; i<n;i++){
				attsView[i] = getTname()+"."+getAttName(attsOrigin[i]).trim();
			}
		}
		
		setAttMap(new HashMap<String,String>(n));
		for(int i=0; i<n; i++){
			getAttMap().put(attsView[i].trim(), attsOrigin[i].trim());
		}
		//outputAtts();
	}

	
	private void error(String msg) throws Exception{
		//Thread.currentThread().getStackTrace().toString();
		System.out.println("Error:"+msg);
		throw new Exception("Error:"+msg);
	}

	
	private void setSql(String sql) {
		this.sql = sql;
	}

	private void setTname(String tname) {
		this.tname = tname;
	}
	
	private void setSnames(String[] snamesIn) {
		this.snames = new String[snamesIn.length];
		for(int i=0; i<snamesIn.length; i++){
			this.snames[i] = snamesIn[i].trim();
		}
	}
	
	private void setCondition(String[] condition) {
		this.condition = condition;
	}

	private void setAttMap(HashMap<String, String> attMap) {
		this.attMap = attMap;
	}
}
