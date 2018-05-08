//商品控制层(商家后台管理)
app.controller('goodsController' ,function($scope,$controller,$location
                                           ,goodsService,uploadService,itemCatService,typeTemplateService){

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
    $scope.findOne=function(){
        var id= $location.search()['id'];//获取参数值
        if (id==null){
            return;
        }
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

    $scope.searchEntity={};//定义搜索对象(用户输入的信息,就是我们要查询的对象)

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
    $scope.entity={goodsDesc:{itemImages:[],specificationItems:[]} }
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

    //读取一级分类的信息
    $scope.selectItemCat1List=function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List=response;
            }
        )
    }

    //读取二级分类的信息$watch 方法用于监控某个变量的值，当被监控的值发生变化，就自动执行相应的函数
    $scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {
        //根据选择的值,查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List=response;
            }
        )
    })

    //读取三级分类
    $scope.$watch('entity.goods.category2Id',function (newValue,oldValue) {
        //根据选择的值,查询三级分类的数据
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List=response;
            }
        )
    })
    //读取三级分类,读取模版的id值,监控最后的变化
    $scope.$watch('entity.goods.category3Id',function (newValue,oldValue) {
        //根据选择的值,查询三级分类的数据
        itemCatService.findOne(newValue).success(
            function (response) {
               // alert(response.id)
                $scope.entity.goods.typeTemplateId=response.typeId;
            }
        )
    })

    //根据id模版,更新品牌的列表
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                //获取类型模版
                $scope.typeTemplate=response;
                //获取品牌列表$scope.typeTemplate.brandIds是字符串我需要的是集合
                $scope.typeTemplate.brandIds=
                    JSON.parse( $scope.typeTemplate.brandIds)

                //在用户更新模板 ID 时，读取模板中的扩展属性赋给商品的扩展属性。
                $scope.entity.goodsDesc.customAttributeItems=
                    JSON.parse( $scope.typeTemplate.customAttributeItems)
            }
        )
        //查询所有的规格列表信息
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                alert("规格列表信息")
                $scope.speclist=response;
            }
        )
    })

    //规格选择
    //[{“attributeName”:”规格名称”,”attributeValue”:[“规格选项 1”,“规格选项 2”.... ] } , .... ]

    $scope.updateSpecAttribute=function ($event,name,value) {
        var object=$scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
        if (object!=null){
            if($event.target.checked){
                //说明已经勾选了
                object.attributeValue.push(value)
            }else {
                //取消了勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value),1)//移除选项
               //选项全部取消的话,移除整个的列表
                if(object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object),1)//移除整个
                }
            }
        }else {
            //第一次点击,创建整个集合
            /*alert(name)
            alert(value)*/
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name ,"attributeValue":[value] })

        }
    }

    /**
     * 创建SKU列表
     *      商品的属性,各种描述
     */
    $scope.createItemList=function () {
        //初始化,一个行的数据
        //$scope.entity.itemList=[{spec:{}}]
        $scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0' } ];

       //获取规格这个列表数据的对象
        var items= $scope.entity.goodsDesc.specificationItems;
        for (var i=0;i<items.length;i++){
            //添加每一行数据
            $scope.entity.itemList=addColum($scope.entity.itemList,
                items[i].attributeName,items[i].attributeValue);
        }
    }
    //添加列值
    addColumn=function(list,columnName,conlumnValues){
        var newList=[];//新的集合
        for(var i=0;i<list.length;i++){
            var oldRow= list[i];
            for(var j=0;j<conlumnValues.length;j++){
                var newRow= JSON.parse( JSON.stringify( oldRow ) );//深克隆
                newRow.spec[columnName]=conlumnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

    $scope.status=['未审核','已审核','审核未通过','关闭'];//商品状态

    /**
     * 加载商品列表分类的名称
     * 如果数据声明的方法,在model中引用,必须要加载页面
     */

    $scope.itemCatList=[]  //如果没有定义一个空壳
    $scope.findItemCatList=function () {
        itemCatService.findAll().success(
            function (response) {
                //响应回来的数据[{"id":1,"name":"图书、音像、电子书刊","parentId":0,"typeId":35},{}]
                //因为我们需要根据分类 ID 得到分类名称，所以我们将返回的分页结果以数组形式再次封装。
                for (var i=0;i<response.length;i++){
                 //   $scope.itemCatList[response[i].id]=response[i].name
                $scope.itemCatList[response[i].id]=response[i].name;
                }
            }
        )
    }


});
