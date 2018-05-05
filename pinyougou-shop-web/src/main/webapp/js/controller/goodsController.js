//商品控制层(商家后台管理)
app.controller('goodsController' ,function($scope,$controller   ,goodsService,uploadService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        goodsService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        goodsService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(id){
        goodsService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //保存
    $scope.add=function(){
        $scope.entity.goodsDesc.introduction=editor.html();

        goodsService.add($scope.entity).success(
            function(response){
                if(response.type==0){
                    //重新查询
                    alert(response.msg)
                    //清空列表行的数据.
                    $scope.entity={}
                    //清空富文本的编辑器
                    editor.html('');
                }else{
                    alert(response.msg);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        goodsService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds=[];
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        goodsService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }
    /**
     * 上传图片的操作
     */
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(
            function (response) {
            if(response.type==0){
                //上传成功
               alert(response.msg)
                $scope.image_entity.url=response.msg; //设置文件地址

            }else {
                alert(response.msg)
            }

            })
            .error(function () {
                alert("上传发生错误")
            })
    }
    //定义页面 实体的结构
    $scope.entity={goodsDesc:{itemImages:[]}}
   // $scope.entity.goodsDesc={itemImages:[]}
    /**
     * 添加图片列表的信息
     * image_entity一个图片的信息
     */
    $scope.add_image_entity=function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity)
    }
    
    //删除图片
    $scope.remove_image_entity=function (index) {
        alert(index)
        $scope.entity.goodsDesc.itemImages.splice(index,1);
        //slice
    }
});
