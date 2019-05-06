		$('#main_list :checkbox.check_all').click(function (){
			$('#main_list :checkbox[name=delid]').prop('checked',$(this).is(':checked'));
		})
		$('#del_more_btn').click(function (){
			if(page_cn){
				del_more(page_cn,$('#main_list :checkbox[name=delid]:checked'));
			}
			return false;
		})
		
		function del_more(cn,objs){
			$ids = list_get_ids();
			
			if($ids==""){
				alert("请选择要删除的数据");
				return;
			}
			
			if(confirm('您确定要删除这些项目吗?')){
				var msg='';
				var all_length=$(objs).length;
				var now_length=0;
				$(objs).each(function (){
					var id=$(this).val();
					jQuery.getJSON('command.php?cn='+cn+'&do=del&id='+id,function (jo){
						if(jo.succ){
							msg+=id+':删除成功\r\n';
						}else{
							msg+=id+':删除失败('+jo.msg+')\r\n';
						}
						now_length+=1;
						if(now_length>=all_length){
							if(msg!=''){
								alert(msg);
								window.location.reload();
							}else{
								alert('请选择要删除的项目');
							}
						}
					});
				})
			}
		}
		function list_get_ids(){
			var ids='';
			$('#main_list input:checkbox:checked').each(function (){
				var id=$(this).attr('value');
				var name=$(this).attr('name');
				if(id && name){
					if(ids==''){
						ids=id;
					}else{
						ids+=','+id;
					}
				}
			})
			return ids;
		}
		$('*[id=csvBtn][base]').click(function (){
			var url=$(this).attr('base');
			$('#main_list table tr').find('td:first :checkbox:checked').each(function (){
				url+='&ids[]='+$(this).val();
			});
			window.location.href=url;
			return false;
		})
		function htmlToCsv(id,name){
			var html=$('#'+id).html();
			var param={'html':html};
			$.post('../comm/csv.html.php?file='+name,param,function (url){
				if(url) window.location.href=url;
			});
		}
