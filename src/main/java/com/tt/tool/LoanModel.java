package com.tt.tool;
import java.util.HashMap;
import java.util.Map;


public class LoanModel{
	public static Map<Object,String> LoanTypeModel(){
		Map<Object,String> loanTypeModel=new HashMap<>();
		//逾期模块
		loanTypeModel.put("1", "逾期");
		loanTypeModel.put("11", "初级逾期");
		loanTypeModel.put("12", "中级逾期");
		loanTypeModel.put("13", "高级逾期");

		loanTypeModel.put("2", "电催");

		loanTypeModel.put("3",  "拖车");
		loanTypeModel.put("31", "拖车未处理");
		loanTypeModel.put("32", "拖车已处理");
		loanTypeModel.put("33", "拖车完成");
		loanTypeModel.put("34", "失败");

		loanTypeModel.put("4", "诉讼");
		loanTypeModel.put("41", "未诉讼");
		loanTypeModel.put("42", "已诉讼");

		loanTypeModel.put("5", "拍卖");
		loanTypeModel.put("51", "未拍卖");
		loanTypeModel.put("52", "亏损(拍卖完成)");
		loanTypeModel.put("53", "盈利(拍卖完成)");

		loanTypeModel.put("6", "结清");
		loanTypeModel.put("61", "正常结清");
		loanTypeModel.put("62", "提前结清");
		loanTypeModel.put("63", "强制结清");
		loanTypeModel.put("64", "亏损结清");

		loanTypeModel.put("7", "核销");
		loanTypeModel.put("71", "未核销");
		loanTypeModel.put("72", "已核销");

		return loanTypeModel;
	}

	public static void main(String[] args) {
		String a = LoanModel.LoanTypeModel().get("1");
		System.out.println(a);
	}

}
