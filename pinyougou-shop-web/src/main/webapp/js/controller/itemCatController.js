 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		//记住上级ID
		var serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			//赋予上级的id
			$scope.entity.parentId=$scope.parentId;
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.type==0){
					//重新查询 
		        	$scope.findByParentId($scope.parentId);//重新加载
				}else{
					alert(response.msg);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.type==0){
					$scope.reloadList();//刷新列表

					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    /**
	 * 记住上一级的id,因为每次点击下一级的时候,id会改变,
	 * 而在保存的时候会用到这个id的值,因为我要明白往哪里存
	 *
     * @param parentId
     */
    //根据上级id,,信息查询
	$scope.findByParentId=function (parentId) {
		//定义一个全局的变量id;
        $scope.parentId=parentId;
		itemCatService.findByParentId(parentId).success(
			function (response) {
				$scope.list=response;
            }
		)
    }
    //要返回上级列表，需要通过点击面包屑来实现
    $scope.level=1; //默认等级是1级
    //设置级别
    $scope.setLevel=function (value) {
        //全局变量
        $scope.level=value;
    }
    //读取列表
    $scope.selectList=function (p_entity) {
        //判读level的值 ,看现在处于什么等级
		//alert($scope.level)
        if($scope.level==1){
            //最高的等级
            $scope.entity_1=null;
            $scope.entity_2=null;
        }
        if($scope.level==2){
            //下一个的等级
            $scope.entity_1=p_entity;
            $scope.entity_2=null;
        }
        if($scope.level==3){
            //最后的等级
            $scope.entity_2=p_entity;
        }
        //查询信息,现在等级id
        $scope.findByParentId(p_entity.id);
    }
});	
