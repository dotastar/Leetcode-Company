import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import data.MappingDataManager;




public class SchemaMapper {
	private final static String CreateCorrespond = "CREATE TABLE \"TEST\".\"CORRESPOND\" (\"TARGET\" VARCHAR2(40 BYTE) NOT NULL ENABLE, \"SOURCE\" VARCHAR2(40 BYTE) NOT NULL ENABLE)";
	private final static String CreateMetaData = "CREATE TABLE \"TEST\".\"METADATA\" (\"K\" VARCHAR2(120 BYTE) , \"V1\" VARCHAR2(120 BYTE), \"V2\" VARCHAR2(120 BYTE))";
	private final static String SelectColumnNames = "select COLUMN_NAME  from ALL_TAB_COLUMNS where  TABLE_NAME=";
	private static String  fileBaseAddr = "D:/Asia/Dropbox/Graduate/CSE636 Data Integration/project/InputExample";

	
	public static void main(String[] args) {
		System.out.println("Start");
		String s = " __1212_.asfd33_  =asdf12.fsdf14__ ";
		//System.out.println(s.matches("\\s*\\w+\\.\\w+\\s*=\\s*\\w+\\.\\w+\\s*"));
		
		//initialization
		//createTables(createCorrespond);
		//createTables(createMetaData);

		//clearViews(separateLines(dropSPrime_Views));
		String exampleNumb = "1";
		//read input data
		String addr1 = fileBaseAddr + exampleNumb + "/1.sql";
		String addr2 = fileBaseAddr + exampleNumb + "/2.sql";
		String addr3 = fileBaseAddr + exampleNumb + "/3.sql";
		//String addr4 = fileBaseAddr + exampleNumb + "/ClearView.sql";
		
		//composition
		String sql_st = readFile(addr1);
		String sql_ttp = readFile(addr2);		
		//inversion
		String sql_ssp = readFile(addr3);
		
		//String dropComposedViews = readFile(addr4);
		clearViews();
		
		try {
			List<Mapping> stMappings = constructST(sql_st);
			List<Mapping> ttpMappings = constructTTP(sql_ttp);
			List<Mapping> sspMappings = constructSSP(sql_ssp);
			
			compose(stMappings, ttpMappings);
			inverse(sspMappings);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("end.");
	}
	
	public static boolean verifyInversion(String sql, String originTName) throws Exception{
		Mapping ssp = new Mapping(sql);
		ssp.saveIntoDB();
		String verifySQL = "select * from " + originTName + " minus select * from " + ssp.getTname();
		List<String> res = MappingDataManager.getInstance().queryValue(verifySQL);
		if(res.size()==0)
			return true;
		else
			return false;
	}
	
	public static String inverse(List<Mapping> sspMappings) throws Exception{
		//generate new view name
		StringBuilder inverseSQL = new StringBuilder("create view ");
		inverseSQL.append(sspMappings.get(0).getSnames()[0] + "_inverse");
		
		//copy origin's schema to the new inverse schema
		String originTName = sspMappings.get(0).getSnames()[0];
		List<String> originAtts = getColumnNames(originTName.toUpperCase());
		inverseSQL.append('(');
		for(String origAtt : originAtts){
			inverseSQL.append(origAtt+",");
		}
		inverseSQL.deleteCharAt(inverseSQL.length()-1);
		inverseSQL.append(')');
		
		//mapping origin attributes
		inverseSQL.append(" as select ");
		//	generate new inverse attribute map
		Map<String, String> origin2viewMap = new HashMap<String, String>(); 
		for(Mapping sspMapping : sspMappings){
			for(Entry<String, String> entry : sspMapping.getAttMap().entrySet()){
				//view as key, origin as value reverse it
				//become origin as key, view as value
				origin2viewMap.put(entry.getValue(), entry.getKey());
			}
		}
		
		Set<String> newSNames = new HashSet<String>();
		//	mapping
		for(String originAtt : originAtts){
			String fullAtt = originTName + "." + originAtt;
			String mappedAtt = origin2viewMap.get(fullAtt);	//map
			if(mappedAtt==null)
				throw new Exception("The number of attribute does not match, this mapping cannot use inversion!");
			
			newSNames.add(Mapping.getAttTName(mappedAtt));
			inverseSQL.append(mappedAtt + ","); //append
		}
		inverseSQL.deleteCharAt(inverseSQL.length()-1);
		
		//	append newSNames
		inverseSQL.append(" from ");
		for(String sname : newSNames){
			inverseSQL.append(sname + ",");
		}
		inverseSQL.deleteCharAt(inverseSQL.length()-1);
		
		
		//more than one source prime schema exist, need to add joint addition
		if(sspMappings.size()!=1){
			//need a join condition
			inverseSQL.append(" where ");
			//find join attribute
			Set<String>[] joinSets = new Set[sspMappings.size()];
			for(int i=0; i<joinSets.length; i++)	//initialize array
				joinSets[i] = new HashSet<String>();
			
			int i = 0;
			//	separate attribute set
			for(Mapping sspMapping : sspMappings){
				Set<String> joinAttSet = joinSets[i];
				for(Entry<String,String> entry : sspMapping.getAttMap().entrySet()){
					String att = Mapping.getAttName(entry.getKey());
					joinAttSet.add(att);
				}
				i++;
			}
			// intersect these sets
			Set<String> jointSet = joinSets[0]; //joint attribute set
			for(Set<String> set : joinSets){
				jointSet.retainAll(set);
			}
			
			//******************** suppose we only have one joint attribute
			String jointAttr = jointSet.toArray()[0].toString();
			List<String> conditionAtts = new ArrayList<String>();
			for(String newSName : newSNames){
				conditionAtts.add(newSName + "." + jointAttr);
			}
			
			String firstJointAtt = conditionAtts.get(0);
			for(int j=1; j<conditionAtts.size(); j++){
				inverseSQL.append(firstJointAtt + "=" + conditionAtts.get(j) + " AND ");
			}
			inverseSQL.delete(inverseSQL.length()-4, inverseSQL.length());
		}
		
		String sql = inverseSQL.toString();
		System.out.println("inverse : "+ sql);
		
		if(verifyInversion(sql, originTName))
			return sql;
		else
			return "";
	}
	
	
	public static String[] compose(List<Mapping> stMappings, List<Mapping> ttpMappings) throws Exception{
		int size = ttpMappings.size();
		String[] outputs = new String[size];
		for(int i=0; i<size; i++){
			Mapping ttpMapping = ttpMappings.get(i);
			String stp = ttpMapping.getSql();
			int where = stp.indexOf("where");
			if(where>0)
				stp = stp.substring(0,where);
			
			Set<String> stSNames = new HashSet<String>();
			
			//replacing new names
			stp = stp.replace(ttpMapping.getTname(), ttpMapping.getTname()+"_composed");
			
			//replacing attributes
			for(String targetAttr : ttpMapping.getAttMap().values()){	//origin attribute of a ttp mapping which means it's an attribute in target schema 
				if(Mapping.getAttName(targetAttr).equals("rowid")){	//the attribute is out of current table
					String newRowid = targetAttr.replace(Mapping.getAttTName(targetAttr), stMappings.get(0).getSnames()[0]);	//***************** use the first mapping's column as rowid *******************
					stp = stp.replace(targetAttr, newRowid);
					continue;
				}
				
				for(Mapping stMapping : stMappings){
					String sourceAttr = stMapping.getAttMap().get(targetAttr);	//source schema attribute
					if(sourceAttr!=null){
						stp = stp.replace(targetAttr, sourceAttr);
						stSNames.add(Mapping.getAttTName(sourceAttr));//target schema name, source schema name
						break;
					}
				}
			}

			//replacing table names
			StringBuilder sb = new StringBuilder("");
//			if(ttpMapping.getSnames().length==2||stSNames.size()==1){
//				//two target schema mapped by one source schema
//				String stSName = "";
//				for(String str : stSNames){
//					stSName = str;
//				}
//				sb.append(stSName+","+stSName);
//			}
//			else{
				for(String name : stSNames){
					sb.append(name + ",");
				}
				sb.deleteCharAt(sb.length()-1);
//			}
			int from = stp.indexOf("from ");
			stp = stp.substring(0, from+5);
			stp = stp + sb.toString();
			
			
			boolean samePattern = false;
			String equalsRegex = "\\s*\\w+\\.\\w+\\s*=\\s*\\w+\\.\\w+\\s*";
			//add st condition
			StringBuilder finalCondition = new StringBuilder("");	
			for(int j=0; j< stMappings.size(); j++){
				Mapping stMap = stMappings.get(j);
				//stCondtion
				if(stMap.getConditionString().length()==0) continue;
				if(samePattern(stMap.getConditionString(),ttpMapping.getConditionString(),equalsRegex)){
					samePattern = true;
				}
				if(j>=1){
					//if previous st condition are not same of present st condition
					if(!samePattern(stMap.getConditionString(),stMappings.get(j-1).getConditionString(),equalsRegex)){
						finalCondition.append(stMap.getConditionString() + " AND ");
					}
				}else{	//append the first condition
					finalCondition.append(stMap.getConditionString() + " AND ");
				}
			}
			
			if(finalCondition.length()>0)
				finalCondition.delete(finalCondition.length()-5, finalCondition.length());
			 
			//add ttpCondition
			String ttpCondition = ttpMapping.getConditionString();
			if(ttpCondition.length()!=0){			
				if(!ttpCondition.matches(equalsRegex)){
					//replace attr
					for(int j=0; j<ttpMapping.getCondition().length-1;j++){
						String targetAttr = ttpMapping.getCondition()[j];
						//search in every correspond of stMappings 
						for(Mapping stMapping : stMappings){
							String sourceAttr = stMapping.getAttMap().get(targetAttr);	//source schema attribute
							if(sourceAttr!=null){
								ttpCondition = ttpCondition.replace(targetAttr, sourceAttr);
								break;
							}
						}
					}
					//if this pattern "[].[]=[].[]" is not repeated in previous st condition
					if(!samePattern){
						if(finalCondition.length()==0)
							finalCondition.append(ttpCondition);
						else
							finalCondition.append(" AND "+ttpCondition);
						//System.out.println("does not match");
					}
				}
			}
			
			if(finalCondition.length()!=0)
				outputs[i] = stp + " where " + finalCondition.toString();
			else
				outputs[i] = stp;
			System.out.println("compose : "+outputs[i]);
			stp=null;
		}
		
		return rename(outputs);
	}
	
	
	public static void createTables(String... sqls){
		MappingDataManager.getInstance().executeSQLs(sqls);
	}

	public static List<Mapping> constructST(String sqls) throws Exception{
		String[] sqlsArr = sqls.split("\n");
		int n = sqlsArr.length;

		List<Mapping> stMappings = new ArrayList<Mapping>(n);
		
		for(int i=0; i<n; i++){
			Mapping mapping = new Mapping(sqlsArr[i]);
			mapping.saveIntoDB();
			stMappings.add(mapping);
		}
		return stMappings;
	}
	
	public static List<Mapping> constructTTP(String sqls) throws Exception{
		String[] sqlsArr = sqls.split("\n");
		int n = sqlsArr.length;
		List<Mapping> ttpMappings = new ArrayList<Mapping>(n);
		
		for(int i=0; i<n; i++){
			Mapping mapping = new Mapping(sqlsArr[i]);
			mapping.saveIntoDB();
			ttpMappings.add(mapping);
		}
		return ttpMappings;
	}
	
	public static List<Mapping> constructSSP(String sqls) throws Exception{
		String[] sqlsArr = sqls.split("\n");
		int n = sqlsArr.length;

		List<Mapping> sspMappings = new ArrayList<Mapping>(n);
		
		for(int i=0; i<n; i++){
			Mapping mapping = new Mapping(sqlsArr[i]);
			mapping.saveIntoDB();
			sspMappings.add(mapping);
		}
		return sspMappings;
	}

	
	public static void clearViews(String...sqls){
		MappingDataManager.getInstance().executeSQLs(sqls);
	}
	
	public static void clearViews(){
		String selectAllViews = "SELECT * FROM (SELECT 'DROP VIEW '||VIEW_NAME FROM user_views) ORDER BY 1 ASC";
		List<String> sqls = MappingDataManager.getInstance().queryValue(selectAllViews);
		String[] sqlArr = new String[sqls.size()];
		for(int i=0; i<sqls.size(); i++)
			sqlArr[i] = sqls.get(i);
		MappingDataManager.getInstance().executeSQLs(sqlArr);
	}
	
	public static String[] separateLines(String str){	
		return str.split("\n");
	}
	

	public static String readFile(String addr) {
		String content = "";

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(addr)), "UTF-8"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.isEmpty()||line.substring(0, 2).contains("--"))	continue;
				content += line + "\n";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Finish reading file:\n" + content);
		return content;
	}

	
	private static boolean samePattern(String s1, String s2, String regex){
		if(s1.matches(regex)&&s2.matches(regex))
			return true;
		else 
			return false;
	}
	
	private static List<String> getColumnNames(String name){
		StringBuilder sb = new StringBuilder(SelectColumnNames);
		sb.append("'"+ name + "'");
		List<String> res = MappingDataManager.getInstance().queryValue(sb.toString());
		int n = res.size();
		List<String> inverseOrder = new ArrayList<String>(n);
		
		for(int i=n-1; i>=0; i--){
			inverseOrder.add(res.get(i).toLowerCase());
		}
		return inverseOrder;
	}
	
	private static String[] rename(String... sqls) throws Exception{
		String[] res = new String[sqls.length];
		int k = 0;
		for(String sql : sqls){
			int from = sql.indexOf("from");
			int where = sql.indexOf("where");
			if(where<0) 
				where = sql.length();
			
			String firstPart = sql.substring(0, from);
			String secondPart = sql.substring(from, where);
			String thirdPart = sql.substring(where, sql.length());
			
			Mapping mapping = new Mapping(sql);
			HashMap<String, String> aliasMap = new HashMap<String, String>();
			int i=1;
			for(String sname : mapping.getSnames()){
				String alias = "t" + i++;
				aliasMap.put(sname, "t"+alias);
				
				firstPart = firstPart.replaceAll(sname, alias);
				thirdPart = thirdPart.replaceAll(sname, alias);
				secondPart = secondPart.replaceFirst(sname, sname + " " + alias);
			}
			
			res[k++] = firstPart + secondPart + thirdPart;
			//System.out.println(res[k-1]);
		}
		return res;
	}
	
}
