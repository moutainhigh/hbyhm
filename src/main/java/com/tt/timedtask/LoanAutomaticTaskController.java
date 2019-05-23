package com.tt.timedtask;

import com.tt.manager.LoanImportExcelController;
import com.tt.tool.DbCtrl;
import com.tt.tool.DbTools;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 自动任务
 *
 * 用于每日凌晨00:00逾期列表客户逾期天数增加一天
 *
 * @author 三十画生 2019-5-23
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class LoanAutomaticTaskController {
	private static Logger log = LogManager.getLogger(LoanAutomaticTaskController.class.getName());

	/**
	 * 修改逾期天数
	 */
//	@Scheduled(cron = "0/5 * *  * * ? ") // 每5秒执行一次
	@Scheduled(cron = "0 0 2 * * ?")// 每天凌晨2点执行
	public void tasktest() {
		DbTools dbTools = new DbTools();
		String updateOverdueDay="update hbloan_overdue_list set overdue_days=overdue_days+1,dt_edit=sysdate() where overdue_amount > 0";
		int countsDay = dbTools.recupdate(updateOverdueDay);
		System.out.println("hb修改逾期天数自动执行:"+LoanImportExcelController.Getnow()+"---"+countsDay);
		log.info("hb修改逾期天数自动执行:"+LoanImportExcelController.Getnow()+"---"+countsDay);

		String sql = "UPDATE hbloan_overdue_list l set dt_edit=sysdate(),l.type_status=(CASE WHEN l.overdue_days>=(select c.overdue_one from loan_config c where c.gems_fs_id=l.gems_fs_id) and l.overdue_days<(select c.overdue_two from loan_config c where c.gems_fs_id=l.gems_fs_id) THEN 11 WHEN l.overdue_days>=(select c.overdue_two from loan_config c where c.gems_fs_id=l.gems_fs_id) and l.overdue_days<(select c.overdue_three from loan_config c where c.gems_fs_id=l.gems_fs_id) THEN 12 WHEN l.overdue_days>=(select c.overdue_three from loan_config c where c.gems_fs_id=l.gems_fs_id) THEN 13 ELSE l.type_status END) where l.overdue_amount>0 and l.type_id=1";
		int counts = dbTools.recupdate(sql);
		System.out.println("hb修改逾期状态自动执行:"+LoanImportExcelController.Getnow()+"---"+counts);
		log.info("hb修改逾期状态自动执行:"+LoanImportExcelController.Getnow()+"---"+counts);
	}


	/**
	 * 从逾期修改到电催
	 */
//	@Scheduled(cron="0/6 * *  * * ? ")   //每6秒执行一次
	@Scheduled(cron = "0 0 5 * * ?") //每天凌晨5点执行
	public void tasktestTo(){
		DbTools dbTools = new DbTools();
		String sqlTo = "UPDATE hbloan_overdue_list l set dt_edit=sysdate(),l.type_id=2,l.type_status=0 where l.type_id=1 and l.overdue_days>(select c.overdue_to_phone from loan_config c where c.gems_fs_id=l.gems_fs_id)";
		int counts = 0;
		counts = dbTools.recupdate(sqlTo);
		log.info("hb修改逾期到电催自动执行:"+ LoanImportExcelController.Getnow()+"---"+counts);
	}
}